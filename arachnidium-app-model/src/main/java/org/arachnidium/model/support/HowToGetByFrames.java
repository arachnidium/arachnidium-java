package org.arachnidium.model.support;

import java.util.ArrayList;
import java.util.List;

import org.arachnidium.core.WebDriverEncapsulation;import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Manages switching between frames on a page or context of the hybrid 
 * mobile application
 */
public class HowToGetByFrames {

	final static Class<?>[] availableClassesOfFrameIdentifiers = new Class<?>[] {
	String.class, int.class, By.class, WebElement.class};
	final ArrayList<Object> framePath = new ArrayList<Object>();

	public WebDriverEncapsulation switchTo(WebDriverEncapsulation webDriverEncapsulation) {
		framePath.forEach((frameIdentifier) -> {
			
			WebDriver wrapped = webDriverEncapsulation.getWrappedDriver();
			if (frameIdentifier instanceof String) {
				wrapped.switchTo().frame(String.valueOf(frameIdentifier));
				return;
			}
			
			if (frameIdentifier instanceof Integer) {
				wrapped.switchTo().frame((int) frameIdentifier);
				return;
			}
			
			if (frameIdentifier instanceof By) {
				wrapped.switchTo().frame(wrapped.findElement((By) frameIdentifier));
				return;
			}
			
			
			
		});
		return webDriverEncapsulation;
	}

	/**
	 * Collects sequential frame path
	 * @param frameIdentifier is object of {@link String}, {@link int}, {@link By} or {@link WebElement}
	 * @throws IllegalArgumentException
	 */
	public void addNextFrame(Object frameIdentifier) throws IllegalArgumentException{
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

	public List<Object> getFramePath(){
		return framePath;
	}
}
