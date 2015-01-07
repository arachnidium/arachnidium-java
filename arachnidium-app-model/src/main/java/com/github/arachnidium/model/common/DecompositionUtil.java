package com.github.arachnidium.model.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.arachnidium.core.HowToGetPage;
import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.abstractions.ModelObject;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.ClassDeclarationReader;
import com.github.arachnidium.model.support.annotations.DefaultContextIndex;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedAndroidActivity;
import com.github.arachnidium.model.support.annotations.ExpectedContext;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.Frame;
import com.github.arachnidium.model.support.annotations.TimeOut;
import com.github.arachnidium.model.support.annotations.rootelements.CommonRootElementReader;
import com.github.arachnidium.model.support.annotations.rootelements.ElementReaderForMobilePlatforms;
import com.github.arachnidium.model.support.annotations.rootelements.IRootElementReader;
import com.github.arachnidium.model.support.annotations.rootelements.RootAndroidElement;
import com.github.arachnidium.model.support.annotations.rootelements.RootIOSElement;
import com.github.arachnidium.util.proxy.EnhancedProxyFactory;
import com.github.arachnidium.util.reflect.executable.ExecutableUtil;

abstract class DecompositionUtil {
	static final String GET_PART = "getPart";

	/**
	 * Creation of any decomposable part of application
	 */
	static <T extends IDecomposable> T get(Class<T> partClass,
			Class<?>[] params, Object[] paramValues) {
		try{
			T decomposable = EnhancedProxyFactory.getProxy(partClass,
					getRelevantConstructorParameters(params, paramValues, partClass), paramValues,
					new InteractiveInterceptor() {
					});
			DecompositionUtil.populateFieldsWhichAreDecomposable((ModelObject<?>) decomposable);
			return decomposable;
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}		
	}
	
