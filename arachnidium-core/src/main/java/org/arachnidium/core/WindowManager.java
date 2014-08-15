package org.arachnidium.core;

import java.util.List;
import java.util.Set;

import org.arachnidium.core.bean.MainBeanConfiguration;
import org.arachnidium.core.components.common.AlertHandler;
import org.arachnidium.core.components.common.FluentWindowConditions;
import org.arachnidium.core.settings.WindowsTimeOuts;
import org.arachnidium.util.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;

public final class WindowManager extends Manager {
	private final FluentWindowConditions fluent;

	public WindowManager(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation);
		fluent = getWebDriverEncapsulation().getComponent(FluentWindowConditions.class); 
	}

	@Override
	void changeActive(String handle) throws NoSuchWindowException,
			UnhandledAlertException {
		Set<String> handles = getHandles();
		if (!handles.contains(handle))
			throw new NoSuchWindowException("There is no window with handle "
					+ handle + "!");
		try {
			getWrappedDriver().switchTo().window(handle);
		} catch (UnhandledAlertException | NoSuchWindowException e) {
			throw e;
		}
	}

	synchronized void close(String handle) throws UnclosedWindowException,
			NoSuchWindowException, UnhandledAlertException,
			UnreachableBrowserException {
		WindowsTimeOuts timeOuts = getWindowTimeOuts();
		long timeOut = getTimeOut(timeOuts.getWindowClosingTimeOutSec(),
				defaultTime);

		try {
			changeActive(handle);
			WebDriver driver = getWrappedDriver();
			driver.switchTo().window(handle).close();
		} catch (UnhandledAlertException | NoSuchWindowException e) {
			throw e;
		}

		try {
			awaiting.awaitCondition(timeOut, fluent.isClosed(handle));
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

	@Override
	public synchronized Alert getAlert() throws NoAlertPresentException {
		WindowsTimeOuts timeOuts = getWindowTimeOuts();
		return getWebDriverEncapsulation()
				.getComponent(
						AlertHandler.class,
						new Class[] { long.class },
						new Object[] { getTimeOut(
								timeOuts.getSecsForAwaitinAlertPresent(),
						defaultTime) });

	}

	/**
	 * returns window handle by it's index
	 */
	@Override
	public synchronized Handle getByIndex(int index) {
		String handle = this.getHandleByIndex(index);
		SingleWindow initedWindow = (SingleWindow) Handle.isInitiated(handle,
				this);
		if (initedWindow != null)
			return initedWindow;
		SingleWindow window = new SingleWindow(handle, this);
		return returnNewCreatedListenableHandle(window, MainBeanConfiguration.WINDOW_BEAN);
	}

	@Override
	/**
	 * returns window handle by it's index
	 */
	String getHandleByIndex(int windowIndex) throws NoSuchWindowException {
		try {
			Log.debug("Attempt to get window that is specified by index "
					+ Integer.toString(windowIndex) + "...");
			WindowsTimeOuts timeOuts = getWindowTimeOuts();
			long timeOut = getTimeOut(timeOuts.getWindowCountTimeOutSec(),
					defaultTime);
			return awaiting.awaitCondition(timeOut, 100,
					fluent.suchWindowWithIndexIsPresent(windowIndex));
		} catch (TimeoutException e) {
			throw new NoSuchWindowException(
					"Can't find window! Index out of bounds! Specified index is "
					+ Integer.toString(windowIndex)
					+ " is more then actual window count", e);
		}
	}

	@Override
	Set<String> getHandles() {
		return getWrappedDriver().getWindowHandles();
	}

	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration
	 */
	@Override
	public synchronized Handle getNewHandle() {
		SingleWindow window = new SingleWindow(switchToNew(), this);
		return returnNewCreatedListenableHandle(window, MainBeanConfiguration.WINDOW_BEAN);
	}

	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration. new window should has page that loads by
	 * specified URL. We can specify it as regular expression list
	 */
	public synchronized Handle getNewHandle(List<String> urls) {
		SingleWindow window =  new SingleWindow(switchToNew(urls), this);
		return returnNewCreatedListenableHandle(window, MainBeanConfiguration.WINDOW_BEAN);
	}

	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time
	 */
	@Override
	public synchronized Handle getNewHandle(long timeOutInSeconds) {
		SingleWindow window =  new SingleWindow(switchToNew(timeOutInSeconds), this);
		return returnNewCreatedListenableHandle(window, MainBeanConfiguration.WINDOW_BEAN);
	}

	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time. new window should has page that loads by specified URLs. We can
	 * specify it as regular expression list
	 */
	public synchronized Handle getNewHandle(long timeOutInSeconds,
			List<String> urls) {
		SingleWindow window =  new SingleWindow(switchToNew(timeOutInSeconds, urls), this);
		return returnNewCreatedListenableHandle(window, MainBeanConfiguration.WINDOW_BEAN);
	}

	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time. new window should has defined title. We can specify title partially
	 * as a regular expression
	 */
	@Override
	public synchronized Handle getNewHandle(long timeOutInSeconds, String title) {
		SingleWindow window = new SingleWindow(switchToNew(timeOutInSeconds, title), this);
		return returnNewCreatedListenableHandle(window, MainBeanConfiguration.WINDOW_BEAN);
	}

	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration. new window should has defined title. We can
	 * specify title partially as a regular expression
	 */
	@Override
	public synchronized Handle getNewHandle(String title) {
		SingleWindow window =  new SingleWindow(switchToNew(title), this);
		return returnNewCreatedListenableHandle(window, MainBeanConfiguration.WINDOW_BEAN);
	}

	private WindowsTimeOuts getWindowTimeOuts() {
		return getWebDriverEncapsulation().configuration
				.getSection(WindowsTimeOuts.class);
	}

	@Override
	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration
	 */
	String switchToNew() throws NoSuchWindowException {
		WindowsTimeOuts timeOuts = getWindowTimeOuts();
		long timeOut = getTimeOut(timeOuts.getNewWindowTimeOutSec(),
				defaultTimeForNew);
		return switchToNew(timeOut);
	}

	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration. new window should has page that loads by
	 * specified URL. We can specify it as regular expression list
	 */
	String switchToNew(List<String> urls) throws NoSuchWindowException {
		WindowsTimeOuts timeOuts = getWindowTimeOuts();
		long timeOut = getTimeOut(timeOuts.getNewWindowTimeOutSec(),
				defaultTimeForNew);
		return switchToNew(timeOut, urls);
	}

	@Override
	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time
	 */
	String switchToNew(long timeOutInSeconds) throws NoSuchWindowException {
		try {
			Log.debug("Waiting a new window for "
					+ Long.toString(timeOutInSeconds) + " seconds.");
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.newWindowIsAppeared());
			changeActive(newHandle);
			return newHandle;
		} catch (TimeoutException e) {
			throw new NoSuchWindowException(
					"There is no new window! We have been waiting for "
							+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time. new window should has page that loads by specified URLs. We can
	 * specify it as regular expression list
	 */
	String switchToNew(long timeOutInSeconds, List<String> urls)
			throws NoSuchWindowException {
		try {
			Log.debug("Waiting a new window for "
					+ Long.toString(timeOutInSeconds)
					+ " seconds. New window should have page "
					+ " that is loaded by specified URLs. Urls are "
					+ urls.toString());
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.newWindowIsAppeared(urls));
			changeActive(newHandle);
			return newHandle;
		} catch (TimeoutException e) {
			throw new NoSuchWindowException(
					"There is no new window that loads by " + urls.toString()
					+ " ! We have been waiting for "
					+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	@Override
	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time. new window should has defined title. We can specify title partially
	 * as a regular expression
	 */
	String switchToNew(long timeOutInSeconds, String title)
			throws NoSuchWindowException {
		try {
			Log.debug("Waiting a new window for "
					+ Long.toString(timeOutInSeconds)
					+ " seconds. New window should have title " + title);
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.newWindowIsAppeared(title));
			changeActive(newHandle);
			return newHandle;
		} catch (TimeoutException e) {
			throw new NoSuchWindowException(
					"There is no new window with title " + title
					+ " ! We have been waiting for "
					+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	@Override
	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration. new window should has defined title. We can
	 * specify title partially
	 * as a regular expression
	 */
	String switchToNew(String title) throws NoSuchWindowException {
		WindowsTimeOuts timeOuts = getWindowTimeOuts();
		long timeOut = getTimeOut(timeOuts.getNewWindowTimeOutSec(),
				defaultTimeForNew);
		return switchToNew(timeOut, title);
	}
}