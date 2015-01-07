package com.github.arachnidium.model.support.annotations.rootelements;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.openqa.selenium.By;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.util.reflect.annotations.AnnotationUtil;

/**
 * Reads class declarations and returns 
 * {@link By} instances when class is marked by
 * {@link RootElement}, {@link RootAndroidElement},
 * {@link RootIOSElement}
 *
 */
public interface IRootElementReader {
		
	default <T extends Annotation> T[] getAnnotations(Class<? extends Annotation> requiredAnnotation, AnnotatedElement target){
		if (!Class.class.isAssignableFrom(target.getClass())){
			return AnnotationUtil.getAnnotations(requiredAnnotation, target);
		}
		
		return AnnotationUtil.getAnnotations(requiredAnnotation, (Class<?>) target, true);
	}
	
	/**
	 * This method should return {@link By} if the given class is marked by
	 * {@link RootElement}, {@link RootAndroidElement},
     * {@link RootIOSElement}
	 */
	public By readClassAndGetBy(AnnotatedElement annotatedTarget, ESupportedDrivers supportedDriver);
}
