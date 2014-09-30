package com.github.arachnidium.core.highlighting;

import java.awt.Color;

import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.interfaces.IConfigurable;
import com.github.arachnidium.util.logging.Log;
import com.github.arachnidium.util.logging.Photographer;
import com.github.arachnidium.util.logging.eAvailableLevels;
import com.github.arachnidium.util.logging.eLogColors;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import com.github.arachnidium.core.settings.ScreenShots;

/**
 * The default implementor of {@link IWebElementHighlighter}
 */
public class WebElementHighLighter implements IConfigurable,
		IWebElementHighlighter {

	// is this doing screenshots
	private boolean toDoScreenShots;
	private final boolean isDoingScreenShotsByDefault = true;

	private void execDecorativeScript(JavascriptExecutor scriptExecutor,
			WebElement element, String script) throws InterruptedException {
		try {
			scriptExecutor.executeScript(script, element);
		} catch (ClassCastException e) {
			scriptExecutor.executeScript(script,
					((WrapsElement) element).getWrappedElement());
		}
		Thread.sleep(100);
	}

	private String getOriginalStyle(WebElement elementToBeHiglighted) {
		return elementToBeHiglighted.getAttribute("style");
	}

	/**
	 * Optionally takes screenshots.
	 * 
	 * @see com.github.arachnidium.core.highlighting.IWebElementHighlighter#highlightAsFine(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement, java.awt.Color, java.lang.String)   
	 */
	@Override
	public synchronized void highlightAsFine(WebDriver driver,
			WebElement webElement, Color highlight, String comment) {
		highlightelement(driver, webElement, highlight, eAvailableLevels.FINE,
				comment);
	}

	/**
	 * Optionally takes screenshots.
	 * Highlighting color is {@link eLogColors#DEBUGCOLOR}
	 * 
	 * @see com.github.arachnidium.core.highlighting.IWebElementHighlighter#highlightAsFine(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement, java.lang.String)
	 *   
	 */
	@Override
	public synchronized void highlightAsFine(WebDriver driver,
			WebElement webElement, String comment) {
		highlightelement(driver, webElement,
				eLogColors.DEBUGCOLOR.getStateColor(), eAvailableLevels.FINE,
				comment);
	}

	/**
	  * Optionally takes screenshots.
	 * @see com.github.arachnidium.core.highlighting.IWebElementHighlighter#highlightAsInfo(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement, java.awt.Color, java.lang.String)
	 */
	@Override
	public synchronized void highlightAsInfo(WebDriver driver,
			WebElement webElement, Color highlight, String comment) {
		highlightelement(driver, webElement, highlight, eAvailableLevels.INFO,
				comment);
	}

	/**
	 * Optionally takes screenshots.
	 * Highlighting color is {@link eLogColors#CORRECTSTATECOLOR}   
	 * 
	 * @see com.github.arachnidium.core.highlighting.IWebElementHighlighter#highlightAsInfo(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement, java.lang.String) 
	 */
	@Override
	public synchronized void highlightAsInfo(WebDriver driver,
			WebElement webElement, String comment) {
		highlightelement(driver, webElement,
				eLogColors.CORRECTSTATECOLOR.getStateColor(),
				eAvailableLevels.INFO, comment);
	}

	/**
	 * Optionally takes screenshots.
	 * 
	 * @see com.github.arachnidium.core.highlighting.IWebElementHighlighter#highlightAsSevere(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement, java.awt.Color, java.lang.String)
	 */
	@Override
	public synchronized void highlightAsSevere(WebDriver driver,
			WebElement webElement, Color highlight, String comment) {
		highlightelement(driver, webElement, highlight,
				eAvailableLevels.SEVERE, comment);
	}

	/**
	 * Optionally takes screenshots.
	 * Highlighting color is {@link eLogColors#SEVERESTATECOLOR} 
	 *  
	 * @see com.github.arachnidium.core.highlighting.IWebElementHighlighter#highlightAsSevere(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement, java.lang.String)   
	 */
	@Override
	public synchronized void highlightAsSevere(WebDriver driver,
			WebElement webElement, String comment) {
		highlightelement(driver, webElement,
				eLogColors.SEVERESTATECOLOR.getStateColor(),
				eAvailableLevels.SEVERE, comment);
	}

	/**
	 * Optionally takes screenshots.
	 * 
	 * @see com.github.arachnidium.core.highlighting.IWebElementHighlighter#highlightAsWarning(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement, java.awt.Color, java.lang.String)
	 */
	@Override
	public synchronized void highlightAsWarning(WebDriver driver,
			WebElement webElement, Color highlight, String comment) {
		highlightelement(driver, webElement, highlight, eAvailableLevels.WARN,
				comment);
	}

	/**
	 * Optionally takes screenshots.
	 * Highlighting color is {@link eLogColors#WARNSTATECOLOR}   
	 * 
	 * @see com.github.arachnidium.core.highlighting.IWebElementHighlighter#highlightAsWarning(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement, java.lang.String)  
	 */
	@Override
	public synchronized void highlightAsWarning(WebDriver driver,
			WebElement webElement, String comment) {
		highlightelement(driver, webElement,
				eLogColors.WARNSTATECOLOR.getStateColor(),
				eAvailableLevels.WARN, comment);
	}

	private void highlightelement(WebDriver driver, WebElement webElement,
			Color color, eAvailableLevels LogLevel, String comment) {
		try {
			String originalStyle = getOriginalStyle(webElement);
			setNewColor((JavascriptExecutor) driver, webElement,
					"4px solid rgb(" + Integer.toString(color.getRed()) + ","
							+ Integer.toString(color.getGreen()) + ","
							+ Integer.toString(color.getBlue()) + ")");
			if (toDoScreenShots)
				Photographer.takeAPictureForLog(driver, LogLevel, comment);
			else
				Log.log(LogLevel, comment);
			setStyle((JavascriptExecutor) driver, webElement, originalStyle);
		} // There is a problem with mobile applications. Not all locators are
			// supported
		catch (WebDriverException e) {
			if (toDoScreenShots)
				Photographer.takeAPictureForLog(driver, LogLevel, comment);
			else
				Log.log(LogLevel, comment);
		}
	}

	@Override
	public synchronized void resetAccordingTo(Configuration config) {
		Boolean toDoScreenShots = config.getSection(ScreenShots.class)
				.getToTakeScreenShotsOnElementHighLighting();
		if (toDoScreenShots == null)
			this.toDoScreenShots = isDoingScreenShotsByDefault;
		else
			this.toDoScreenShots = toDoScreenShots;
	}

	private void setNewColor(JavascriptExecutor scriptExecutor,
			WebElement elementToBeHiglighted, String colorExpression) {
		try {
			execDecorativeScript(scriptExecutor, elementToBeHiglighted,
					"arguments[0].style.border = '" + colorExpression + "'");
		} catch (InterruptedException | StaleElementReferenceException e) {
		}
	}

	private void setStyle(JavascriptExecutor scriptExecutor,
			WebElement elementToBeHiglighted, String style) {
		try {
			execDecorativeScript(scriptExecutor, elementToBeHiglighted,
					"arguments[0].setAttribute('style', '" + style + "');");
		} catch (InterruptedException | StaleElementReferenceException e) {
		}
	}

}
