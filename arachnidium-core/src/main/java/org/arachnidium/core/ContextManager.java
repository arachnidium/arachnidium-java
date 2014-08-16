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

public final class ContextManager extends Manager<FluentContextStrategy> {
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

	private FluentContextStrategy isSupportActivities(FluentContextStrategy fluentHandleStrategy){
		if (!isSupportActivities){
			fluentHandleStrategy.setExpected((List<String>) null);
		}
		return fluentHandleStrategy;
	}
	
	/**
	 * returns context handle by it's index
	 */
	@Override
	public synchronized Handle getHandle(int index)
			throws NoSuchContextException {
		String handle = this.getStringHandle(index);
		SingleContext initedContext = (SingleContext) Handle.isInitiated(
				handle, this);
		if (initedContext != null)
			return initedContext;
		SingleContext context = new SingleContext(handle, this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	@Override
	/**
	 * returns context by it's index
	 */
	String getStringHandle(int index) throws NoSuchContextException {
		Long time = getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut());
		FluentContextStrategy f = new FluentContextStrategy();
		f.setExpected(index);
		return getStringHandle(time, f);
	}

	@Override
	public Set<String> getHandles() {
		return contextTool.getContextHandles();
	}

	@Override
	public Handle getHandle(FluentContextStrategy fluentHandleStrategy)
			throws NoSuchContextException {
		SingleContext context = new SingleContext(
				getStringHandle(isSupportActivities(fluentHandleStrategy)), this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	@Override
	public Handle getHandle(long timeOut,
			FluentContextStrategy fluentHandleStrategy)
			throws NoSuchContextException {
		SingleContext context = new SingleContext(getStringHandle(timeOut,
				isSupportActivities(fluentHandleStrategy)), this);
		return returnNewCreatedListenableHandle(context,
				MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	@Override
	String getStringHandle(FluentContextStrategy fluentHandleStrategy)
			throws NoSuchContextException {
		Long time = getTimeOut(getHandleWaitingTimeOut()
				.getHandleWaitingTimeOut());
		return getStringHandle(time, fluentHandleStrategy);
	}

	@Override
	String getStringHandle(long timeOut,
			FluentContextStrategy fluentHandleStrategy)
			throws NoSuchContextException {
		FluentContextStrategy clone = fluentHandleStrategy.cloneThis();
		try {
			return awaiting.awaitCondition(timeOut,
					clone.getExpectedCondition(handleWaiting));
		} catch (TimeoutException e) {
			throw new NoSuchContextException(
					"Can't find context! Condition is " + clone.toString(), e);
		}
	}

}
