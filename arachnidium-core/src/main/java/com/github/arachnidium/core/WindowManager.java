package com.github.arachnidium.core;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.github.arachnidium.core.bean.BeanWindowConfiguration;
import com.github.arachnidium.core.fluenthandle.FluentPageWaiting;
import com.github.arachnidium.core.settings.WindowIsClosedTimeOut;

public final class WindowManager extends Manager<HowToGetPage, BrowserWindow> {

	private static long TIME_OUT_TO_SWITCH_ON = 2; //two seconds
	
	public WindowManager(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation, 
				new AnnotationConfigApplicationContext(BeanWindowConfiguration.class));
	}

	/**
	 * Changes active window
	 * @see com.github.arachnidium.core.Manager#changeActive(java.lang.String)
	 */
	@Override
	void changeActive(String handle) throws NoSuchWindowException,
			UnhandledAlertException {
		Set<String> handles = getHandles();
		if (!handles.contains(handle))
			throw new NoSuchWindowException("There is no window with handle "
					+ handle + "!");
		try {
			awaiting.awaitCondition(TIME_OUT_TO_SWITCH_ON, isSwithedOn(handle));
		} catch (UnhandledAlertException | NoSuchWindowException e) {
			throw e;
		}
		catch (TimeoutException e) {
			throw new WebDriverException("Can't to switch on window handle " + handle, e);
		}
	}

	/**
	 * Closes browser window
	 * 
	 * @param handle
	 *            Given string wibdow handle
	 * 
	 * @throws UnclosedWindowException
	 *             If window is not closed
	 * @throws NoSuchWindowException
	 *             If window has been already closed
	 * @throws UnhandledAlertException
	 * @throws UnreachableBrowserException
	 */
	synchronized void close(String handle) throws UnclosedWindowException,
			NoSuchWindowException, UnhandledAlertException,
			UnreachableBrowserException {
		long timeOut = getTimeOut(getWebDriverEncapsulation().getWrappedConfiguration()
				.getSection(WindowIsClosedTimeOut.class)
				.getWindowIsClosedTimeOutTimeOut());

		try {
			changeActive(handle);
			WebDriver driver = getWrappedDriver();
			driver.switchTo().window(handle).close();
		} catch (UnhandledAlertException | NoSuchWindowException e) {
			throw e;
		}

		try {
			awaiting.awaitCondition(timeOut, isClosed(handle));
		} catch (TimeoutException e) {
			throw new UnclosedWindowException("Window hasn't been closed!", e);
		}

		int actualWinCount = 0;
		try {
			actualWinCount = getHandles().size();
		} catch (WebDriverException e) { // if all windows are closed
			actualWinCount = 0;
		} finally {
			if (actualWinCount == 0) {
				destroy();
				getWebDriverEncapsulation().destroy();
			}
		}
	}

	/**
	 * @see com.github.arachnidium.core.Manager#getHandles()
	 */
	@Override
	Set<String> getHandles() {
		return getWrappedDriver().getWindowHandles();
	}

	/**
	 * Actual strategy is {@link HowToGetPage}
	 * 
	 * @see com.github.arachnidium.core.Manager#getStringHandle(long,
	 *      com.github.arachnidium.core.fluenthandle.IHowToGetHandle)
	 */
	@Override
	String getStringHandle(long timeOut,
			HowToGetPage howToGet)
			throws NoSuchWindowException {
		HowToGetPage clone = howToGet.cloneThis();
		try {
			return awaiting.awaitCondition(timeOut,
					clone.getExpectedCondition(new FluentPageWaiting()));
		} catch (TimeoutException e) {
			throw new NoSuchWindowException("Can't find window! Condition is "
					+ clone.toString(), e);
		}
	}

	// is browser window closed?
	private static Boolean isClosed(final WebDriver from, String handle) {
		Set<String> handles;
		try {
			handles = from.getWindowHandles();
		} catch (WebDriverException e) { // if all windows are closed
			return true;
		}

		if (!handles.contains(handle))
			return true;
		else
			return null;
	}
	
	//is window switched on?
	private static Boolean isSwithedOn(final WebDriver from, String handle) {
		from.switchTo().window(handle);
		if (from.getWindowHandle().equals(handle))
			return true;
		else
			return null;
	}

	// fluent waiting for the result. See above
	private static ExpectedCondition<Boolean> isClosed(final String closingHandle) {
		return from -> isClosed(from, closingHandle);
	}
	
	private static ExpectedCondition<Boolean> isSwithedOn(final String handle) {
		return from -> isSwithedOn(from, handle);
	}	


	@Override
	BrowserWindow getRealHandle(long timeOut,
			HowToGetPage howToGet, By by, 
			HowToGetByFrames howToGetByFramesStrategy) {
		String handle = this.getStringHandle(timeOut,
				howToGet);			
		BrowserWindow window = new BrowserWindow(handle, 
				this, by, howToGetByFramesStrategy);
		return returnNewCreatedListenableHandle(window,
				BeanWindowConfiguration.WINDOW_BEAN);
	}
}