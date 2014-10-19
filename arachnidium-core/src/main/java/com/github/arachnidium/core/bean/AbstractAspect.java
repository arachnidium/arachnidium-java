package com.github.arachnidium.core.bean;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * This aspect is used by listeners
 */
public abstract class AbstractAspect {

	protected static enum WhenLaunch {
		BEFORE(BeforeTarget.class), AFTER(AfterTarget.class);
		/**
		 * I use only @BeforeTarget and @AfterTarget here
		 */
		private final Class<? extends Annotation> annotation;

		private WhenLaunch(Class<? extends Annotation> annotation) {
			this.annotation = annotation;
		}

		private Annotation[] getAnnotations(Method m) {
			return m.getAnnotationsByType(annotation);
		}
	}

	/**
	 * Customized annotations which are used by {@link AbstractAspect} subclasses
	 */
	
    /**
     * This annotation should mark methods 
     * that are invoked before target methods
     */
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	@Repeatable(BeforeTargets.class)
	protected @interface BeforeTarget {
		Class<?> targetClass();

		String targetMethod();
	}

	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	protected @interface BeforeTargets {
		BeforeTarget[] value();
	}

    /**
     * This annotation should mark methods 
     * that are invoked after target methods
     */	
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	@Repeatable(AfterTargets.class)
	protected @interface AfterTarget {
		Class<?> targetClass();

		String targetMethod();
	}

	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	protected @interface AfterTargets {
		AfterTarget[] value();
	}

    /**
     * This annotation should mark parameter
     * of the listener method if it is assumed to
     * use target object
     */
	@Target(value = ElementType.PARAMETER)
	@Retention(value = RetentionPolicy.RUNTIME)
	// listenable target
	// that can be used as a parameter value
	protected @interface TargetParam {
	}

    /**
     * This annotation should mark listener field.
     * It is possible that some object is a parameter
     * of the listener method. But it is not a target and 
     * it is not one of target method parameters.
     * 
     *  So. This object can be instantiated by listener.
     *  After it can be inserted to listener method signature.
     */	
	@Target(value = ElementType.FIELD)
	@Retention(value = RetentionPolicy.RUNTIME)
	// This field value can be inserted as a parameter
	protected @interface SupportField {
	}

	/**
	 * If parameter is marked by 
	 * this it means that one of instantiated 
	 * field values will be inserted here. 
	 * 
	 * Field should be marked by {@link SupportField} 
	 * and have suitable class
	 */
	@Target(value = ElementType.PARAMETER)
	@Retention(value = RetentionPolicy.RUNTIME)
	// Field marked by @SuppotField
	// can be inserted as a parameter marked by
	protected @interface SupportParam {
	}

	/**
     * If parameter is marked by 
	 * this it means that one of the target method parameters
	 * will be inserted here 
	 */
	@Target(value = ElementType.PARAMETER)
	@Retention(value = RetentionPolicy.RUNTIME)
	// Field marked by @SuppotField
	// can be inserted as a parameter marked by
	protected @interface UseParameter {
		int number();
	}

	/**
	 * *****************************************
	 * *****************************************
	 */
	private final static String TARGET_CLASS = "targetClass";
	private final static String TARGET_METHOD = "targetMethod";
	private final static String PARAMETER_N = "number";
	private final static Class<?>[] EMPTY_VALUE_ARGS = new Class<?>[] {};
	private final static Object[] EMPTY_PARAMETER_VALUES = new Object[] {};
	final IConfigurationWrapper configurationWrapper;

