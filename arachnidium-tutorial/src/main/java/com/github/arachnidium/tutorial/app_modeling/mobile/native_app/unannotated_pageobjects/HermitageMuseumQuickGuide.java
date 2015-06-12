package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.unannotated_pageobjects;

import io.appium.java_client.MobileElement;

import java.util.concurrent.TimeUnit;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.NativeContent;

public class HermitageMuseumQuickGuide extends NativeContent {

	private MobileElement close;
	
	public HermitageMuseumQuickGuide(MobileScreen context) {
		super(context);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	@WithImplicitlyWait(timeOut = 60, 
	timeUnit = TimeUnit.SECONDS)  /**The presence of @InteractiveMethod activates some useful 
	options like this*/
	public void close(){
		close.click();
	}
	
	/**Some more interesting things could be implemented below*/
	/**.................................*/
}