	/**
	 * This method populates fields of classes which implements {@link ModelObject}
	 * This field should be merked by {@link Static}. It can be marked by {@link Frame},
	 * {@link RootElement}/{@link RootAndroidElement}/{@link RootIOSElement}
	 * 
	 * @param targetDecomposableObject this is the object whose fields should be populated
	 */
	static void populateFieldsWhichAreDecomposable(
			ModelObject<?> targetDecomposableObject) {
		Class<?> clazz = targetDecomposableObject.getClass();
		ESupportedDrivers supportedDriver = targetDecomposableObject.
				getWebDriverEncapsulation().getInstantiatedSupportedDriver();
		while (clazz != Object.class) {
			List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
			for (Field field: fields){
				try {
					field.setAccessible(true);
					if (!field.isAnnotationPresent(Static.class)|| 
							field.get(targetDecomposableObject) != null) {
						continue;
					}
				
					Class<?> fieldClass = field.getType();
					//if here is possible list of decomposable object
					if (List.class.isAssignableFrom(fieldClass) && getClassFromTheList(field) != null){
						field.set(targetDecomposableObject, EnhancedProxyFactory.
								getProxy(ArrayList.class, new Class<?>[] {}, 
								new Object[]{}, new DecomposableListInterceptor(field, 
										targetDecomposableObject, supportedDriver)));
						continue;
					}					
					
					if (ModelObject.class.isAssignableFrom(fieldClass)){ //if here is a field where 
						//should be only single object
						Object[] args = new Object[] {field.getType()};
						Method m = ExecutableUtil.getRelevantMethod(clazz, GET_PART, args);
						if (Application.class.isAssignableFrom(clazz)){
							args = getRelevantArgs2(supportedDriver, m, args, field);
						}
						else{
							args = getRelevantArgs(supportedDriver, m, args, field);
						}
						m = ExecutableUtil.getRelevantMethod(clazz, GET_PART, args);
						ModelObject<?> value =  (ModelObject<?>) m.invoke(targetDecomposableObject, args);
						field.set(targetDecomposableObject, value);
						//ModelObject fields of a new mock-instance are mocked too 
						populateFieldsWhichAreDecomposable((ModelObject<?>) value);
						continue;
					}
					
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			};
			clazz = clazz.getSuperclass();
		}
	}

	private static ClassDeclarationReader declarationReader = new ClassDeclarationReader();

	/**
	 * Creates an instance of {@link HowToGetByFrames} class if
	 * the given class is annotated by {@link Frame}.
	 * 
	 * @param params  
	 * @param targetClass It is a class which is supposed to be annotated by {@link Frame}
	 * 
	 * @return A {@link HowToGetByFrames} strategy instance if the 
	 * given class is annotated by {@link Frame} <br/>
	 * <br/>
	 * <code>null</code> if the 
	 * given class isn't annotated by {@link Frame}
	 */	
	static HowToGetByFrames getHowToGetByFramesStrategy(AnnotatedElement annotatedElement){
		List<Object> framePath = declarationReader
				.getFramePath(declarationReader.getAnnotations(
						Frame.class, annotatedElement));
		if (framePath.size() != 0) {	
			HowToGetByFrames howTo = new HowToGetByFrames();
			framePath.forEach((chainElement) -> {
				howTo.addNextFrame(chainElement);
			});
			return howTo;
		}
		return null;
	}

	/**
	 * This methods transforms
	 * values of annotations that marks
	 * the given class to strategies 
	 * {@link HowToGetPage} or {@link HowToGetMobileScreen} 
	 * 
	 *@param indexAnnotation is the class of annotation which 
	 * is expected marks the given class
	 *possible annotations are {@link DefaultPageIndex} and {@link DefaultContextIndex}.
	 * 
	 *@param handleUniqueIdentifiers is the class of annotation which 
	 * is expected marks the given class
	 * Possible annotations are {@link ExpectedURL} and {@link ExpectedAndroidActivity}.
	 * 
	 *@param additionalStringIdentifieris the class of annotation which 
	 * is expected marks the given class
	 * Possible annotations are {@link ExpectedPageTitle} and {@link ExpectedContext}.
	 * 
	 *@param annotated is a given class that can be marked by annotations above
	 * 
	 *@param howToClass is the class of strategy that combines values of 
	 * annotations above. Available classes are {@link HowToGetPage} 
	 * and {@link HowToGetMobileScreen}
	 * 
	 * @return the instance of a strategy class defined by 
	 *@param howToClass
	 * 
	 * @throws ReflectiveOperationException
	 */
	private static <T extends IHowToGetHandle> T getHowToGetHandleStrategy(
			Class<? extends Annotation> indexAnnotation,
			Class<? extends Annotation> handleUniqueIdentifiers,
			Class<? extends Annotation> additionalStringIdentifier,
			AnnotatedElement annotated, Class<T> howToClass) {
		Annotation[] indexAnnotations = declarationReader
				.getAnnotations(indexAnnotation, annotated);
		Integer index = null;
		if (indexAnnotations.length > 0) {
			index = declarationReader.getIndex(indexAnnotations[0]);
		}
	
		Annotation[] handleUniqueIdentifiers2 = declarationReader
				.getAnnotations(handleUniqueIdentifiers, annotated);
		List<String> identifiers = declarationReader
				.getRegExpressions(handleUniqueIdentifiers2);
		if (identifiers.size() == 0) {
			identifiers = null;
		}
	
		String additionalStringIdentifier2 = null;
		Annotation[] additionalStringIdentifiers = declarationReader
				.getAnnotations(additionalStringIdentifier, annotated);
		if (additionalStringIdentifiers.length > 0) {
			additionalStringIdentifier2 = declarationReader
					.getRegExpressions(additionalStringIdentifiers).get(0);
		}
	
		if (index == null && identifiers == null
				&& additionalStringIdentifier2 == null) {
			return null;
		}
	
		try {
			T result = howToClass.newInstance();
			if (index != null) {
				result.setExpected(index);
			}
			if (identifiers != null) {
				result.setExpected(identifiers);
			}
			if (additionalStringIdentifier2 != null) {
				result.setExpected(additionalStringIdentifier2);
			}
			return result;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns {@link Long} value of implicit waiting for some page/screen if the 
	 * target class is marked by {@link TimeOut} annotation
	 * @param annotated is the target class which is supposed to be annotated 
	 * by {@link TimeOut}
	 * @return {@link Long} value if annotation is present. <code>null</code> otherwise
	 */
	static Long getTimeOut(AnnotatedElement annotated) {
		TimeOut[] timeOuts = declarationReader.getAnnotations(
				TimeOut.class, annotated);
		if (timeOuts.length == 0) {
			return null;
		}
		return declarationReader.getTimeOut(timeOuts[0]);
	}

	static IRootElementReader getRootElementReader(ESupportedDrivers supportedDriver){
		if (supportedDriver.isForBrowser()){
			return new CommonRootElementReader();
		}
		return new ElementReaderForMobilePlatforms();
	}

	/**
	 * This method is used by {@link IDecomposable} method substitution mechanism
	 * 
	 * @param supportedDriver it is description of the supported {@link WebDriver} implementor
	 * @param method Originally invoked method. It should be one of {@link IDecomposable} methods
	 * @param args explicitly given arguments
	 * @param annotatedElement is a {@link Field}, {@link Method} or {@link Class} which is 
	 * supposed to be marked by {@link Frame} and {@link RootElement}/{@link RootAndroidElement}/
	 * {@link RootIOSElement}
	 * @return A new argument array
	 * @throws Throwable
	 */
	static Object[] getRelevantArgs(ESupportedDrivers supportedDriver, Method method, Object[] args, 
			AnnotatedElement annotatedElement) {		
		HowToGetByFrames howTo = getDefinedParameter(method, HowToGetByFrames.class,
						args);
		if (howTo == null)
			howTo = getHowToGetByFramesStrategy(annotatedElement);
		
		By rootBy = getDefinedParameter(method,
				By.class, args);
		if (rootBy == null) {
			IRootElementReader rootElementReader = getRootElementReader(supportedDriver);
			rootBy = rootElementReader.readClassAndGetBy(annotatedElement,	supportedDriver);
		}		
		
		Object[] newArgs = new Object[]{extractTargetFromGetPart(method, args)};
		if (howTo != null) {
			newArgs = ArrayUtils.add(newArgs, howTo);
		}
		
		if (rootBy != null){
			newArgs = ArrayUtils.add(newArgs, rootBy);
		}
		
		return newArgs;
	}
	
	/**
	 * This method is used by {@link IDecomposable} method substitution mechanism
	 * It takes into account situations when window/context should be found by some condition  
	 * 
	 * @param supportedDriver it is description of the supported {@link WebDriver} implementor
	 * @param method Originally invoked method. It should be one of {@link IDecomposable} methods
	 * @param args explicitly given arguments
	 * @param annotatedElement is a {@link Field}, {@link Method} or {@link Class} which is 
	 * supposed to be marked by {@link Frame} and {@link RootElement}/{@link RootAndroidElement}/
	 * {@link RootIOSElement}
	 * @return A new argument array
	 * @throws Throwable
	 */	
	static Object[] getRelevantArgs2(ESupportedDrivers supportedDriver, Method method, Object[] args, 
			AnnotatedElement annotatedElement) {	
		
		IHowToGetHandle how = getDefinedParameter(method, IHowToGetHandle.class, args);
		if (how == null)
			how = getRelevantHowToGetHandleStrategy(supportedDriver, annotatedElement);
			
		
		Integer index = getDefinedParameter(method, int.class, args);
		// if index of a window/screen was defined
		if (how != null && index != null) {
			how.setExpected(index.intValue());
		}
		
		Long timeOutLong = getDefinedParameter(method, long.class, args);
		if (timeOutLong == null)
			timeOutLong = getTimeOut(annotatedElement);
		
		
		HowToGetByFrames howTo = getDefinedParameter(method, HowToGetByFrames.class,
						args);
		if (howTo == null)
			howTo = getHowToGetByFramesStrategy(annotatedElement);
		
		By rootBy = getDefinedParameter(method,
				By.class, args);
		if (rootBy == null) {
			IRootElementReader rootElementReader = getRootElementReader(supportedDriver);
			rootBy = rootElementReader.readClassAndGetBy(annotatedElement, supportedDriver);
		}			

		// attempt to substitute methods is described below
		Object[] newArgs = new Object[]{extractTargetFromGetPart(method, args)};
		if (how != null) {
			newArgs = ArrayUtils.add(newArgs, how);
		} else if (index != null) {
			newArgs = ArrayUtils.add(newArgs, index.intValue());
		}
		
		if (howTo != null) {
			newArgs = ArrayUtils.add(newArgs, howTo);
		}
		
		if (rootBy != null){
			newArgs = ArrayUtils.add(newArgs, rootBy);
		}	
		
		if (timeOutLong != null) {
			newArgs = ArrayUtils.add(newArgs, timeOutLong.longValue());
		}
		
		return newArgs;
	}	
		

	/**
	 * For {@link IDecomposable#getPart(*)} methods
	 * 
	 * @param m method that is expected to be one of {@link IDecomposable#getPart(*)} methods
	 * @param args explicitly given arguments
	 * @return The target class if the given methods is one of {@link IDecomposable#getPart(*)}'s
	 * <code>null</code> will be returned otherwise
	 */
	static Class<?> extractTargetFromGetPart(Method m, Object[] args){
		if (m.getName().equals(GET_PART)){
			return (Class<?>) args[0];
		}
		return null;
	}
	
	static Class<?>[] getRelevantConstructorParameters(
			Class<?>[] paramerers, 
			Object[] values,
			Class<? extends IDecomposable> requiredClass) throws NoSuchMethodException {
		
		Constructor<?>[] declaredConstructors = requiredClass
				.getDeclaredConstructors();
		
		for (Constructor<?> constructor: declaredConstructors){			
			Class<?>[] declaredParams = constructor.getParameterTypes();			
			if (declaredParams.length != paramerers.length){
				continue;
			}
			
			Class<?>[] result = new Class<?>[]{};
			boolean matches = true;
			int i = 0;
			for (Class<?> declaredParam: declaredParams){
				if (declaredParam.isAssignableFrom(paramerers[i])){
					result = ArrayUtils.add(result, paramerers[i]);
					i++;
					continue;
				}
				
				if (values[i] == null){
					result = ArrayUtils.add(result, paramerers[i]);
					i++;
					continue;					
				}
				
				if (declaredParam.isAssignableFrom(values[i].getClass())){
					result = ArrayUtils.add(result, declaredParam);
					i++;
					continue;
				}				
				matches = false;
				break;
			}
			
			if (matches){
				return result;
			}
		}
		
		throw new NoSuchMethodException("There is no cunstructor which matches to " + Arrays.asList(paramerers).toString() + 
				". The target class is " + requiredClass.getName());
	}
	
	/**
	 * This method is used to instantiate lists of {@link IDecomposable} 
	 * (e.g. {@link FunctionalPart} subclasses). It detects the class of the list-field.
	 * If the result implements {@link IDecomposable} then it will be returned. <code>null</code>
	 * will be returned otherwise
	 * 
	 * @param field which is supposed to be a list of {@link IDecomposable} (e.g. {@link FunctionalPart} subclasses)
	 * @return the generic class of the list. If it is the {@link List}-field and the generic class 
	 * implements {@link IDecomposable} then it will be returned. <code>null</code>
	 * will be returned otherwise
	 */
	@SuppressWarnings("unchecked")
	static Class<? extends IDecomposable> getClassFromTheList(Field field){
		if (!List.class.isAssignableFrom(field.getType()))
			return null;
		
		Type genericType = field.getGenericType();
		if (!(genericType instanceof ParameterizedType)) {
			return null;
		}
		
		Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];	
		try {
			Class<?> candidate = Class.forName(listType.getTypeName());
			if (IDecomposable.class.isAssignableFrom(candidate)){
				return (Class<? extends IDecomposable>) candidate;
			}
			return null;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}				
	}
	
	/**
	 * This method returns the relevant {@link IHowToGetHandle} instance. It depends on the 
	 * launched {@link WebDriver} implementor and annotations which mark the given class/field
	 * <br/><br/>
	 * Here are possible annotations for browser pages and mobile web view content: <br>
	 * {@link ExpectedURL}<br/>
	 * {@link ExpectedPageTitle}<br/>
	 * {@link DefaultPageIndex}<br/><br/>
	 * Here are possible annotations for mobile screens: <br>
	 * {@link ExpectedAndroidActivity}
	 * {@link ExpectedContext}
	 * {@link DefaultContextIndex}
	 * 
	 * 
	 * @param supportedDriver they are parameters of {@link WebDriver} which is already launched
	 * @param annotatedElement the given class or field which is probably annotated by 
	 * <br/>
	 * {@link ExpectedURL}<br/>
	 * {@link ExpectedPageTitle}<br/>
	 * {@link DefaultPageIndex}<br/> if browser pages or mobile web view content are supposed <br/>
	 * {@link ExpectedAndroidActivity}<br/>
	 * {@link ExpectedContext}<br/>
	 * {@link DefaultContextIndex} if mobile screens are supposed	 * 
	 * @return a built instance of {@link IHowToGetHandle}
	 */
	static IHowToGetHandle getRelevantHowToGetHandleStrategy(ESupportedDrivers supportedDriver, 
			AnnotatedElement annotatedElement){
		
		HowToGetMobileScreen howToGetMobileScreen = null;
		HowToGetPage howToGetPage = getHowToGetHandleStrategy(DefaultPageIndex.class,
				ExpectedURL.class, ExpectedPageTitle.class, 
				annotatedElement, HowToGetPage.class);
		
		if (supportedDriver.isForBrowser()){
			return howToGetPage;
		}else{
			howToGetMobileScreen = getHowToGetHandleStrategy(DefaultContextIndex.class,
					ExpectedAndroidActivity.class, ExpectedContext.class, 
					annotatedElement, HowToGetMobileScreen.class);
			if (howToGetMobileScreen != null)
				howToGetMobileScreen.defineHowToGetPageStrategy(howToGetPage);
			return howToGetMobileScreen;
		}			
	}
	
	/**
	 * Converts the given {@link Method} to {@link MethodProxy}
	 * 
	 * @param clazz A class whose {@link Method} should be converted to {@link MethodProxy}
	 * @param m a method to be converted to {@link MethodProxy}
	 * @return an instance of {@link MethodProxy}
	 */
	static MethodProxy getMethodProxy(Class<?> clazz, Method m){
		org.objectweb.asm.Type returned = org.objectweb.asm.Type.getReturnType(m);
		org.objectweb.asm.Type[] argTypes = org.objectweb.asm.Type.getArgumentTypes(m);
		Signature s = new Signature(m.getName(), returned, argTypes);
		return MethodProxy.find(clazz, s);		
	}	
	
	@SuppressWarnings("unchecked")
	static <T> T getDefinedParameter(Method method, Class<?> desiredClass, Object[] args){
		int paramIndex = ExecutableUtil.getParameterIndex(
				method, desiredClass);
		if (paramIndex >= 0){
			return (T) args[paramIndex];
		}
		return null;
	}
}
