package org.arachnidium.model.support;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;

/**
 * This manages switching between frames on a browser page or web view of a hybrid mobile
 * application
 * 
 * @see TargetLocator
 */
public class HowToGetByFrames {

	final static ArrayList<Class<?>> availableClassesOfFrameIdentifiers =  new ArrayList<Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(String.class);
			add(Integer.class); 
			add(By.class);
			add(WebElement.class); 
		}
	};
	final ArrayList<Object> framePath = new ArrayList<Object>();

	/**
	 * This method performs the switching from
	 * one to another specified frame
	 * 
	 * @param driver is the instance of {@link WebDriver} 
	 * On this instance the switching is performed
	 * 
	 * @see TargetLocator
	 */
	public void switchTo(WebDriver driver) {
		framePath.forEach((frameIdentifier) -> {
			if (frameIdentifier instanceof String) {
				driver.switchTo().frame(String.valueOf(frameIdentifier));
				return;
			}

			if (frameIdentifier instanceof Integer) {
				driver.switchTo().frame((int) frameIdentifier);
				return;
			}

			if (frameIdentifier instanceof By) {
				driver.switchTo().frame(
						driver.findElement((By) frameIdentifier));
				return;
			}
			
			if (frameIdentifier instanceof WebElement) {
				driver.switchTo().frame((WebElement) frameIdentifier);
				return;
			}			
		});
	}

	/**
	 * Collects sequential frame path
	 * 
	 * @param frameIdentifier
	 *            is object of {@link String}, {@link Integer}, {@link By} or
	 *            {@link WebElement}
	 * @throws IllegalArgumentException
	 */
	public void addNextFrame(Object frameIdentifier)
			throws IllegalArgumentException {
		for (Class<?> classOfAFrameIdentifier : availableClassesOfFrameIdentifiers) {
			if (!classOfAFrameIdentifier.isAssignableFrom(frameIdentifier
					.getClass())) {
				continue;
			}
			framePath.add(frameIdentifier);
			return;
		}
		throw new IllegalArgumentException("Object of the type "
				+ frameIdentifier.getClass().getName()
				+ " are not alowed! Available classes are "
				+ availableClassesOfFrameIdentifiers.toString());
	}

	public List<Object> getFramePath() {
		return framePath;
	}
}
