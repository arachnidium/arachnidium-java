package org.arachnidium.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.util.List;
import java.util.Set;

import org.arachnidium.core.bean.MainBeanConfiguration;
import org.arachnidium.core.components.mobile.ContextTool;
import org.arachnidium.core.fluenthandle.FluentContextWaiting;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class ContextManager extends Manager<HowToGetMobileContext> {
	private final ContextTool contextTool;
	private final boolean isSupportActivities;

	public ContextManager(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation);
		contextTool = getWebDriverEncapsulation().getComponent(
				ContextTool.class);
		String mobilePlatform = String
				.valueOf(((RemoteWebDriver) getWebDriverEncapsulation()
						.getWrappedDriver()).getCapabilities().getCapability(
						MobileCapabilityType.PLATFORM_NAME));
		isSupportActivities = mobilePlatform.trim().toUpperCase()
				.equals(MobilePlatform.ANDROID.toUpperCase());
		handleWaiting = new FluentContextWaiting();
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

	private HowToGetMobileContext isSupportActivities(HowToGetMobileContext howToGet){
		if (!isSupportActivities){
			howToGet.setExpected((List<String>) null);
		}
		return howToGet;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MobileContext getHandle(int contextIndex)
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
	public MobileContext getHandle(HowToGetMobileContext howToGet)
			throws NoSuchContextException {
		Long time = getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut());
		return getHandle(time, howToGet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MobileContext getHandle(long timeOut,
			HowToGetMobileContext howToGet)
			throws NoSuchContextException {
		MobileContext context = new MobileContext(getStringHandle(timeOut,
				isSupportActivities(howToGet)), this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	@Override
	String getStringHandle(long timeOut,
			HowToGetMobileContext howToGet)
			throws NoSuchContextException {
		HowToGetMobileContext clone = howToGet.cloneThis();
		try {
			return awaiting.awaitCondition(timeOut,
					clone.getExpectedCondition(handleWaiting));
		} catch (TimeoutException e) {
			throw new NoSuchContextException(
					"Can't find context! Condition is " + clone.toString(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public MobileContext getHandle(int contextIndex, long timeOut) 
			throws NoSuchContextException  {
		String handle = this.getStringHandle(contextIndex, timeOut);
		MobileContext initedContext = (MobileContext) Handle.isInitiated(
				handle, this);
		if (initedContext != null)
			return initedContext;
		MobileContext context = new MobileContext(handle, this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);	
	}

	@Override
	String getStringHandle(int contextIndex, long timeOut) 
			throws NoSuchContextException  {
		HowToGetMobileContext f = new HowToGetMobileContext();
		f.setExpected(contextIndex);
		return getStringHandle(timeOut, f);
	}

}
