package com.github.arachnidium.model.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public interface IDefaultAnnotationReader {
	
	/**
	 * Attempts to return
	 * annotations which mark class or superclass of the given class 
	 */
	@SuppressWarnings("unchecked")
	default <T extends Annotation> T[] getAnnotations(Class<? extends Annotation> requiredAnnotation, AnnotatedElement target){
		T[] result = (T[]) target.getAnnotationsByType(requiredAnnotation);
		if (!Class.class.isAssignableFrom(target.getClass())){
			return result;
		}
		Class<?> superC = ((Class<?>) target).getSuperclass();
		while (result.length == 0 && superC != null){
			result = (T[]) superC.getAnnotationsByType(requiredAnnotation);
			superC = superC.getSuperclass();
		}
		return result;
	}

}
