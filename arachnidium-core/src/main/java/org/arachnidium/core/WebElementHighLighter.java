package org.arachnidium.core;

import java.awt.Color;

import org.arachnidium.core.interfaces.IWebElementHighlighter;
import org.arachnidium.core.webdriversettings.ScreenShots;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IConfigurable;
import org.arachnidium.util.logging.Log;
import org.arachnidium.util.logging.Photographer;
import org.arachnidium.util.logging.eAvailableLevels;
import org.arachnidium.util.logging.eLogColors;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

/**
 * @author s.tihomirov it can highlight elements and do screenshots
 */
public class WebElementHighLighter implements IConfigurable, IWebElementHighlighter {

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

	@Override
	public synchronized void highlightAsFine(WebDriver driver,
			WebElement webElement, Color highlight, String Comment) {
		highlightelement(driver, webElement, highlight, eAvailableLevels.FINE,
				Comment);
	}

	@Override
	public synchronized void highlightAsFine(WebDriver driver,
			WebElement webElement, String Comment) {
		highlightelement(driver, webElement,
				eLogColors.DEBUGCOLOR.getStateColor(), eAvailableLevels.FINE,
				Comment);
	}

	@Override
	public synchronized void highlightAsInfo(WebDriver driver,
			WebElement webElement, Color highlight, String Comment) {
		highlightelement(driver, webElement, highlight, eAvailableLevels.INFO,
				Comment);
	}

	@Override
	public synchronized void highlightAsInfo(WebDriver driver,
			WebElement webElement, String Comment) {
		highlightelement(driver, webElement,
				eLogColors.CORRECTSTATECOLOR.getStateColor(),
				eAvailableLevels.INFO, Comment);
	}

	@Override
	public synchronized void highlightAsSevere(WebDriver driver,
			WebElement webElement, Color highlight, String Comment) {
		highlightelement(driver, webElement, highlight,
				eAvailableLevels.SEVERE, Comment);
	}

	@Override
	public synchronized void highlightAsSevere(WebDriver driver,
			WebElement webElement, String Comment) {
		highlightelement(driver, webElement,
				eLogColors.SEVERESTATECOLOR.getStateColor(),
				eAvailableLevels.SEVERE, Comment);
	}

	@Override
	public synchronized void highlightAsWarning(WebDriver driver,
			WebElement webElement, Color highlight, String Comment) {
		highlightelement(driver, webElement, highlight, eAvailableLevels.WARN,
				Comment);
	}

	@Override
	public synchronized void highlightAsWarning(WebDriver driver,
			WebElement webElement, String Comment) {
		highlightelement(driver, webElement,
				eLogColors.WARNSTATECOLOR.getStateColor(),
				eAvailableLevels.WARN, Comment);
	}

	private void highlightelement(WebDriver driver, WebElement webElement,
			Color color, eAvailableLevels LogLevel, String Comment) {
		try {
			String originalStyle = getOriginalStyle(webElement);
			setNewColor((JavascriptExecutor) driver, webElement,
					"4px solid rgb(" + Integer.toString(color.getRed()) + ","
							+ Integer.toString(color.getGreen()) + ","
							+ Integer.toString(color.getBlue()) + ")");
			if (toDoScreenShots)
				Photographer.takeAPictureForLog(driver, LogLevel, Comment);
			else
				Log.log(LogLevel, Comment);
			setStyle((JavascriptExecutor) driver, webElement, originalStyle);
		} // There is a problem with mobile applications. Not all locators are
			// supported
		catch (WebDriverException e) {
			if (toDoScreenShots)
				Photographer.takeAPictureForLog(driver, LogLevel, Comment);
			else
				Log.log(LogLevel, Comment);
		}
	}

	@Override
	public synchronized void resetAccordingTo(Configuration config) {
		Boolean toDoScreenShots = config.getSection(ScreenShots.class)
				.getToDoScreenShotsOnElementHighLighting();
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
