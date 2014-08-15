package org.arachnidium.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.util.Set;

import org.arachnidium.core.bean.MainBeanConfiguration;
import org.arachnidium.core.components.common.AlertHandler;
import org.arachnidium.core.components.mobile.ContextTool;
import org.arachnidium.core.components.mobile.FluentContextConditions;
import org.arachnidium.core.settings.ContextTimeOuts;
import org.arachnidium.util.logging.Log;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class ContextManager extends Manager {
	private final FluentContextConditions fluent;
	private final ContextTool contextTool;
	private final boolean isSupportActivities;

	public ContextManager(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation);
		fluent = getWebDriverEncapsulation().getComponent(
				FluentContextConditions.class);
		contextTool = getWebDriverEncapsulation().getComponent(
				ContextTool.class);
		String mobilePlatform = String
				.valueOf(((RemoteWebDriver) getWebDriverEncapsulation()
						.getWrappedDriver()).getCapabilities().getCapability(
						MobileCapabilityType.PLATFORM_NAME));
		isSupportActivities = mobilePlatform.trim().toUpperCase()
				.equals(MobilePlatform.ANDROID.toUpperCase());
	}

	@Override
	void changeActive(String context) throws NoSuchContextException {
		contextTool.context(context);
	}

	synchronized String getActivityByHandle(String handle)
			throws NoSuchContextException {
		changeActive(handle);
		if (isSupportActivities){
			return ((AppiumDriver) getWrappedDriver()).currentActivity();
		}
		//iOS doesn't support activities. It is frustrating.
		return ""; 
	}

	@Override
	public Alert getAlert() throws NoAlertPresentException {
		ContextTimeOuts timeOuts = getContextTimeOuts();
		return getWebDriverEncapsulation()
				.getComponent(
						AlertHandler.class,
						new Class[] { long.class },
						new Object[] { getTimeOut(
								timeOuts.getSecsForAwaitinAlertPresent(),
						defaultTime) });
	}

	/**
	 * returns context handle by it's name
	 */
	public synchronized Handle getByContextName(String contextName) {
		ContextTimeOuts timeOuts = getContextTimeOuts();
		long timeOut = getTimeOut(timeOuts.getIsContextPresentTimeOut(),
				defaultTime);
		awaiting.awaitCondition(timeOut, fluent.isContextPresent(contextName));
		contextTool.context(contextName);
		SingleContext initedContext = (SingleContext) Handle.isInitiated(
				contextName, this);
		if (initedContext != null)
			return initedContext;
		SingleContext context = new SingleContext(contextName, this);
		return returnNewCreatedListenableHandle(context, MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	/**
	 * returns context handle by it's index
	 */
	@Override
	public synchronized Handle getByIndex(int index) {
		String handle = this.getHandleByIndex(index);
		SingleContext initedContext = (SingleContext) Handle.isInitiated(
				handle, this);
		if (initedContext != null)
			return initedContext;
		SingleContext context = new SingleContext(handle, this);
		return returnNewCreatedListenableHandle(context, MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	private ContextTimeOuts getContextTimeOuts() {
		return getWebDriverEncapsulation().configuration
				.getSection(ContextTimeOuts.class);
	}

	@Override
	/**
	 * returns context by it's index
	 */
	String getHandleByIndex(int index) throws NoSuchContextException {
		try {
			Log.debug("Attempt to get context that is specified by index "
					+ Integer.toString(index) + "...");
			ContextTimeOuts timeOuts = getContextTimeOuts();
			long timeOut = getTimeOut(timeOuts.getContextCountTimeOutSec(),
					defaultTime);
			return awaiting.awaitCondition(timeOut, 100,
					fluent.suchContextWithIndexIsPresent(index));
		} catch (TimeoutException e) {
			throw new NoSuchContextException(
					"Can't find context! Index out of bounds! Specified index is "
					+ Integer.toString(index)
					+ " is more then actual context count", e);
		}
	}

	@Override
	public Set<String> getHandles() {
		return contextTool.getContextHandles();
	}

	/**
	 * returns handle of a new context that we have been waiting for time that
	 * specified in configuration
	 */
	@Override
	public synchronized Handle getNewHandle() {
		SingleContext context = new SingleContext(switchToNew(), this);
		return returnNewCreatedListenableHandle(context, MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	/**
	 * returns handle of a new context that we have been waiting for specified
	 * time
	 */
	@Override
	public synchronized Handle getNewHandle(long timeOutInSeconds) {
		SingleContext context =  new SingleContext(switchToNew(timeOutInSeconds), this);
		return returnNewCreatedListenableHandle(context, MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	/**
	 * returns handle of a new context that we have been waiting for specified
	 * time using context name
	 */
	@Override
	public synchronized Handle getNewHandle(long timeOutInSeconds,
			String contextName) {
		SingleContext context =  new SingleContext(switchToNew(timeOutInSeconds, contextName),
				this);
		return returnNewCreatedListenableHandle(context, MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration using context name
	 */
	@Override
	public synchronized Handle getNewHandle(String contextName) {
		SingleContext context =  new SingleContext(switchToNew(contextName), this);
		return returnNewCreatedListenableHandle(context, MainBeanConfiguration.MOBILE_CONTEXT_BEAN);
	}

	@Override
	/**
	 * returns a new context that we have been waiting for time that
	 * specified in configuration
	 */
	String switchToNew() throws NoSuchContextException {
		ContextTimeOuts timeOuts = getContextTimeOuts();
		long timeOut = getTimeOut(timeOuts.getNewContextTimeOutSec(),
				defaultTimeForNew);
		return switchToNew(timeOut);
	}

	@Override
	/**
	 * returns a new context that we have been waiting for specified
	 * time
	 */
	String switchToNew(long timeOutInSeconds) throws NoSuchContextException {
		try {
			Log.debug("Waiting a new context for "
					+ Long.toString(timeOutInSeconds) + " seconds.");
			String context = awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.newContextIsAppeared());
			changeActive(context);
			return context;
		} catch (TimeoutException e) {
			throw new NoSuchContextException(
					"There is no new context! We have been waiting for "
							+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	@Override
	/**
	 * returns a new context that we have been waiting for specified
	 * time. new context is predefined.
	 */
	String switchToNew(long timeOutInSeconds, String context)
			throws NoSuchContextException {
		try {
			Log.debug("Waiting a new context '" + context + "' for "
					+ Long.toString(timeOutInSeconds));
			awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.isContextPresent(context));
			changeActive(context);
			return context;
		} catch (TimeoutException e) {
			throw new NoSuchContextException("There is no new context '"
					+ context + "'! We have been waiting for "
					+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	/**
	 * returns a new context that we have been waiting for specified time. new
	 * context is predefined. Time out is specified in configuration
	 */
	@Override
	String switchToNew(String context) {
		ContextTimeOuts timeOuts = getContextTimeOuts();
		long timeOut = getTimeOut(timeOuts.getNewContextTimeOutSec(),
				defaultTimeForNew);
		return switchToNew(timeOut, context);
	}

}
