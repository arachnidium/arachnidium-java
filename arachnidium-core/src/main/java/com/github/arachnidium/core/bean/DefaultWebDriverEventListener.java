package com.github.arachnidium.core.bean;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.github.arachnidium.core.eventlisteners.IWebDriverEventListener;
import com.github.arachnidium.core.highlighting.IWebElementHighlighter;
import com.github.arachnidium.core.highlighting.WebElementHighLighter;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import com.github.arachnidium.util.logging.Log;

public class DefaultWebDriverEventListener implements IWebDriverEventListener {
	
	final IConfigurationWrapper configurationWrapper;
	private final IDestroyable destroyable;
	
	private static enum HowToHighLightElement {
		INFO {
			@Override
			void highLight(IWebElementHighlighter highlighter,
					WebDriver driver, WebElement element, String message) {
				highlighter.highlightAsInfo(driver, element, message);
			}
		},
		DEBUG {
			@Override
			void highLight(IWebElementHighlighter highlighter,
					WebDriver driver, WebElement element, String message) {
				highlighter.highlightAsFine(driver, element, message);
			}
		};

		void highLight(IWebElementHighlighter highlighter, WebDriver driver,
				WebElement element, String message) {
			// does nothing
		}
	}
	
	private final List<WebDriverEventListener> additionalListeners = new ArrayList<WebDriverEventListener>() {
		private static final long serialVersionUID = 1L;
		{
			// it is filled by SPI			
			Iterator<WebDriverEventListener> providers = ServiceLoader.load(
					WebDriverEventListener.class).iterator();
			while (providers.hasNext())
				add((WebDriverEventListener) providers.next());
			
			Iterator<IWebDriverEventListener> extendedProviders = ServiceLoader.load(
					IWebDriverEventListener.class).iterator();
			while (extendedProviders.hasNext())
				add((IWebDriverEventListener) extendedProviders.next());
		}
	};	
	
	private final WebElementHighLighter highLighter = new WebElementHighLighter();
	
	private final IWebDriverEventListener proxyExtendedListener = (IWebDriverEventListener) Proxy
			.newProxyInstance(IWebDriverEventListener.class.getClassLoader(),
					new Class[] { IWebDriverEventListener.class }, (proxy,
							method, args) -> {
						additionalListeners.forEach((eventListener) -> {
							try {
								if (eventListener instanceof IWebDriverEventListener)
									method.invoke(eventListener, args);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}

						});
						return null;
					});

	private final WebDriverEventListener proxyListener = (IWebDriverEventListener) Proxy
			.newProxyInstance(IWebDriverEventListener.class.getClassLoader(),
					new Class[] { IWebDriverEventListener.class }, (proxy,
							method, args) -> {
						additionalListeners.forEach((eventListener) -> {
							try {
								method.invoke(eventListener, args);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}

						});
						return null;
					});
	
