package com.github.arachnidium.core;

import java.util.*;
import java.util.logging.Level;

import com.github.arachnidium.util.logging.Log;
import com.github.arachnidium.util.logging.Photographer;
import com.github.arachnidium.util.proxy.EnhancedProxyFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.pagefactory.ByChained;

import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.interfaces.ICalculatesBy;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.core.interfaces.IHasSearchContext;
import com.github.arachnidium.core.interfaces.ISwitchesToItself;
import com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf;

/**
 * z Represents objects that have handles e.g. browser window and mobile
 * context/screen
 */
public abstract class Handle implements IHasHandle, ISwitchesToItself,
		ITakesPictureOfItSelf, IDestroyable, SearchContext, ICalculatesBy,
		IHasSearchContext {

	private String handle;
	public WebDriverEncapsulation driverEncapsulation;
	public Manager<?, ?> nativeManager;
	By by;
	HowToGetByFrames howToGetByFramesStrategy;

	IHowToGetHandle howToGetHandleStrategy;
	long timeOut;
	SearchContext context;

	private final HandleReceptionist receptionist;
	private static final Map<Class<? extends WebDriver>, Class<? extends WebElement>> elementTypesMap = new HashMap<Class<? extends WebDriver>, Class<? extends WebElement>>() {
		private static final long serialVersionUID = 1L;

		{
			put(AndroidDriver.class, AndroidElement.class);
			put(IOSDriver.class, IOSElement.class);
			put(RemoteWebDriver.class, RemoteWebElement.class);
		}
	};

	Handle(String handle, Manager<?, ?> manager, By by,
			HowToGetByFrames howToGetByFramesStrategy) {
		this.nativeManager = manager;
		this.driverEncapsulation = manager.getWebDriverEncapsulation();
		this.handle = handle;
		this.receptionist = nativeManager.getHandleReceptionist();
		this.by = by;
		this.howToGetByFramesStrategy = howToGetByFramesStrategy;		
		context = createSearchContext(by, driverEncapsulation, this);
	}

	@Override
	public void destroy() {
		receptionist.remove(this);
	}

	/**
	 * @return flag of the handle existing
	 */
	public synchronized boolean exists() {
		if (!nativeManager.isAlive())
			return false;
		try {
			Set<String> handles = nativeManager.getHandles();
			return handles.contains(handle);
		} catch (WebDriverException e) { // if there is no handle
			return false;
		}
	}

	/**
	 * @return Window string handle/mobile context name
	 *
	 * @see com.github.arachnidium.core.interfaces.IHasHandle#getHandle()
	 */
	@Override
	public String getHandle() {
		return handle;
	}

	/**
	 * Sets focus to itself
	 */
	@Override
	public synchronized void switchToMe() {
		nativeManager.switchTo(handle);
		if (howToGetByFramesStrategy != null)
			howToGetByFramesStrategy.switchTo(driverEncapsulation
					.getWrappedDriver());
	}

	/**
	 * Takes a picture of itself. It creates FINE {@link Level} {@link Log}
	 * message with attached picture (optionally)
	 */
	@Override
	public synchronized void takeAPictureOfAFine(String comment) {
		Photographer.takeAPictureOfAFine(
				driverEncapsulation.getWrappedDriver(), comment);
	}

	/**
	 * Takes a picture of itself. It creates INFO {@link Level} {@link Log}
	 * message with attached picture (optionally)
	 */
	@Override
	public synchronized void takeAPictureOfAnInfo(String comment) {
		Photographer.takeAPictureOfAnInfo(
				driverEncapsulation.getWrappedDriver(), comment);
	}

	/**
	 * Takes a picture of itself. It creates SEVERE {@link Level} {@link Log}
	 * message with attached picture (optionally)
	 */
	@Override
	public synchronized void takeAPictureOfASevere(String comment) {
		Photographer.takeAPictureOfASevere(
				driverEncapsulation.getWrappedDriver(), comment);
	}

	/**
	 * Takes a picture of itself. It creates WARN {@link Level} {@link Log}
	 * message with attached picture (optionally)
	 */
	@Override
	public synchronized void takeAPictureOfAWarning(String comment) {
		Photographer.takeAPictureOfAWarning(
				driverEncapsulation.getWrappedDriver(), comment);
	}

	@Override
	public By returnBy(By by) {
		By usedBy = null;
		if (this.by != null) {
			usedBy = new ByChained(this.by, by);
		} else
			usedBy = by;
		return usedBy;
	}

	@Override
	public WebElement findElement(By by) {
		return getSearchContext().findElement(by);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return getSearchContext().findElements(by);
	}

	@Override
	public SearchContext getSearchContext() {
		return context;		
	}
	
	static SearchContext createSearchContext(By by, WebDriverEncapsulation driverEncapsulation, 
			Handle handle){
		if (by == null) 
			return proxyDriver(driverEncapsulation, handle);
		else
			return proxyElement(driverEncapsulation, handle);
	}
	
	private static WebDriver proxyDriver(WebDriverEncapsulation driverEncapsulation, Handle handle){
		WebDriver driver = driverEncapsulation.getWrappedDriver();		
		Class<?> driverClass = driver.getClass().getSuperclass();
		//object is a proxy created via Spring/CGLib 
		return (WebDriver) EnhancedProxyFactory.
				getProxyBypassConstructor(driverClass, new WebDriverInterceptor(handle));
	}

	private static WebElement proxyElement(WebDriverEncapsulation driverEncapsulation, Handle handle) {
		WebDriver driver = driverEncapsulation.getWrappedDriver();

		Set<Map.Entry<Class<? extends WebDriver>, Class<? extends WebElement>>> entries = elementTypesMap
				.entrySet();
		Iterator<Map.Entry<Class<? extends WebDriver>, Class<? extends WebElement>>> iterator = entries
				.iterator();

		Class<? extends WebElement> target = null;
		while (iterator.hasNext()) {
			Map.Entry<Class<? extends WebDriver>, Class<? extends WebElement>> entry = iterator
					.next();

			Class<? extends WebDriver> webDriverClass = entry.getKey();
			if (webDriverClass.isAssignableFrom(driver.getClass())) {
				target = entry.getValue();
				break;
			}
		}
		return EnhancedProxyFactory.getProxy(target, new Class[] {},
				new Object[] {}, new NestedElementInterceptor(handle));
	}

}
