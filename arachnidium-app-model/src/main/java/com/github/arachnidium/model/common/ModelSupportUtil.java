package com.github.arachnidium.model.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.sf.cglib.asm.Type;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.HowToGetBrowserWindow;
import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.Manager;
import com.github.arachnidium.core.WebDriverEncapsulation;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
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

/**
 * 
 * This is utility class for inner usage
 *
 */
abstract class ModelSupportUtil {

	private static final HashMap<Class<?>, Class<?>> FOR_USED_SIMPLE_TYPES = new HashMap<Class<?>, Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			put(Integer.class, int.class);
			put(Long.class, long.class);
			put(Boolean.class, boolean.class);
			// other can be added
		}
	};
	
	private ModelSupportUtil() {
		super();
	}

	static Handle getTheFirstHandle(
			Class<? extends Manager<?>> handleManagerClass,
			Class<?>[] wdEncapsulationParams, Object[] wdEncapsulationParamVals) {
		try {
			Constructor<?> wdeC = WebDriverEncapsulation.class
					.getConstructor(wdEncapsulationParams);
			WebDriverEncapsulation wdeInstance = (WebDriverEncapsulation) wdeC
					.newInstance(wdEncapsulationParamVals);
			return getTheFirstHandle(handleManagerClass, wdeInstance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Handle getTheFirstHandle(
			Class<? extends Manager<?>> handleManagerClass,
			WebDriverEncapsulation wdeInstance) {
		try {
			Constructor<?> c = handleManagerClass
					.getConstructor(new Class<?>[] { WebDriverEncapsulation.class });
			Manager<?> m = (Manager<?>) c
					.newInstance(new Object[] { wdeInstance });
	
			return m.getHandle(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Class<?>[] getParameterClasses(Object[] paramerers,
			Class<?> requiredClass) {
	
		Class<?>[] givenParameters = new Class<?>[paramerers.length];
		for (int i = 0; i < paramerers.length; i++) {
			givenParameters[i] = paramerers[i].getClass();
		}	
		Constructor<?>[] declaredConstructors = requiredClass
				.getDeclaredConstructors();
		Class<?>[] result = getSuitableParameterClasses(declaredConstructors, paramerers);
		if (result != null){
			return result;
		}
		throw new RuntimeException(new NoSuchMethodException(
				"There is no suitable constructor! Given parameters: "
						+ Arrays.asList(givenParameters).toString() + ". "
						+ "Class is " + requiredClass.getName()));
	}
	
	private static Class<?>[] getSuitableParameterClasses(
			Executable[] executables, Object[] paramerers) {

		Class<?>[] givenParameters = new Class<?>[paramerers.length];
		for (int i = 0; i < paramerers.length; i++) {
			givenParameters[i] = paramerers[i].getClass();
		}

		for (Executable executable : executables) {
			Class<?>[] declaredParameters = executable.getParameterTypes();

			if (declaredParameters.length != givenParameters.length) {
				continue;
			}

			boolean isMatch = true;
			for (int i = 0; i < declaredParameters.length; i++) {
				boolean areParametersMatch = declaredParameters[i]
						.isAssignableFrom(givenParameters[i]);
				Class<?> simpleType = FOR_USED_SIMPLE_TYPES
						.get(givenParameters[i]);
				boolean isCastedToSimple = (simpleType != null);
				if (!areParametersMatch && isCastedToSimple) {
					areParametersMatch = declaredParameters[i]
							.isAssignableFrom(simpleType);
				}
				if (!areParametersMatch) {
					isMatch = false;
					break;
				}
			}

			if (isMatch) {
				return declaredParameters;
			}
		}
		return null;
	}
	
	
	
	
	static Method getSuitableMethod(Class<?> clazz, String methodName, Object[] argValues){
		Method[] declaredMethods = clazz.getDeclaredMethods();
		List<Method> found = new ArrayList<Method>();
		for (Method m: declaredMethods){
			if (!m.getName().equals(methodName)){
				continue;
			}
			found.add(m);
		}
		
		if (found.size() == 0){
			return null;
		}
		
		for (Method m: found){
			Method[] methods = new Method[] {m};
			Class<?>[] result = getSuitableParameterClasses(methods, argValues);
			if (result == null){
				continue;
			}
			return m;
		}
		return null;
	}
	
	static MethodProxy getMethodProxy(Class<?> clazz, Method m){
		Type returned = Type.getReturnType(m);
		Type[] argTypes = Type.getArgumentTypes(m);
		return MethodProxy.find(clazz,
				new Signature(m.getName(), returned, argTypes));		
	}
	
	static int getParameterIndex(Parameter[] parameters, Class<?> requredClass){
		for (int i = 0; i < parameters.length; i ++){
			if (parameters[i].getType().isAssignableFrom(requredClass)){
				return i;
			}
		}
		return -1;
	}
	
	
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
	static HowToGetByFrames getHowToGetByFramesStrategy(List<Class<?>> params, Class<?> targetClass){
		if (!params.contains(HowToGetByFrames.class)) {
			List<Object> framePath = ClassDeclarationReader
					.getFramePath(ClassDeclarationReader.getAnnotations(
							Frame.class, targetClass));
			if (framePath.size() == 0) {
				return null;
			}
		
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
	 * {@link HowToGetBrowserWindow} or {@link HowToGetMobileScreen} 
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
	 * annotations above. Available classes are {@link HowToGetBrowserWindow} 
	 * and {@link HowToGetMobileScreen}
	 * 
	 * @return the instance of a strategy class defined by 
	 *@param howToClass
	 * 
	 * @throws ReflectiveOperationException
	 */
	static <T extends IHowToGetHandle> T getHowToGetHandleStrategy(
			Class<? extends Annotation> indexAnnotation,
			Class<? extends Annotation> handleUniqueIdentifiers,
			Class<? extends Annotation> additionalStringIdentifier,
			Class<?> annotated, Class<T> howToClass)
			throws ReflectiveOperationException {
		Annotation[] indexAnnotations = ClassDeclarationReader
				.getAnnotations(indexAnnotation, annotated);
		Integer index = null;
		if (indexAnnotations.length > 0) {
			index = ClassDeclarationReader.getIndex(indexAnnotations[0]);
		}

		Annotation[] handleUniqueIdentifiers2 = ClassDeclarationReader
				.getAnnotations(handleUniqueIdentifiers, annotated);
		List<String> identifiers = ClassDeclarationReader
				.getRegExpressions(handleUniqueIdentifiers2);
		if (identifiers.size() == 0) {
			identifiers = null;
		}

		String additionalStringIdentifier2 = null;
		Annotation[] additionalStringIdentifiers = ClassDeclarationReader
				.getAnnotations(additionalStringIdentifier, annotated);
		if (additionalStringIdentifiers.length > 0) {
			additionalStringIdentifier2 = ClassDeclarationReader
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
			throw e;
		}
	}	
	
	/**
	 * Returns {@link Long} value of implicit waiting for some page/screen if the 
	 * target class is marked by {@link TimeOut} annotation
	 * @param annotated is the target class which is supposed to be annotated 
	 * by {@link TimeOut}
	 * @return {@link Long} value if annotation is present. <code>null</code> otherwise
	 */
	static Long getTimeOut(Class<?> annotated) {
		TimeOut[] timeOuts = ClassDeclarationReader.getAnnotations(
				TimeOut.class, annotated);
		if (timeOuts.length == 0) {
			return null;
		}
		return ClassDeclarationReader.getTimeOut(timeOuts[0]);
	}
}
