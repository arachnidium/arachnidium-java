package org.arachnidium.model.mobile.ios;

import io.appium.java_client.ios.GetsNamedTextField;
import io.appium.java_client.ios.IOSDeviceActionShortcuts;
import io.appium.java_client.ios.IOSDriver;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.MobileApplication;
import org.arachnidium.model.mobile.Screen;
import org.arachnidium.model.support.HowToGetByFrames;
import org.openqa.selenium.WebElement;

/**
 * It is the same as {@link MobileApplication} with some capabilities of
 * iOS. It works only with {@link IOSDriver}
 */
public abstract class iOSScreen extends Screen implements IOSDeviceActionShortcuts, 
   GetsNamedTextField{
	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 * 
	 * @see Screen#Screen(FunctionalPart)
	 */
	protected iOSScreen(FunctionalPart<MobileScreen> parent) {
		super(parent);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)
	 * 
	 * @see Screen#Screen(FunctionalPart, HowToGetByFrames)
	 */	
	protected iOSScreen(FunctionalPart<MobileScreen> parent, HowToGetByFrames path) {
		super(parent, path);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)
	 * 
	 * @see Screen#Screen(FunctionalPart, HowToGetByFrames)
	 */	
	protected iOSScreen(MobileScreen context) {
		super(context);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle, HowToGetByFrames))
	 * 
	 * @see Screen#Screen(MobileScreen, HowToGetByFrames)
	 */		
	protected iOSScreen(MobileScreen context, HowToGetByFrames path) {
		super(context, path);
	}

	@Override
	@InteractiveMethod
	public void hideKeyboard(String keyName) {
		((IOSDriver) getWrappedDriver()).hideKeyboard(keyName);		
	}

	@Override
	@InteractiveMethod
	public void hideKeyboard(String strategy, String keyName) {
		((IOSDriver) getWrappedDriver()).hideKeyboard(strategy, keyName);		
	}

	@Override
	@InteractiveMethod
	public void shake() {
		((IOSDriver) getWrappedDriver()).shake();		
	}

	@Override
	@InteractiveMethod
	public WebElement getNamedTextField(String name) {
		return ((IOSDriver) getWrappedDriver()).getNamedTextField(name);
	}
}
