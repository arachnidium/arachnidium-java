package com.github.arachnidium.model.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.DeviceActionShortcuts;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ScrollsTo;
import io.appium.java_client.TouchShortcuts;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.core.components.mobile.TouchActionsPerformer;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;

/**
 * Can be used to describe a single mobile app native screen or its fragment
 */
@IfMobileContext(regExp = "NATIVE_APP")
public abstract class NativeContent extends FunctionalPart<MobileScreen> implements 
DeviceActionShortcuts, TouchShortcuts, ScrollsTo, Rotatable {

	protected final TouchActionsPerformer touchActionsPerformer;
	
	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 */	
	protected NativeContent(NativeContent parent) {
		super(parent);
		touchActionsPerformer = getComponent(TouchActionsPerformer.class);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle)
	 */
	protected NativeContent(MobileScreen context) {
		super(context);
		touchActionsPerformer = getComponent(TouchActionsPerformer.class);
	}

	@InteractiveMethod
	@Override
	public void hideKeyboard() {
		((AppiumDriver) getWrappedDriver()).hideKeyboard();		
	}

	@Override
	@InteractiveMethod
	public void sendKeyEvent(int key) {
		((AppiumDriver) getWrappedDriver()).sendKeyEvent(key);		
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

	@Override
	public ScreenOrientation getOrientation() {
		return rotator.getOrientation();
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		rotator.rotate(orientation);		
	}	
}