	public DefaultWebDriverEventListener(IConfigurationWrapper configurationWrapper,
			IDestroyable destroyable){
		this.configurationWrapper = configurationWrapper;
		this.destroyable = destroyable;
	}
	
	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		Log.message("Attempt to navigate to another url. Required url is "
				+ url);
		proxyListener.beforeNavigateTo(url, driver);
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		Log.message("Current URL is " + driver.getCurrentUrl());
		proxyListener.afterNavigateTo(url, driver);
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		Log.message("Attempt to navigate to previous url. Current url is "
				+ driver.getCurrentUrl());
		proxyListener.beforeNavigateBack(driver);
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		Log.message("Current URL is  " + driver.getCurrentUrl());
		proxyListener.afterNavigateBack(driver);
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		Log.message("Attempt to navigate to next url. Current url is "
				+ driver.getCurrentUrl());
		proxyListener.beforeNavigateForward(driver);
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		Log.message("Current URL is  " + driver.getCurrentUrl());
		proxyListener.afterNavigateForward(driver);
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		Log.debug("Searching for element by locator " + by.toString()
				+ " has been started");
		if (element != null) {
			highlightElementAndLogAction(element, "Using root element",
					HowToHighLightElement.DEBUG);
		}
		proxyListener.beforeFindBy(by, element, driver);
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		Log.debug("Searching for web element has been finished. Locator is "
				+ by.toString());
		if (element != null) {
			highlightElementAndLogAction(element, "Root element was used",
					HowToHighLightElement.DEBUG);
		}
		proxyListener.afterFindBy(by, element, driver);

	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		highlightElementAndLogAction(element,
				"State before element will be clicked on.",
				HowToHighLightElement.INFO);
		proxyListener.beforeClickOn(element, driver);

	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		Log.message("Click on element has been successfully performed!");
		proxyListener.afterClickOn(element, driver);
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		highlightElementAndLogAction(element,
				"State before element value will be changed.",
				HowToHighLightElement.INFO);
		proxyListener.beforeChangeValueOf(element, driver);

	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		highlightElementAndLogAction(element,
				"State after element value was changed.",
				HowToHighLightElement.INFO);
		proxyListener.afterChangeValueOf(element, driver);

	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		proxyListener.beforeScript(script, driver);

	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		proxyListener.afterScript(script, driver);
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		Log.debug("An exception has been caught out."
				+ throwable.getClass().getName() + ":" + throwable.getMessage());
		proxyListener.onException(throwable, driver);

	}

	@Override
	public void afterAlertAccept(WebDriver driver, Alert alert) {
		Log.message("Alert has been accepted");
		proxyExtendedListener.afterAlertAccept(driver, alert);

	}

	@Override
	public void afterAlertDismiss(WebDriver driver, Alert alert) {
		Log.message("Alert has been dismissed");
		proxyExtendedListener.afterAlertDismiss(driver, alert);
	}

	@Override
	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		Log.message("String " + keys + " has been sent to alert");
		proxyExtendedListener.afterAlertSendKeys(driver, alert, keys);
	}

	@Override
	public void afterSubmit(WebDriver driver, WebElement element) {
		Log.message("Submit has been performed successfully");
		proxyExtendedListener.afterSubmit(driver, element);
	}

	@Override
	public void beforeAlertAccept(WebDriver driver, Alert alert) {
		Log.message("Attempt to accept alert...");
		proxyExtendedListener.beforeAlertAccept(driver, alert);
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver, Alert alert) {
		Log.message("Attempt to dismiss the alert...");
		proxyExtendedListener.beforeAlertDismiss(driver, alert);
	}

	@Override
	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys) {
		Log.message("Attemt to send string " + keys + " to alert...");
		proxyExtendedListener.beforeAlertSendKeys(driver, alert, keys);
	}

	@Override
	public void beforeSubmit(WebDriver driver, WebElement element) {
		highlightElementAndLogAction(element,
				"State before submit will be performed by element: ",
				HowToHighLightElement.INFO);
		proxyExtendedListener.beforeSubmit(driver, element);
	}


	@Override
	public void beforeQuit(WebDriver driver) {
		destroyable.destroy();
		proxyExtendedListener.beforeQuit(driver);
	}
	
	private void highlightElementAndLogAction(WebElement element,
			String logMessage, HowToHighLightElement howToHighLightElement) {
		String elementDescription = elementDescription(element);
		highLighter.resetAccordingTo(configurationWrapper
				.getWrappedConfiguration());
		howToHighLightElement.highLight(highLighter, ((WrapsDriver) element).getWrappedDriver(), element,
				logMessage + elementDescription);
	}
	
	private String elementDescription(WebElement element) {
		String description = "";
		if (element == null)
			return description;

		if (!String.valueOf(element.getTagName()).equals(""))
			description += "tag:" + String.valueOf(element.getTagName());
		description = addToDescription(element, "id", description);
		description = addToDescription(element, "name", description);
		if (!"".equals(element.getText()))
			description += " ('" + String.valueOf(element.getText()) + "')";
		if (!description.equals(""))
			description = " Element is: " + description;

		return description;
	}
	
	private String addToDescription(WebElement element, String attribute,
			String description) {
		try {
			if (element.getAttribute(attribute) == null)
				return description;
			if (element.getAttribute(attribute).equals(""))
				return description;
			description += " " + attribute + ": "
					+ String.valueOf(element.getAttribute(attribute));
		} catch (Exception e) {
		}
		return description;
	}
	

}
