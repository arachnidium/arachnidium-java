package org.arachnidium.core;

import io.appium.java_client.android.AndroidDriver;

import java.util.List;
import java.util.Set;

import org.arachnidium.core.bean.MainBeanConfiguration;
import org.arachnidium.core.components.mobile.ContextTool;
import org.arachnidium.core.fluenthandle.FluentScreenWaiting;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public final class ScreenManager extends Manager<HowToGetMobileScreen> {
	private final ContextTool contextTool;
	private final boolean isSupportActivities;

	public ScreenManager(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation);
		contextTool = getWebDriverEncapsulation().getComponent(
				ContextTool.class);
		WebDriver wrappedDriver = getWebDriverEncapsulation()
				.getWrappedDriver();
		isSupportActivities = AndroidDriver.class
				.isAssignableFrom(wrappedDriver.getClass());
		handleWaiting = new FluentScreenWaiting();
	}

	/**
	 * Changes active context
	 * 
	 * @see org.arachnidium.core.Manager#changeActive(java.lang.String)
	 */
	@Override
	void changeActive(String context) throws NoSuchContextException {
		contextTool.context(context);
	}

	private HowToGetMobileScreen isSupportActivities(
			HowToGetMobileScreen howToGet) {
		if (!isSupportActivities) {
			howToGet.setExpected((List<String>) null);
		}
		return howToGet;
	}

	/**
	 * @see org.arachnidium.core.Manager#getHandle(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MobileScreen getHandle(int contextIndex)
			throws NoSuchContextException {
		Long time = getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut());
		return getHandle(contextIndex, time);
	}

	/**
	 * @see org.arachnidium.core.Manager#getHandles()
	 */
	@Override
	public Set<String> getHandles() {
		return contextTool.getContextHandles();
	}

	/**
	 * Actual strategy is {@link HowToGetMobileScreen}
	 * 
	 * @see org.arachnidium.core.Manager#getHandle(org.arachnidium.core.fluenthandle.IHowToGetHandle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MobileScreen getHandle(HowToGetMobileScreen howToGet)
			throws NoSuchContextException {
		Long time = getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut());
		return getHandle(time, howToGet);
	}

	/**
	 * Actual strategy is {@link HowToGetMobileScreen}
	 * 
	 * @see org.arachnidium.core.Manager#getHandle(long,
	 *      org.arachnidium.core.fluenthandle.IHowToGetHandle)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MobileScreen getHandle(long timeOut, HowToGetMobileScreen howToGet)
			throws NoSuchContextException {
		String handle = this.getStringHandle(timeOut,
				isSupportActivities(howToGet));
		MobileScreen initedContext = (MobileScreen) Handle.isInitiated(handle,
				this);
		if (initedContext != null)
			return initedContext;
		MobileScreen context = new MobileScreen(handle, this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	@Override
	String getStringHandle(long timeOut, HowToGetMobileScreen howToGet)
			throws NoSuchContextException {
		HowToGetMobileScreen clone = howToGet.cloneThis();
		try {
			return awaiting.awaitCondition(timeOut,
					clone.getExpectedCondition(handleWaiting));
		} catch (TimeoutException e) {
			throw new NoSuchContextException("Can't find screen! Condition is "
					+ clone.toString(), e);
		}
	}

	/**
	 * @see org.arachnidium.core.Manager#getHandle(int, long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MobileScreen getHandle(int contextIndex, long timeOut)
			throws NoSuchContextException {
		String handle = this.getStringHandle(contextIndex, timeOut);
		MobileScreen initedContext = (MobileScreen) Handle.isInitiated(handle,
				this);
		if (initedContext != null)
			return initedContext;
		MobileScreen context = new MobileScreen(handle, this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	/**
	 * @see org.arachnidium.core.Manager#getStringHandle(int, long)
	 */
	@Override
	String getStringHandle(int contextIndex, long timeOut)
			throws NoSuchContextException {
		HowToGetMobileScreen f = new HowToGetMobileScreen();
		f.setExpected(contextIndex);
		return getStringHandle(timeOut, f);
	}
}
