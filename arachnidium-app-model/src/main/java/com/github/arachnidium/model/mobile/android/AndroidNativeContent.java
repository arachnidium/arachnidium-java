package com.github.arachnidium.model.mobile.android;

import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.mobile.NativeContent;

/**
 * The same as {@link NativeContent} with some capabilities specifically for Android
 * It works only with {@link AndroidDriver}
 */
public abstract class AndroidNativeContent extends NativeContent implements AndroidDeviceActionShortcuts, IHasActivity{

	/**
	 * @see FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle)
	 * 
	 * @see NativeContent#Screen(MobileScreen)
	 */
	protected AndroidNativeContent(MobileScreen context) {
		super(context);
	}

	@InteractiveMethod
	@Override
	public String currentActivity(){
		return ((AndroidDriver<?>) getWrappedDriver()).currentActivity();
	}

    @InteractiveMethod
    @Override
    public void sendKeyEvent(int i) {
        ((AndroidDriver<?>) getWrappedDriver()).sendKeyEvent(i);
    }

    @InteractiveMethod
	@Override
	public void sendKeyEvent(int key, Integer metastate) {
		((AndroidDriver<?>) getWrappedDriver()).sendKeyEvent(key, metastate);		
	}

}
