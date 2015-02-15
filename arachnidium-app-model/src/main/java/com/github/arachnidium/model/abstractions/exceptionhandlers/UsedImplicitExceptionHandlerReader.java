package com.github.arachnidium.model.abstractions.exceptionhandlers;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.arachnidium.util.reflect.annotations.AnnotationUtil;

/**
 * This is used for the reading of {@link UsedImplicitExceptionHandlers}
 * declarations
 */
public final class UsedImplicitExceptionHandlerReader {
	
	private UsedImplicitExceptionHandlerReader(){
		super();
	}
	
	public static List<ModelObjectExceptionHandler> getDeclaredExceptionHandlers(AnnotatedElement annotated){
		
		UsedImplicitExceptionHandlers[] annotations = null;		
		if (!Class.class.isAssignableFrom(annotated.getClass())){
			annotations = AnnotationUtil.getAnnotations(
					UsedImplicitExceptionHandlers.class, annotated);
		}else{
			annotations = AnnotationUtil.getAnnotations(
					UsedImplicitExceptionHandlers.class, (Class<?>) annotated, true);
		}		
		
		List<ModelObjectExceptionHandler> result = new ArrayList<>();
		if (annotations.length != 0) {
			UsedImplicitExceptionHandlers ueh = annotations[0];
			List<Class<? extends ModelObjectExceptionHandler>> throwableHandlers = Arrays
					.asList(ueh.areUsed());
			throwableHandlers.forEach((handler) -> {
				try {
					result.add(handler.newInstance());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});			
		 }
		
		return result;
	}
}
