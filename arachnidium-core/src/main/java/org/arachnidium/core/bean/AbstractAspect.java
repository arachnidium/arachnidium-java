package org.arachnidium.core.bean;

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

import org.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
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
	 * By this annotations methods of all listeners Should be marked
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
	private @interface BeforeTargets {
		BeforeTarget[] value();
	}

	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	@Repeatable(AfterTargets.class)
	protected @interface AfterTarget {
		Class<?> targetClass();

		String targetMethod();
	}

	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	private @interface AfterTargets {
		AfterTarget[] value();
	}

	@Target(value = ElementType.PARAMETER)
	@Retention(value = RetentionPolicy.RUNTIME)
	// listenable target
	// that can be used as a parameter value
	protected @interface TargetParam {
	}

	@Target(value = ElementType.FIELD)
	@Retention(value = RetentionPolicy.RUNTIME)
	// This field value can be inserted as a parameter
	protected @interface SupportField {
	}

	@Target(value = ElementType.PARAMETER)
	@Retention(value = RetentionPolicy.RUNTIME)
	// Field marked by @SuppotField
	// can be inserted as a parameter marked by
	protected @interface SupportParam {
	}

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
	
	public abstract Object doAround(ProceedingJoinPoint point)  throws Throwable;

}
