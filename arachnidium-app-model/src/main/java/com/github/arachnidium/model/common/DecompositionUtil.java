package com.github.arachnidium.model.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;



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
import com.github.arachnidium.model.support.annotations.classdeclaration.ClassDeclarationReader;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.TimeOut;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.CommonRootElementReader;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.ElementReaderForMobilePlatforms;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.IRootElementReader;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootAndroidElement;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootIOSElement;
import com.github.arachnidium.util.proxy.EnhancedProxyFactory;

abstract class DecompositionUtil {
	protected static final String GET_PART = "getPart";

	/**
	 * Creation of any decomposable part of application
	 */
	static <T extends IDecomposable> T get(Class<T> partClass,
			Object[] paramValues) {
		try{
			T decomposable = EnhancedProxyFactory.getProxy(partClass,
					MethodReadingUtil.getParameterClasses(paramValues, partClass), paramValues,
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
					if (!field.isAnnotationPresent(Static.class)||!ModelObject.class.isAssignableFrom(field
							.getDeclaringClass()) || field.get(targetDecomposableObject) != null) {
						continue;
					}
				
					Object[] args = new Object[] {field.getType()};
					Method m = MethodReadingUtil.getSuitableMethod(clazz, GET_PART, args);
					if (Application.class.isAssignableFrom(clazz)){
						args = getRelevantArgs2(supportedDriver, m, args, field);
					}
					else{
						args = getRelevantArgs(supportedDriver, m, args, field);
					}
					m = MethodReadingUtil.getSuitableMethod(clazz, GET_PART, args);
					ModelObject<?> value =  (ModelObject<?>) m.invoke(targetDecomposableObject, args);
					field.set(targetDecomposableObject, value);
					//ModelObject fields of a new mock-instance are mocked too 
					populateFieldsWhichAreDecomposable((ModelObject<?>) value);
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
	private static HowToGetByFrames getHowToGetByFramesStrategy(AnnotatedElement annotatedElement){
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
	 *possible annotations are {@link IfBrowserDefaultPageIndex} and {@link IfMobileDefaultContextIndex}.
	 * 
	 *@param handleUniqueIdentifiers is the class of annotation which 
	 * is expected marks the given class
	 * Possible annotations are {@link IfBrowserURL} and {@link IfMobileAndroidActivity}.
	 * 
	 *@param additionalStringIdentifieris the class of annotation which 
	 * is expected marks the given class
	 * Possible annotations are {@link IfBrowserPageTitle} and {@link IfMobileContext}.
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
	private static Long getTimeOut(AnnotatedElement annotated) {
		TimeOut[] timeOuts = declarationReader.getAnnotations(
				TimeOut.class, annotated);
		if (timeOuts.length == 0) {
			return null;
		}
		return declarationReader.getTimeOut(timeOuts[0]);
	}

	private static IRootElementReader getRootElementReader(ESupportedDrivers supportedDriver){
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
		HowToGetByFrames howTo = MethodReadingUtil
				.getDefinedParameter(method, HowToGetByFrames.class,
						args);
		if (howTo == null)
			howTo = getHowToGetByFramesStrategy(annotatedElement);
		
		By rootBy = MethodReadingUtil.getDefinedParameter(method,
				By.class, args);
		if (rootBy == null) {
			IRootElementReader rootElementReader = getRootElementReader(supportedDriver);
			rootBy = rootElementReader.readClassAndGetBy(annotatedElement,
					supportedDriver.getUsingWebDriverClass());
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
		
		IHowToGetHandle how = MethodReadingUtil.getDefinedParameter(method, IHowToGetHandle.class, args);
		if (how == null)
			how = getHowToGetHandleStrategy(getIndexAnnotation(supportedDriver),
					getHandleIdentifiers(supportedDriver), getHandleStringIdentifiers(supportedDriver), 
					annotatedElement, getHandleStrategyClass(supportedDriver));
		
		Integer index = MethodReadingUtil.getDefinedParameter(method, int.class, args);
		// if index of a window/screen was defined
		if (how != null && index != null) {
			how.setExpected(index.intValue());
		}
		
		Long timeOutLong = MethodReadingUtil.getDefinedParameter(method, long.class, args);
		if (timeOutLong == null)
			timeOutLong = getTimeOut(annotatedElement);
		
		
		HowToGetByFrames howTo = MethodReadingUtil
				.getDefinedParameter(method, HowToGetByFrames.class,
						args);
		if (howTo == null)
			howTo = getHowToGetByFramesStrategy(annotatedElement);
		
		By rootBy = MethodReadingUtil.getDefinedParameter(method,
				By.class, args);
		if (rootBy == null) {
			IRootElementReader rootElementReader = getRootElementReader(supportedDriver);
			rootBy = rootElementReader.readClassAndGetBy(annotatedElement,
					supportedDriver.getUsingWebDriverClass());
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
	
	
	private static Class<? extends Annotation> getIndexAnnotation(ESupportedDrivers supportedDriver){
		if (supportedDriver.isForBrowser()){
			return IfBrowserDefaultPageIndex.class;
		}
		return IfMobileDefaultContextIndex.class;
	}
	
	private static Class<? extends Annotation> getHandleIdentifiers(ESupportedDrivers supportedDriver){
		if (supportedDriver.isForBrowser()){
			return IfBrowserURL.class;
		}
		return IfMobileAndroidActivity.class;
	}
	
	private static Class<? extends Annotation> getHandleStringIdentifiers(ESupportedDrivers supportedDriver){
		if (supportedDriver.isForBrowser()){
			return IfBrowserPageTitle.class;
		}
		return IfMobileContext.class;
	}	
	
	
	private static Class<? extends IHowToGetHandle> getHandleStrategyClass(ESupportedDrivers supportedDriver){
		if (supportedDriver.isForBrowser()){
			return HowToGetPage.class;
		}
		return HowToGetMobileScreen.class;
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

}
