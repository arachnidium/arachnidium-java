package com.github.arachnidium.core;

import io.appium.java_client.android.AndroidDriver;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.github.arachnidium.core.bean.MainBeanConfiguration;
import com.github.arachnidium.core.components.mobile.ContextTool;
import com.github.arachnidium.core.fluenthandle.FluentPageWaiting;
import com.github.arachnidium.core.fluenthandle.FluentScreenWaiting;
import com.github.arachnidium.util.logging.Log;

public final class ScreenManager extends Manager<HowToGetMobileScreen, MobileScreen> {
	private final ContextTool contextTool;
	private final boolean isSupportActivities;
	
	private final String NATIVE_APP_CONTEXT = "NATIVE_APP";
	private String SPLITTER = "/";
	private String WEBVIEW_CONTEXT = "WEBVIEW";

	public ScreenManager(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation);
		contextTool = getWebDriverEncapsulation().getComponent(
				ContextTool.class);
		WebDriver wrappedDriver = getWebDriverEncapsulation()
				.getWrappedDriver();
		isSupportActivities = AndroidDriver.class
				.isAssignableFrom(wrappedDriver.getClass());
	}

	/**
	 * Changes active context/WebView page
	 * 
	 * @see com.github.arachnidium.core.Manager#changeActive(java.lang.String)
	 */
	@Override
	void changeActive(String context) throws NoSuchContextException {
		String[] handles = context.split(SPLITTER);
		contextTool.context(handles[0]);
		if (handles.length == 1){
			return;
		}
		if (handles.length == 2 && handles[0].contains(NATIVE_APP_CONTEXT))
			throw new IllegalArgumentException("In cases when you want to get to the page you should be "
					+ "inside " + WEBVIEW_CONTEXT + " context. The current context is " + handles[0]);
		getWrappedDriver().switchTo().window(handles[1]);		
	}

	private HowToGetMobileScreen isSupportActivities(
			HowToGetMobileScreen howToGet) {
		if (!isSupportActivities) {
			howToGet.setExpected((List<String>) null);
		}
		return howToGet;
	}

	/**
	 * @see com.github.arachnidium.core.Manager#getHandles()
	 */
	@Override
	public Set<String> getHandles() {
		return contextTool.getContextHandles();
	}

	@Override
	String getStringHandle(long timeOut, HowToGetMobileScreen howToGet)
			throws NoSuchContextException {
		HowToGetMobileScreen clone = howToGet.cloneThis();
		HowToGetPage howToGetPage = clone.getHowToGetPageStrategy();
		
		//This expected condition tries to find context.
		//If WEBVIEW is found and page parameters are defined 
		//then it attempts to find a required page inside WEBVIEW
		ExpectedCondition<String> ec = input -> {
			String context = clone.getExpectedCondition(new FluentScreenWaiting()).apply(input);
			if (howToGetPage == null)
				return context;
			
			if (context.contains(NATIVE_APP_CONTEXT)){
				Log.debug("In cases when you want to get to the page you should be "
						+ "inside " + WEBVIEW_CONTEXT + " context. The current context is " + context + "."
								+ " So " + howToGetPage.toString() + " has been ignored.");
				return context;
			}				
			String pageHandle = howToGetPage.getExpectedCondition(new FluentPageWaiting()).apply(input);
			if (pageHandle == null){
				return null;
			}
			else{
				return context + SPLITTER + pageHandle;
			}
		};
		
		try {						
			return awaiting.
					awaitCondition(timeOut, ec);
			
		} catch (TimeoutException e) {
			String errorMessage = "Can't find screen! Condition is "
					+ clone.toString() + ".";
			if (howToGetPage != null){
				errorMessage = errorMessage + " Defined page is " + howToGetPage.toString();
			}
			throw new NoSuchContextException(errorMessage, e);
		}
	}

	@Override
	MobileScreen getRealHandle(long timeOut,
			HowToGetMobileScreen howToGet) {
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
}
