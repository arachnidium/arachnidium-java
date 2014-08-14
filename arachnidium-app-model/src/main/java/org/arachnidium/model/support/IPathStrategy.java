package org.arachnidium.model.support;

import java.util.ArrayList;
import java.util.List;
import org.arachnidium.core.WebDriverEncapsulation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * An abstraction which represents something
 * that can manage switching between frames and handles (windows, tabs, mobile contexts) 
 * By default there is a frame path
 */
public interface IPathStrategy extends Cloneable{
	final ArrayList<Object> framePath = new ArrayList<Object>();
	final static Class<?>[] availableClassesOfFrameIdentifiers = new Class<?>[] {
			String.class, int.class, By.class, WebElement.class};	
	/**
	 * Collects sequential frame path
	 * @param frameIdentifier is object of {@link String}, {@link int}, {@link By} or {@link WebElement}
	 * @throws IllegalArgumentException
	 */
	default void addNextFrame(Object frameIdentifier) throws IllegalArgumentException{
		for (Class<?> classOfAFrameIdentifier: availableClassesOfFrameIdentifiers){
			if (!classOfAFrameIdentifier.isAssignableFrom(frameIdentifier.getClass())){
				continue;
			}
			framePath.add(frameIdentifier);
			return;
		}
		throw new IllegalArgumentException("Object of the type " + frameIdentifier.getClass().getName() + " are not alowed! Available classes are " + 
		availableClassesOfFrameIdentifiers.toString());
	}	
	
	default List<Object> getFramePath(){
		return framePath;
	}
	
	public <T extends Object> T switchTo(WebDriverEncapsulation webDriverEncapsulation);
}
