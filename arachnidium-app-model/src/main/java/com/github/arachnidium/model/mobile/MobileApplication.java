package com.github.arachnidium.model.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.DeviceActionShortcuts;

import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;

import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.core.ScreenManager;
import com.github.arachnidium.model.common.Application;

/**
 * Representation of a mobile application
 * 
 * @see Application
 */
public abstract class MobileApplication extends Application<MobileScreen, 
    HowToGetMobileScreen> implements DeviceActionShortcuts, LocationContext {

	protected MobileApplication(MobileScreen context) {
		super(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScreenManager getManager() {
		return (ScreenManager) super.getManager();
	}

	@Override
	public void hideKeyboard() {
		((AppiumDriver) getWrappedDriver()).hideKeyboard();		
	}

	@Override
	public void sendKeyEvent(int key) {
		((AppiumDriver) getWrappedDriver()).hideKeyboard();		
	}

	@Override
	public Location location() {
		return ((AppiumDriver) getWrappedDriver()).location();
	}

	@Override
	public void setLocation(Location location) {
		((AppiumDriver) getWrappedDriver()).setLocation(location);		
	}

}
