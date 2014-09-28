package org.arachnidium.model.mobile.android;

import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.HasAppStrings;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Screen;
import org.arachnidium.model.support.HowToGetByFrames;

/**
 * The same as {@link Screen} with some capabilities specifically for Android
 * It works only with {@link AndroidDriver}
 */
public abstract class AndroidScreen extends Screen implements HasAppStrings, AndroidDeviceActionShortcuts, IHasActivity{

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 * 
	 * @see Screen#Screen(FunctionalPart)
	 */
	protected AndroidScreen(FunctionalPart<MobileScreen> parent) {
		super(parent);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)
	 * 
	 * @see Screen#Screen(FunctionalPart, HowToGetByFrames)
	 */
	protected AndroidScreen(FunctionalPart<MobileScreen> parent, HowToGetByFrames path) {
		super(parent, path);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle)
	 * 
	 * @see Screen#Screen(MobileScreen)
	 */
	protected AndroidScreen(MobileScreen context) {
		super(context);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle, HowToGetByFrames))
	 * 
	 * @see Screen#Screen(MobileScreen, HowToGetByFrames)
	 */	
	protected AndroidScreen(MobileScreen context, HowToGetByFrames path) {
		super(context, path);
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
