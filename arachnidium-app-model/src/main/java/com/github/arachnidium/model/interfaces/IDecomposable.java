package com.github.arachnidium.model.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.arachnidium.core.HowToGetByFrames;

/**
 * There is an assumption that any object (whole application, page/screen and so on)
 * could be decomposed to limited set of logically final and reusable parts.
 * 
 * This interface specifies decomposition model 
 * provided by Arachnidium Java framework.
 */

public interface IDecomposable {

	/**
	 * This method should provide the getting of more specific 
	 * part from another which is more generalized. 
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * 
	 * @return The instance of required class specified by <code>partClass</code> 
	 * parameter 
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass);

	
	/**
	 * This method should provide the getting of more specific 
	 * part from another which is more generalized. 
	 * 
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * 
	 * @param pathStrategy is a path to frame which is specified by {@link HowToGetByFrames} 
	 * 
	 * @return The instance of required class specified by @param partClass 
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames pathStrategy);
	
	/**
	 * This method should provide the getting of more specific 
	 * part from another which is more generalized. <br/><br/>
	 * The root element is defined {@link By} locator strategy
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * @return The instance of required class specified by @param partClass 
	 */
	public <T extends IDecomposable> T getPart(Class<T> partClass, By by);
	
	
	/**
	 * This method should provide the getting of more specific 
	 * part from another which is more generalized. 
	 * 
	 * As it is all about UI of browser and mobile applications, 
	 * the usage of frames is possible (browser applications, hybrid mobile clients).
	 * So this fact is considered by method.<br/><br/>
	 * The root element is defined {@link By} locator strategy
	 * 
	 * @param partClass is required class that implements {@link IDecomposable}
	 * @param pathStrategy is a path to frame which is specified by {@link HowToGetByFrames} 
	 * @param by Is a locator strategy which is used to get the root {@link WebElement}
	 * 
	 * @return The instance of required class specified by @param partClass 
	 */	
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			HowToGetByFrames pathStrategy, By by);	
}