	private static Object getSupportField(Object aspectObject,
			Class<?> requiredClass) {
		Field[] declaredFields = aspectObject.getClass().getDeclaredFields();
		for (Field f : declaredFields) {
			if (!f.isAnnotationPresent(SupportField.class)) {
				continue;
			}

			if (!requiredClass.isAssignableFrom(f.getType())) {
				continue;
			}
			try {
				f.setAccessible(true);
				return f.get(aspectObject);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	private static List<Method> getListenerMethods(WhenLaunch when,
			Object aspectObject) {
		Method[] methods = aspectObject.getClass().getDeclaredMethods();
		List<Method> result = new ArrayList<Method>();
		for (Method m : methods) {
			if (when.getAnnotations(m).length == 0) {
				continue;
			}
			result.add(m);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getAnnotatonParameter(Annotation a, String paramName) {
		try {
			Method m = a.getClass().getMethod(paramName, EMPTY_VALUE_ARGS);
			return (T) m.invoke(a, EMPTY_PARAMETER_VALUES);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private static List<Class<?>> getTargetClasses(Annotation[] annotations) {
		ArrayList<Class<?>> result = new ArrayList<>();
		for (Annotation a : annotations) {
			result.add(getAnnotatonParameter(a, TARGET_CLASS));
		}
		return result;
	}

	private static List<String> getTargetMethods(Annotation[] annotations) {
		ArrayList<String> result = new ArrayList<>();
		for (Annotation a : annotations) {
			result.add(getAnnotatonParameter(a, TARGET_METHOD));
		}
		return result;
	}

	private static boolean isAssignable(Class<?> assignable,
			List<Class<?>> possibleClasses) {
		for (Class<?> c : possibleClasses) {
			if (!c.isAssignableFrom(assignable)) {
				continue;
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns method of listener that should be executed before/after target
	 * method
	 */
	private static Method getSuitableListenerMethod(JoinPoint joinPoint,
			Object aspectObject, WhenLaunch when) {
		String methodName = joinPoint.getSignature().getName();
		Object target = joinPoint.getTarget();
		List<Method> methods = getListenerMethods(when, aspectObject);

		for (Method m : methods) {
			Annotation[] annotations = when.getAnnotations(m);
			if (!isAssignable(target.getClass(), getTargetClasses(annotations))) {
				continue;
			}
			if (!getTargetMethods(annotations).contains(methodName)) {
				continue;
			}
			return m;
		}
		return null;
	}

	/**
	 * Runs method before or after target method
	 */
	protected static void launchMethod(JoinPoint joinPoint,
			Object aspectObject, WhenLaunch when) {
		final Method m = getSuitableListenerMethod(joinPoint, aspectObject,
				when);
		if (m == null) {
			return;
		}
		final Object[] args = joinPoint.getArgs();
		final Parameter[] listenerParams = m.getParameters();
		Object[] listerArgValues = new Object[m.getParameterCount()];

		if (listerArgValues.length == 0) {
			try {
				m.invoke(aspectObject, EMPTY_PARAMETER_VALUES);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}

		for (int i = 0; i < listenerParams.length; i++) {
			if (listenerParams[i].isAnnotationPresent(TargetParam.class)) {
				Object target = joinPoint.getTarget();
				if (listenerParams[i].getType().isAssignableFrom(target.getClass())){
					listerArgValues[i]= joinPoint.getTarget();
				}
				else {
					listerArgValues[i] = null; 
				}
			}

			if (listenerParams[i].isAnnotationPresent(SupportParam.class)) {
				listerArgValues[i] = getSupportField(aspectObject,
						listenerParams[i].getType());
			}

			if (listenerParams[i].isAnnotationPresent(UseParameter.class)) {
				UseParameter useParameter = listenerParams[i]
						.getAnnotation(UseParameter.class);
				Object val = getAnnotatonParameter(useParameter,
						PARAMETER_N);
				int index = Integer
						.valueOf(val.toString());
				listerArgValues[i] = args[index];
			}
		}

		try {
			m.setAccessible(true);
			m.invoke(aspectObject, listerArgValues);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public AbstractAspect(IConfigurationWrapper configurationWrapper){
		this.configurationWrapper = configurationWrapper;
	}
	
	/**
	 * This abstract method will implement logic of the listening. 
	 * 
	 * @param point is the {@link ProceedingJoinPoint} instance
	 * @return Object that has been returned by target method
	 * @throws Throwable
	 * 
	 * @see {@link ProceedingJoinPoint}
	 */
	public abstract Object doAround(ProceedingJoinPoint point)  throws Throwable;
	
	/**
	 * If some exception is thrown its cause will be got and thrown instead of 
	 * it
	 * @param thrown Is a {@link Throwable} instance which is thrown 
	 * @return The root cause of the thrown exception
	 */
	protected static Throwable getRootCause(Throwable thrown){
		Class<? extends Throwable> throwableClass = thrown.getClass();
		if (!InvocationTargetException.class.equals(throwableClass) && !RuntimeException.class.equals(throwableClass)){
			return thrown;
		}
		if (thrown.getCause() != null){
			return getRootCause(thrown.getCause());
		}
		return thrown;
	}

}
