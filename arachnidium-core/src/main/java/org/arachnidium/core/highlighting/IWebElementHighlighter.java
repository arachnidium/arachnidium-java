package org.arachnidium.core.highlighting;

import java.awt.Color;
import java.util.logging.Level;

import org.arachnidium.util.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * There is an assumption that implementors will
 * highlight HTML elements and generate {@link Log} messages
 */
public interface IWebElementHighlighter {
	/**
	 * HTML element will be highlighted any color you want
	 * and FINE {@link Level} {@link Log} message will be 
	 * created
	 */
	public void highlightAsFine(WebDriver driver, WebElement webElement,
			Color highlight, String comment);

	/**
	 * HTML element will be highlighted 
	 * and FINE {@link Level} {@link Log} message will be 
	 * created. Color of highlighting will be defined by implementor
	 */
	public void highlightAsFine(WebDriver driver, WebElement webElement,
			String comment);

	/**
	 * HTML element will be highlighted any color you want
	 * and INFO {@link Level} {@link Log} message will be 
	 * created
	 */
	public void highlightAsInfo(WebDriver driver, WebElement webElement,
			Color highlight, String comment);

	/**
	 * HTML element will be highlighted 
	 * and INFO {@link Level} {@link Log} message will be 
	 * created. Color of highlighting will be defined by implementor
	 */
	public void highlightAsInfo(WebDriver driver, WebElement webElement,
			String Comment);

	/**
	 * HTML element will be highlighted any color you want
	 * and SEVERE {@link Level} {@link Log} message will be 
	 * created
	 */
	public void highlightAsSevere(WebDriver driver, WebElement webElement,
			Color highlight, String comment);

	/**
	 * HTML element will be highlighted 
	 * and SEVERE {@link Level} {@link Log} message will be 
	 * created. Color of highlighting will be defined by implementor
	 */
	public void highlightAsSevere(WebDriver driver, WebElement webElement,
			String comment);

	/**
	 * HTML element will be highlighted any color you want
	 * and WARNING {@link Level} {@link Log} message will be 
	 * created
	 */
	public void highlightAsWarning(WebDriver driver, WebElement webElement,
			Color highlight, String comment);

	/**
	 * HTML element will be highlighted 
	 * and WARNING {@link Level} {@link Log} message will be 
	 * created. Color of highlighting will be defined by implementor
	 */
	public void highlightAsWarning(WebDriver driver, WebElement webElement,
			String comment);
}
