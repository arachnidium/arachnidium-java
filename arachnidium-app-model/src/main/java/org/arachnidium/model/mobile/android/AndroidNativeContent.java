package org.arachnidium.model.mobile.android;

import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.HasAppStrings;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.NativeContent;

/**
 * The same as {@link NativeContent} with some capabilities specifically for Android
 * It works only with {@link AndroidDriver}
 */
public abstract class AndroidNativeContent extends NativeContent implements HasAppStrings, AndroidDeviceActionShortcuts, IHasActivity{

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 * 
	 * @see NativeContent#Screen(FunctionalPart)
	 */
	protected AndroidNativeContent(NativeContent parent) {
		super(parent);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle)
	 * 
	 * @see NativeContent#Screen(MobileScreen)
	 */
	protected AndroidNativeContent(MobileScreen context) {
		super(context);
	}

	@InteractiveMethod
	@Override
	public String currentActivity(){
		return ((AndroidDriver) getWrappedDriver()).currentActivity();
	}

	@InteractiveMethod
	@Override
	public String getAppStrings() {
		return ((AndroidDriver) getWrappedDriver()).getAppStrings();
	}

	@InteractiveMethod
	@Override
	public String getAppStrings(String language) {
		return ((AndroidDriver) getWrappedDriver()).getAppStrings(language);
	}

	@InteractiveMethod
	@Override
	public void sendKeyEvent(int key, Integer metastate) {
		((AndroidDriver) getWrappedDriver()).sendKeyEvent(key, metastate);		
	}

}
