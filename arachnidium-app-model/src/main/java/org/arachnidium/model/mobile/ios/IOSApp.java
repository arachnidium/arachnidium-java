package org.arachnidium.model.mobile.ios;

import io.appium.java_client.ios.IOSDeviceActionShortcuts;
import io.appium.java_client.ios.IOSDriver;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.mobile.MobileApplication;

/**
* It is the same as {@link MobileApplication} with some capabilities of
* iOS. It works only with {@link IOSDriver}
*/
public abstract class IOSApp extends MobileApplication implements IOSDeviceActionShortcuts{

	protected IOSApp(MobileScreen context) {
		super(context);
	}

	@Override
	public void hideKeyboard(String keyName) {
		((IOSDriver) getWrappedDriver()).hideKeyboard(keyName);		
	}

	@Override
	public void hideKeyboard(String strategy, String keyName) {
		((IOSDriver) getWrappedDriver()).hideKeyboard(strategy, keyName);		
	}

	@Override
	public void shake() {
		((IOSDriver) getWrappedDriver()).shake();		
	}

}
