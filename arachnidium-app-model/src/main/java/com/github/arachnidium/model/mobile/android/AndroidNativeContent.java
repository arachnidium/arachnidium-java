package com.github.arachnidium.model.mobile.android;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.mobile.NativeContent;
import com.github.arachnidium.model.support.HowToGetByFrames;

/**
 * The same as {@link NativeContent} with some capabilities specifically for Android
 * It works only with {@link AndroidDriver}
 */
public abstract class AndroidNativeContent extends NativeContent implements AndroidDeviceActionShortcuts, IHasActivity{

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 * 
	 * @see NativeContent#Screen(FunctionalPart)
	 */
	protected AndroidNativeContent(NativeContent parent, @Deprecated HowToGetByFrames ignored, By by) {
		super(parent, ignored, by);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle)
	 * 
	 * @see NativeContent#Screen(MobileScreen)
	 */
	protected AndroidNativeContent(MobileScreen context, @Deprecated HowToGetByFrames ignored, By by) {
		super(context, ignored, by);
	}

	@InteractiveMethod
	@Override
	public String currentActivity(){
		return ((AndroidDriver) getWrappedDriver()).currentActivity();
	}

	@InteractiveMethod
	@Override
	public void sendKeyEvent(int key, Integer metastate) {
		((AndroidDriver) getWrappedDriver()).sendKeyEvent(key, metastate);		
	}

}
