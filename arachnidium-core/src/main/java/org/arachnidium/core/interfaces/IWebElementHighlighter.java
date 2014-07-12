package org.arachnidium.core.interfaces;

import java.awt.Color;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author s.tihomirov it is possible that classes implements this will
 *         highlight elements on a page and generate log messages
 */
public interface IWebElementHighlighter {
	/**
	 * @author s.tihomirov It highlights element any color you want. I thing
	 *         that log messages of FINE level will be generated
	 */
	public void highlightAsFine(WebDriver driver, WebElement webElement,
			Color highlight, String Comment);

	/**
	 * @author s.tihomirov It highlights element any color you have specified by
	 *         this method implementation. I thing that log messages of FINE
	 *         level will be generated
	 */
	public void highlightAsFine(WebDriver driver, WebElement webElement,
			String Comment);

	/**
	 * @author s.tihomirov It highlights element any color you want. I thing
	 *         that log messages of INFO level will be generated
	 */
	public void highlightAsInfo(WebDriver driver, WebElement webElement,
			Color highlight, String Comment);

	/**
	 * @author s.tihomirov It highlights element any color you have specified by
	 *         this method implementation. I thing that log messages of INFO
	 *         level will be generated
	 */
	public void highlightAsInfo(WebDriver driver, WebElement webElement,
			String Comment);

	/**
	 * @author s.tihomirov It highlights element any color you want. I thing
	 *         that log messages of SEVERE level will be generated
	 */
	public void highlightAsSevere(WebDriver driver, WebElement webElement,
			Color highlight, String Comment);

	/**
	 * @author s.tihomirov It highlights element any color you have specified by
	 *         this method implementation. I thing that log messages of SEVERE
	 *         level will be generated
	 */
	public void highlightAsSevere(WebDriver driver, WebElement webElement,
			String Comment);

	/**
	 * @author s.tihomirov It highlights element any color you want. I thing
	 *         that log messages of WARNING level will be generated
	 */
	public void highlightAsWarning(WebDriver driver, WebElement webElement,
			Color highlight, String Comment);

	/**
	 * @author s.tihomirov It highlights element any color you have specified by
	 *         this method implementation. I thing that log messages of WARNING
	 *         level will be generated
	 */
	public void highlightAsWarning(WebDriver driver, WebElement webElement,
			String Comment);
}
