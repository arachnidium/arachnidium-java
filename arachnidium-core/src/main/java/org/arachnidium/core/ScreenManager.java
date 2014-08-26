package org.arachnidium.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.util.List;
import java.util.Set;

import org.arachnidium.core.bean.MainBeanConfiguration;
import org.arachnidium.core.components.mobile.ContextTool;
import org.arachnidium.core.fluenthandle.FluentScreenWaiting;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class ScreenManager extends Manager<HowToGetMobileScreen> {
	private final ContextTool contextTool;
	private final boolean isSupportActivities;

	public ScreenManager(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation);
		contextTool = getWebDriverEncapsulation().getComponent(
				ContextTool.class);
		String mobilePlatform = String
				.valueOf(((RemoteWebDriver) getWebDriverEncapsulation()
						.getWrappedDriver()).getCapabilities().getCapability(
						MobileCapabilityType.PLATFORM_NAME));
		isSupportActivities = mobilePlatform.trim().toUpperCase()
				.equals(MobilePlatform.ANDROID.toUpperCase());
		handleWaiting = new FluentScreenWaiting();
	}

	@Override
	void changeActive(String context) throws NoSuchContextException {
		contextTool.context(context);
	}

	synchronized String getActivityByHandle(String handle)
			throws NoSuchContextException {
		changeActive(handle);
		if (isSupportActivities) {
			return ((AppiumDriver) getWrappedDriver()).currentActivity();
		}
		// iOS doesn't support activities. It is frustrating.
		return "";
	}

	private HowToGetMobileScreen isSupportActivities(HowToGetMobileScreen howToGet){
		if (!isSupportActivities){
			howToGet.setExpected((List<String>) null);
		}
		return howToGet;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MobileScreen getHandle(int contextIndex)
			throws NoSuchContextException {
		Long time = getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut());
		return getHandle(contextIndex, time);
	}

	@Override
	public Set<String> getHandles() {
		return contextTool.getContextHandles();
	}

	@SuppressWarnings("unchecked")
	@Override
	public MobileScreen getHandle(HowToGetMobileScreen howToGet)
			throws NoSuchContextException {
		Long time = getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut());
		return getHandle(time, howToGet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MobileScreen getHandle(long timeOut,
			HowToGetMobileScreen howToGet)
			throws NoSuchContextException {
		String handle = this.getStringHandle(timeOut,
				isSupportActivities(howToGet));
		MobileScreen initedContext = (MobileScreen) Handle.isInitiated(
				handle, this);
		if (initedContext != null)
			return initedContext;
		MobileScreen context = new MobileScreen(handle, this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	@Override
	String getStringHandle(long timeOut,
			HowToGetMobileScreen howToGet)
			throws NoSuchContextException {
		HowToGetMobileScreen clone = howToGet.cloneThis();
		try {
			return awaiting.awaitCondition(timeOut,
					clone.getExpectedCondition(handleWaiting));
		} catch (TimeoutException e) {
			throw new NoSuchContextException(
					"Can't find screen! Condition is " + clone.toString(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public MobileScreen getHandle(int contextIndex, long timeOut) 
			throws NoSuchContextException  {
		String handle = this.getStringHandle(contextIndex, timeOut);
		MobileScreen initedContext = (MobileScreen) Handle.isInitiated(
				handle, this);
		if (initedContext != null)
			return initedContext;
		MobileScreen context = new MobileScreen(handle, this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);	
	}

	@Override
	String getStringHandle(int contextIndex, long timeOut) 
			throws NoSuchContextException  {
		HowToGetMobileScreen f = new HowToGetMobileScreen();
		f.setExpected(contextIndex);
		return getStringHandle(timeOut, f);
	}

}
