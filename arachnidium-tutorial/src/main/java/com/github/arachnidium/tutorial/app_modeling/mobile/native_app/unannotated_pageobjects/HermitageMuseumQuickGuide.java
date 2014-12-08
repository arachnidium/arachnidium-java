package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.unannotated_pageobjects;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.NativeContent;

public class HermitageMuseumQuickGuide extends NativeContent {

	private MobileElement close;
	
	public HermitageMuseumQuickGuide(MobileScreen context) {
		super(context);
	}
	
	@InteractiveMethod
	@WithImplicitlyWait(timeOut = 60, timeUnit = TimeUnit.SECONDS)
	public void close(){
		close.click();
	}
	
	/**Some more interesting things could be implemented below*/
	/**.................................*/
}
