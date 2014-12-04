package com.github.arachnidium.model.support.annotations.rootelements;

import java.lang.reflect.AnnotatedElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.arachnidium.model.support.IDefaultAnnotationReader;

/**
 * Reads class declarations and returns 
 * {@link By} instances when class is marked by
 * {@link RootElement}, {@link RootAndroidElement},
 * {@link RootIOSElement}
 *
 */
public interface IRootElementReader extends IDefaultAnnotationReader{
		
	/**
	 * This method should return {@link By} if the given class is marked by
	 * {@link RootElement}, {@link RootAndroidElement},
     * {@link RootIOSElement}
	 */
	public By readClassAndGetBy(AnnotatedElement annotatedTarget, Class<? extends WebDriver> driverClass);
}
