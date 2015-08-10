package com.github.arachnidium.core.bean;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import com.github.arachnidium.util.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.springframework.context.support.AbstractApplicationContext;

import com.github.arachnidium.core.eventlisteners.IWebDriverEventListener;
import com.github.arachnidium.core.highlighting.IWebElementHighlighter;
import com.github.arachnidium.core.highlighting.WebElementHighLighter;
import com.github.arachnidium.core.interfaces.IDestroyable;

@Aspect
class AspectWebDriverEventListener extends AbstractAspect implements
		IWebDriverEventListener {

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

	private static final List<Class<?>> listenable = new ArrayList<Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(WebDriver.class);
			add(WebElement.class);
			add(Navigation.class);
			add(TargetLocator.class);
			add(ContextAware.class);
			add(Alert.class);
			add(Options.class);
		}
	};

	@SupportField
	private final WebDriver driver;
	private final WebElementHighLighter highLighter = new WebElementHighLighter();
	private final AbstractApplicationContext context;
	private final String POINTCUT_VALUE = "execution(* org.openqa.selenium.WebDriver.*(..)) || "
			+ "execution(* org.openqa.selenium.WebElement.*(..)) ||"
			+ "execution(* org.openqa.selenium.WebDriver.Navigation.*(..)) || "
			+ "execution(* org.openqa.selenium.WebDriver.Options.*(..)) || "
			+ "execution(* org.openqa.selenium.WebDriver.TargetLocator.*(..)) || "
			+ "execution(* org.openqa.selenium.JavascriptExecutor.*(..)) || "
			+ "execution(* org.openqa.selenium.ContextAware.*(..)) || "
			+ "execution(* org.openqa.selenium.Alert.*(..)) || "
			+ "execution(* io.appium.java_client.MobileElement.*(..)) || "
			+ "execution(* io.appium.java_client.AppiumDriver.*(..)) || "
			+ "execution(* io.appium.java_client.android.AndroidDriver.*(..)) || "
			+ "execution(* io.appium.java_client.ios.IOSDriver.*(..)) || "
		    + "execution(* io.appium.java_client.android.AndroidElement.*(..)) || "
			+ "execution(* io.appium.java_client.ios.IOSElement.*(..))"
			;
	private final IDestroyable destroyable;

	private final List<IWebDriverEventListener> additionalListeners = new ArrayList<IWebDriverEventListener>() {
		private static final long serialVersionUID = 1L;
		{
			// it is filled by SPI
			Iterator<?> providers = ServiceLoader.load(
					IWebDriverEventListener.class).iterator();
			while (providers.hasNext())
				add((IWebDriverEventListener) providers.next());
		}
	};

	private final IWebDriverEventListener proxyListener = (IWebDriverEventListener) Proxy
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

	public AspectWebDriverEventListener(final WebDriver driver,
			IConfigurationWrapper configurationWrapper,
			IDestroyable destroyable, AbstractApplicationContext context) {
		super(configurationWrapper);
		this.driver = driver;
		this.context = context;
		this.destroyable = destroyable;
	}

	private static Class<?> getClassForProxy(Class<?> classOfObject) {
		for (Class<?> c : listenable) {
			if (!c.isAssignableFrom(classOfObject)) {
				continue;
			}
			return c;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> T getListenable(Object object) {
		Class<?> classForProxy = getClassForProxy(object.getClass());
		if (classForProxy != null) {
			return (T) classForProxy.cast(object);
		}
		return null;
	}

	//@BeforeTarget(targetClass = WebDriver.class, targetMethod = "get")
	//@BeforeTarget(targetClass = Navigation.class, targetMethod = "to")
	// url can be an instance of String of URL
	public void beforeNavigateTo(@UseParameter(number = 0) Object url,
			@SupportParam WebDriver driver) {
		beforeNavigateTo(String.valueOf(url), driver);
	}

	//@AfterTarget(targetClass = WebDriver.class, targetMethod = "get")
	//@AfterTarget(targetClass = Navigation.class, targetMethod = "to")
	// url can be an instance of String of URL
	public void afterNavigateTo(@UseParameter(number = 0) Object url,
			@SupportParam WebDriver driver) {
		afterNavigateTo(String.valueOf(url), driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateTo(java.lang.String,
	 *      org.openqa.selenium.WebDriver)
	 */
	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		Log.message("Attempt to navigate to another url. Required url is "
				+ url);
		proxyListener.beforeNavigateTo(url, driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateTo(java.lang.String,
	 *      org.openqa.selenium.WebDriver)
	 */
	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		Log.message("Current URL is " + driver.getCurrentUrl());
		proxyListener.afterNavigateTo(url, driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateBack(org.openqa.selenium.WebDriver)
	 */
	//@BeforeTarget(targetClass = Navigation.class, targetMethod = "back")
	@Override
	public void beforeNavigateBack(@SupportParam WebDriver driver) {
		Log.message("Attempt to navigate to previous url. Current url is "
				+ driver.getCurrentUrl());
		proxyListener.beforeNavigateBack(driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateBack(org.openqa.selenium.WebDriver)
	 */
	//@AfterTarget(targetClass = Navigation.class, targetMethod = "back")
	@Override
	public void afterNavigateBack(@SupportParam WebDriver driver) {
		Log.message("Current URL is  " + driver.getCurrentUrl());
		proxyListener.afterNavigateBack(driver);

	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeNavigateForward(org.openqa.selenium.WebDriver)
	 */
	//@BeforeTarget(targetClass = Navigation.class, targetMethod = "forward")
	@Override
	public void beforeNavigateForward(@SupportParam WebDriver driver) {
		Log.message("Attempt to navigate to next url. Current url is "
				+ driver.getCurrentUrl());
		proxyListener.beforeNavigateForward(driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#afterNavigateForward(org.openqa.selenium.WebDriver)
	 */
	//@AfterTarget(targetClass = Navigation.class, targetMethod = "forward")
	@Override
	public void afterNavigateForward(@SupportParam WebDriver driver) {
		Log.message("Current URL is  " + driver.getCurrentUrl());
		proxyListener.afterNavigateForward(driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeFindBy(org.openqa.selenium.By,
	 *      org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 */
	//@BeforeTarget(targetClass = WebDriver.class, targetMethod = "findElement")
	//@BeforeTarget(targetClass = WebDriver.class, targetMethod = "findElements")
	//@BeforeTarget(targetClass = WebElement.class, targetMethod = "findElement")
	//@BeforeTarget(targetClass = WebElement.class, targetMethod = "findElements")
	@Override
	public void beforeFindBy(@UseParameter(number = 0) By by,
			@TargetParam WebElement element, @SupportParam WebDriver driver) {
		Log.debug("Searching for element by locator " + by.toString()
				+ " has been started");
		if (element != null) {
			highlightElementAndLogAction(element, "Using root element",
					HowToHighLightElement.DEBUG);
		}
		proxyListener.beforeFindBy(by, element, driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#afterFindBy(org.openqa.selenium.By,
	 *      org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 */
	//@AfterTarget(targetClass = WebDriver.class, targetMethod = "findElement")
	//@AfterTarget(targetClass = WebDriver.class, targetMethod = "findElements")
	//@AfterTarget(targetClass = WebElement.class, targetMethod = "findElement")
	//@AfterTarget(targetClass = WebElement.class, targetMethod = "findElements")
	@Override
	public void afterFindBy(@UseParameter(number = 0) By by,
			@TargetParam WebElement element, @SupportParam WebDriver driver) {
		Log.debug("Searching for web element has been finished. Locator is "
				+ by.toString());
		if (element != null) {
			highlightElementAndLogAction(element, "Root element was used",
					HowToHighLightElement.DEBUG);
		}
		proxyListener.afterFindBy(by, element, driver);
	}

	//@BeforeTarget(targetClass = WebElement.class, targetMethod = "click")
	@Override
	public void beforeClickOn(@TargetParam WebElement element,
			@SupportParam WebDriver driver) {
		highlightElementAndLogAction(element,
				"State before element will be clicked on.",
				HowToHighLightElement.INFO);
		proxyListener.beforeClickOn(element, driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#afterClickOn(org.openqa.selenium.WebElement,
	 *      org.openqa.selenium.WebDriver)
	 */
	//@AfterTarget(targetClass = WebElement.class, targetMethod = "click")
	@Override
	public void afterClickOn(@TargetParam WebElement element,
			@SupportParam WebDriver driver) {
		Log.message("Click on element has been successfully performed!");
		proxyListener.afterClickOn(element, driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeChangeValueOf(org.openqa.selenium.WebElement,
	 *      org.openqa.selenium.WebDriver)
	 */
	//@BeforeTarget(targetClass = WebElement.class, targetMethod = "sendKeys")
	//@BeforeTarget(targetClass = WebElement.class, targetMethod = "clear")
	//@BeforeTarget(targetClass = MobileElement.class, targetMethod = "setValue")
	@Override
	public void beforeChangeValueOf(@TargetParam WebElement element,
			@SupportParam WebDriver driver) {
		highlightElementAndLogAction(element,
				"State before element value will be changed.",
				HowToHighLightElement.INFO);
		proxyListener.beforeChangeValueOf(element, driver);
	}

	/**
	 * @see
	 * org.openqa.selenium.support.events.WebDriverEventListener#afterChangeValueOf
	 * (org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 */
	//@AfterTarget(targetClass = WebElement.class, targetMethod = "sendKeys")
	//@AfterTarget(targetClass = WebElement.class, targetMethod = "clear")
	//@AfterTarget(targetClass = MobileElement.class, targetMethod = "setValue")
	@Override
	public void afterChangeValueOf(@TargetParam WebElement element,
			@SupportParam WebDriver driver) {
		highlightElementAndLogAction(element,
				"State after element value was changed.",
				HowToHighLightElement.INFO);
		proxyListener.afterChangeValueOf(element, driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#beforeScript(java.lang.String,
	 *      org.openqa.selenium.WebDriver)
	 */
	//@BeforeTarget(targetClass = JavascriptExecutor.class, targetMethod = "executeAsyncScript")
	//@BeforeTarget(targetClass = JavascriptExecutor.class, targetMethod = "executeScript")
	@Override
	public void beforeScript(@UseParameter(number = 0) String script,
			@SupportParam WebDriver driver) {
		proxyListener.beforeScript(script, driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#afterScript(java.lang.String,
	 *      org.openqa.selenium.WebDriver)
	 */
	//@AfterTarget(targetClass = JavascriptExecutor.class, targetMethod = "executeAsyncScript")
	//@AfterTarget(targetClass = JavascriptExecutor.class, targetMethod = "executeScript")
	@Override
	public void afterScript(@UseParameter(number = 0) String script,
			@SupportParam WebDriver driver) {
		proxyListener.afterScript(script, driver);
	}

	/**
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#onException(java.lang.Throwable,
	 *      org.openqa.selenium.WebDriver)
	 */
	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		Log.debug("An exception has been caught out."
				+ throwable.getClass().getName() + ":" + throwable.getMessage());
		proxyListener.onException(throwable, driver);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#afterAlertAccept(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.Alert)
	 */
	//@AfterTarget(targetClass = Alert.class, targetMethod = "accept")
	@Override
	public void afterAlertAccept(@SupportParam WebDriver driver,
			@TargetParam Alert alert) {
		Log.message("Alert has been accepted");
		proxyListener.afterAlertAccept(driver, alert);
	}

	/**
	 * @see
	 * com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#afterAlertDismiss
	 * (org.openqa.selenium.WebDriver, org.openqa.selenium.Alert)
	 */
	//@AfterTarget(targetClass = Alert.class, targetMethod = "dismiss")
	@Override
	public void afterAlertDismiss(@SupportParam WebDriver driver,
			@TargetParam Alert alert) {
		Log.message("Alert has been dismissed");
		proxyListener.afterAlertDismiss(driver, alert);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#afterAlertSendKeys(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.Alert, java.lang.String)
	 */
	@AfterTarget(targetClass = Alert.class, targetMethod = "sendKeys")
	@Override
	public void afterAlertSendKeys(@SupportParam WebDriver driver,
			@TargetParam Alert alert, @UseParameter(number = 0) String keys) {
		Log.message("String " + keys + " has been sent to alert");
		proxyListener.afterAlertSendKeys(driver, alert, keys);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#afterSubmit(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement)
	 */
	//@AfterTarget(targetClass = WebElement.class, targetMethod = "submit")
	@Override
	public void afterSubmit(@SupportParam WebDriver driver,
			@TargetParam WebElement element) {
		Log.message("Submit has been performed successfully");
		proxyListener.afterSubmit(driver, element);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#beforeAlertAccept(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.Alert)
	 */
	//@BeforeTarget(targetClass = Alert.class, targetMethod = "accept")
	@Override
	public void beforeAlertAccept(@SupportParam WebDriver driver,
			@TargetParam Alert alert) {
		Log.message("Attempt to accept alert...");
		proxyListener.beforeAlertAccept(driver, alert);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#beforeAlertDismiss(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.Alert)
	 */
	//@BeforeTarget(targetClass = Alert.class, targetMethod = "dismiss")
	@Override
	public void beforeAlertDismiss(@SupportParam WebDriver driver,
			@TargetParam Alert alert) {
		Log.message("Attempt to dismiss the alert...");
		proxyListener.beforeAlertDismiss(driver, alert);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#beforeAlertSendKeys(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.Alert, java.lang.String)
	 */
	//@BeforeTarget(targetClass = Alert.class, targetMethod = "sendKeys")
	@Override
	public void beforeAlertSendKeys(@SupportParam WebDriver driver,
			@TargetParam Alert alert, String keys) {
		Log.message("Attemt to send string " + keys + " to alert...");
		proxyListener.beforeAlertSendKeys(driver, alert, keys);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#beforeSubmit(org.openqa.selenium.WebDriver,
	 *      org.openqa.selenium.WebElement)
	 */
	//@BeforeTarget(targetClass = WebElement.class, targetMethod = "submit")
	@Override
	public void beforeSubmit(@SupportParam WebDriver driver,
			@TargetParam WebElement element) {
		highlightElementAndLogAction(element,
				"State before submit will be performed by element: ",
				HowToHighLightElement.INFO);
		proxyListener.beforeSubmit(driver, element);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#beforeFindBy(java.lang.String,
	 *      org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 */
	//@BeforeTarget(targetClass = AndroidDriver.class, targetMethod = "findElementByAndroidUIAutomator")
	//@BeforeTarget(targetClass = AndroidDriver.class, targetMethod = "findElementsByAndroidUIAutomator")
	//@BeforeTarget(targetClass = IOSDriver.class, targetMethod = "findElementByIosUIAutomation")
	//@BeforeTarget(targetClass = IOSDriver.class, targetMethod = "findElementsByIosUIAutomation")
	//@BeforeTarget(targetClass = AppiumDriver.class, targetMethod = "findElementByAccessibilityId")
	//@BeforeTarget(targetClass = AppiumDriver.class, targetMethod = "findElementsByAccessibilityId")
	//@BeforeTarget(targetClass = AndroidElement.class, targetMethod = "findElementByAndroidUIAutomator")
	//@BeforeTarget(targetClass = AndroidElement.class, targetMethod = "findElementsByAndroidUIAutomator")
	//@BeforeTarget(targetClass = IOSElement.class, targetMethod = "findElementByIosUIAutomation")
	//@BeforeTarget(targetClass = IOSElement.class, targetMethod = "findElementsByIosUIAutomation")
	//@BeforeTarget(targetClass = MobileElement.class, targetMethod = "findElementByAccessibilityId")
	//@BeforeTarget(targetClass = MobileElement.class, targetMethod = "findElementsByAccessibilityId")
	@Override
	public void beforeFindBy(@UseParameter(number = 0) String byString,
			@TargetParam WebElement element, @SupportParam WebDriver driver) {
		Log.debug("Searching for element by locator " + byString
				+ " has been started");
		if (element != null) {
			highlightElementAndLogAction(element, "Using root element",
					HowToHighLightElement.DEBUG);
		}
		proxyListener.beforeFindBy(byString, element, driver);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#afterFindBy(java.lang.String,
	 *      org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)
	 */
	//@AfterTarget(targetClass = AndroidDriver.class, targetMethod = "findElementByAndroidUIAutomator")
	//@AfterTarget(targetClass = AndroidDriver.class, targetMethod = "findElementsByAndroidUIAutomator")
	//@AfterTarget(targetClass = IOSDriver.class, targetMethod = "findElementByIosUIAutomation")
	//@AfterTarget(targetClass = IOSDriver.class, targetMethod = "findElementsByIosUIAutomation")
	//@AfterTarget(targetClass = AppiumDriver.class, targetMethod = "findElementByAccessibilityId")
	//@AfterTarget(targetClass = AppiumDriver.class, targetMethod = "findElementsByAccessibilityId")
	//@AfterTarget(targetClass = AndroidElement.class, targetMethod = "findElementByAndroidUIAutomator")
	//@AfterTarget(targetClass = AndroidElement.class, targetMethod = "findElementsByAndroidUIAutomator")
	//@AfterTarget(targetClass = IOSElement.class, targetMethod = "findElementByIosUIAutomation")
	//@AfterTarget(targetClass = IOSElement.class, targetMethod = "findElementsByIosUIAutomation")
	//@AfterTarget(targetClass = MobileElement.class, targetMethod = "findElementByAccessibilityId")
	//@AfterTarget(targetClass = MobileElement.class, targetMethod = "findElementsByAccessibilityId")
	@Override
	public void afterFindBy(@UseParameter(number = 0) String byString,
			@TargetParam WebElement element, @SupportParam WebDriver driver) {
		Log.debug("Searching for web element has been finished. Locator is "
				+ byString);
		if (element != null) {
			highlightElementAndLogAction(element, "Root element was used",
					HowToHighLightElement.DEBUG);
		}
		proxyListener.afterFindBy(byString, element, driver);
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

	private void highlightElementAndLogAction(WebElement element,
			String logMessage, HowToHighLightElement howToHighLightElement) {
		String elementDescription = elementDescription(element);
		highLighter.resetAccordingTo(configurationWrapper
				.getWrappedConfiguration());
		howToHighLightElement.highLight(highLighter, driver, element,
				logMessage + elementDescription);
	}

	private Object transformToListenable(Object result) {
		if (result == null) { // maybe it was "void"
			return result;
		}
		Object o = getListenable(result);
		if (o != null) { // ...so listenable object will be returned! ha-ha-ha
			result = context.getBean(MainBeanConfiguration.COMPONENT_BEAN, o);
		}
		return result;
	}

	// List of WebElement
	private List<Object> returnProxyList(List<Object> originalList) {
		try {
			List<Object> proxyList = new ArrayList<>();
			for (Object o : originalList) {
				if (getClassForProxy(o.getClass()) == null) {
					proxyList.add(o);
				}
				proxyList.add(context.getBean(
						MainBeanConfiguration.COMPONENT_BEAN, o));
			}
			return proxyList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @see com.github.arachnidium.core.bean.AbstractAspect#doAround(org.aspectj.lang.ProceedingJoinPoint)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Around(POINTCUT_VALUE)
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		launchMethod(point, this, WhenLaunch.BEFORE);
		Throwable t = null;
		Object result = null;
		try {
			result = point.proceed();
		} catch (Exception e) {
			onException(e, driver);
			t = e;
			;
		}
		if (t != null) {
			throw getRootCause(t);
		}
		launchMethod(point, this, WhenLaunch.AFTER);

		if (result == null) { // maybe it was "void"
			return result;
		}
		if (List.class.isAssignableFrom(result.getClass())) {
			return returnProxyList((List<Object>) result);
		}
		return transformToListenable(result);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IWebDriverEventListener#beforeQuit(org.openqa.selenium.WebDriver)
	 */
	@Override
	//@BeforeTarget(targetClass = WebDriver.class, targetMethod = "quit")
	public void beforeQuit(@SupportParam WebDriver driver) {
		destroyable.destroy();
		proxyListener.beforeQuit(driver);
	}

}
