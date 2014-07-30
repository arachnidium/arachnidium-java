package org.arachnidium.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;

/**
 * This aspect is used by listeners
 */
public abstract class AbstractAspect {

	protected static enum WhenLaunch {
		BEFORE(BeforeTargets.class), AFTER(AfterTargets.class);
		/**
		 * I use only @BeforeTarget and @AfterTarget here
		 */
		private final Class<? extends Annotation> annotation;
		
		private WhenLaunch(Class<? extends Annotation> annotation){
			this.annotation = annotation;
		}
		
		private Annotation[] getAnnotations(Method m){
			return m.getAnnotationsByType(annotation);
		}
	}

	/**
	 * By this annotations methods of all listeners
	 * Should be marked
	 */
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	@Repeatable(BeforeTargets.class)
	protected @interface BeforeTarget {
		Class<?>[] targetClasses () default {};
		String[] targetMethods () default {};
		Class<?>[] targetArgTypes() default {};
		int[] usedParamNumbers() default {};
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
		Class<?>[] targetClasses () default {};
		String[] targetMethods () default {};
		Class<?>[] targetArgTypes() default {};
		int[] usedParamNumbers() default {};
	}	
	
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	private @interface AfterTargets {
		AfterTarget[] value();
	}
	
	@Target(value = ElementType.PARAMETER)
	@Retention(value = RetentionPolicy.RUNTIME) //listenable target
												//that can be used as a parameter value
	protected @interface TargetParam {
	}	
	
	@Target(value = ElementType.FIELD)
	@Retention(value = RetentionPolicy.RUNTIME) //This field value can be inserted as a parameter												
	protected @interface SupportField {
	}
	
	@Target(value = ElementType.PARAMETER)
	@Retention(value = RetentionPolicy.RUNTIME) //Field marked by @SuppotField 
	//can be inserted as a parameter marked by
	protected @interface SupportParam {
	}		
	/**
	 * *****************************************
	 * *****************************************
	 */
	
	public static void setSupportField(Object valueForSupport, Object aspectObject){
		Field[] declaredFields = aspectObject.getClass().getDeclaredFields();
		for (Field f: declaredFields){
			if (!f.isAnnotationPresent(SupportField.class)){
				continue;
			}
			
			if (!f.getDeclaringClass().isAssignableFrom(valueForSupport.getClass())){
				continue;
			}
			try {
				f.set(aspectObject, valueForSupport);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	protected static Object getSupportField(Object aspectObject, Class<?> requiredClass){
		Field[] declaredFields = aspectObject.getClass().getDeclaredFields();
		for (Field f: declaredFields){
			if (!f.isAnnotationPresent(SupportField.class)){
				continue;
			}
			
			if (!f.getDeclaringClass().isAssignableFrom(requiredClass)){
				continue;
			}
			try {
				return f.get(aspectObject);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
	/**
	 * Returns method of listener that should be executed before/after target method
	 */
	protected static Method getListenerMethod(JoinPoint joinPoint, Object aspectObject, WhenLaunch when){
		//TODO Stub
		return null;
	}
	
}
