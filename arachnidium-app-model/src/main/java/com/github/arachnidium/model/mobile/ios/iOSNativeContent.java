package com.github.arachnidium.model.mobile.ios;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.GetsNamedTextField;
import io.appium.java_client.ios.IOSDeviceActionShortcuts;
import io.appium.java_client.ios.IOSDriver;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.mobile.MobileApplication;
import com.github.arachnidium.model.mobile.NativeContent;
import com.github.arachnidium.core.HowToGetByFrames;

/**
 * It is the same as {@link MobileApplication} with some capabilities of
 * iOS. It works only with {@link IOSDriver}
 */
public abstract class iOSNativeContent extends NativeContent implements IOSDeviceActionShortcuts, 
   GetsNamedTextField<MobileElement>{
	
	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)
	 * 
	 * @see NativeContent#Screen(FunctionalPart, HowToGetByFrames)
	 */	
	protected iOSNativeContent(MobileScreen context) {
		super(context);
	}

	@Override
	@InteractiveMethod
	public void hideKeyboard(String keyName) {
		((IOSDriver<?>) getWrappedDriver()).hideKeyboard(keyName);		
	}

	@Override
	@InteractiveMethod
	public void hideKeyboard(String strategy, String keyName) {
		((IOSDriver<?>) getWrappedDriver()).hideKeyboard(strategy, keyName);		
	}

	@Override
	@InteractiveMethod
	public void shake() {
		((IOSDriver<?>) getWrappedDriver()).shake();		
	}

	@SuppressWarnings("unchecked")
	@Override
	@InteractiveMethod
	public MobileElement getNamedTextField(String name) {
		return ((IOSDriver<MobileElement>) getWrappedDriver()).getNamedTextField(name);
	}
}
