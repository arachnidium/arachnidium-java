package com.github.arachnidium.model.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.DeviceActionShortcuts;
import io.appium.java_client.HasAppStrings;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ScrollsTo;
import io.appium.java_client.TouchShortcuts;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.core.components.mobile.NativeTouchActions;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.core.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.ExpectedContext;
import com.github.arachnidium.model.support.annotations.MobileContextNamePatterns;

/**
 * Can be used to describe a single mobile app native screen or its fragment
 */
@ExpectedContext(regExp = MobileContextNamePatterns.NATIVE)
public abstract class NativeContent extends FunctionalPart<MobileScreen> implements Rotatable, 
DeviceActionShortcuts, TouchShortcuts, ScrollsTo, HasAppStrings {

	protected final NativeTouchActions touchActions;
	
	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 */	
	protected NativeContent(NativeContent parent, @Deprecated HowToGetByFrames ignored, By by) {
		super(parent, null, by);
		touchActions = getComponent(NativeTouchActions.class);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle)
	 */
	protected NativeContent(MobileScreen context, @Deprecated HowToGetByFrames ignored, By by) {
		super(context, null, by);
		touchActions = getComponent(NativeTouchActions.class);
	}
	

	/**
	 * @see org.openqa.selenium.Rotatable#getOrientation()
	 */
	@Override
	public ScreenOrientation getOrientation() {
		return ((MobileScreen) handle).getOrientation();
	}

	/**
	 * @see org.openqa.selenium.Rotatable#rotate(org.openqa.selenium.ScreenOrientation)
	 */
	@Override
	public void rotate(ScreenOrientation orientation) {
		((MobileScreen) handle).rotate(orientation);
	}

	@InteractiveMethod
	@Override
	public void hideKeyboard() {
		((AppiumDriver) getWrappedDriver()).hideKeyboard();		
	}

	@InteractiveMethod
	@Override
	public void zoom(int x, int y) {
		((AppiumDriver) getWrappedDriver()).zoom(x, y);		
	}

	@InteractiveMethod
	@Override
	public void zoom(WebElement el) {
		((AppiumDriver) getWrappedDriver()).zoom(el);		
	}

	@InteractiveMethod
	@Override
	public void tap(int fingers, int x, int y, int duration) {
		((AppiumDriver) getWrappedDriver()).tap(fingers, x, y, duration);		
	}

	@InteractiveMethod
	@Override
	public void tap(int fingers, WebElement element, int duration) {
		((AppiumDriver) getWrappedDriver()).tap(fingers, element, duration);		
	}

	@InteractiveMethod
	@Override
	public void swipe(int startx, int starty, int endx, int endy, int duration) {
		((AppiumDriver) getWrappedDriver()).swipe(startx, starty, endx, endy, duration);		
	}

	@InteractiveMethod
	@Override
	public void pinch(int x, int y) {
		((AppiumDriver) getWrappedDriver()).pinch(x, y);		
	}

	@InteractiveMethod
	@Override
	public void pinch(WebElement el) {
		((AppiumDriver) getWrappedDriver()).pinch(el);		
	}
	
	@Override
	@InteractiveMethod
	public MobileElement scrollTo(String text) {
		return ((AppiumDriver) getWrappedDriver()).scrollTo(text);
	}

	@Override
	@InteractiveMethod
	public MobileElement scrollToExact(String text) {
		return ((AppiumDriver) getWrappedDriver()).scrollToExact(text);
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
	
	
}
