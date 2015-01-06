package com.github.arachnidium.util.reflect.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class facilitates the annotation reading
 */
public final class AnnotationUtil {

	private static final Class<?>[] ANNOTATION_METHOD_PARAM_CLASSES = new Class<?>[] {};
	private static final Object[] ANNOTATION_METHOD_PARAM_VALUES = new Object[] {};

	private AnnotationUtil(){
		super();
	}
	
	/**
	 * Reads the annotation parameter using the required method name
	 * Actually each annotation parameter is a method.
	 * 
	 * @param a is the readable annotation
	 * @param methodName is the required method/parameter name
	 * @return the value which was read
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getValue(Annotation a, String methodName) {
		try {
			Method m = a.getClass().getMethod(methodName,
					ANNOTATION_METHOD_PARAM_CLASSES);
			return (T) m.invoke(a, ANNOTATION_METHOD_PARAM_VALUES);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Reads {@link AnnotatedElement} e.g. {@link Field}, {@link Class} or {@link Method}
	 * and returns the array of required {@link Annotation} subclass instances 
	 * 
	 * @param requiredAnnotation is the {@link Annotation} subclass whose instances should be returned
	 * @param target is a {@link Field}, {@link Class} or {@link Method} which is supposed to be annotated
	 * @return an array of required {@link Annotation} subclass instances 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T[] getAnnotations(Class<? extends Annotation> requiredAnnotation, AnnotatedElement target){
		T[] result = (T[]) target.getAnnotationsByType(requiredAnnotation);
		return result;
	}
	
	/**
	 * Reads the given {@link Class} 
	 * and returns the array of required {@link Annotation} subclass instances
	 * 
	 * @param requiredAnnotation is the {@link Annotation} subclass whose instances should be returned
	 * @param target is a {@link Class} which is supposed to be annotated
	 * @param fromSuperClasses is the flag that means that superclasses can be annotated and 
	 * result should be returned using the next annotated superclasses if the target class is not 
	 * annotated.
	 *  
	 * @return an array of required {@link Annotation} subclass instances 
	 */
	public static <T extends Annotation> T[] getAnnotations(Class<? extends Annotation> requiredAnnotation, Class<?> target, 
			boolean fromSuperClasses){
		T[] result = getAnnotations(requiredAnnotation, target);
		if (!fromSuperClasses){
			return result;
		}
		
		Class<?> superC = target.getSuperclass();
		while (result.length == 0 && superC != null){
			result = getAnnotations(requiredAnnotation, superC);
			superC = superC.getSuperclass();			
		}
		return result;
	}
	
}
