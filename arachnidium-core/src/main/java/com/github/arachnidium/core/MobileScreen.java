package com.github.arachnidium.core;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;

import com.github.arachnidium.core.components.mobile.Rotator;
import com.github.arachnidium.core.interfaces.IContext;

/**
 * It is the representation of a mobile screen/context.
 */
public class MobileScreen extends Handle implements IContext {

	private final Rotator rotator;

	MobileScreen(String context, ScreenManager manager) {
		super(context, manager);
		rotator = driverEncapsulation.getComponent(Rotator.class);
	}

	/**
	 * @see org.openqa.selenium.Rotatable#getOrientation()
	 */
	@Override
	public synchronized ScreenOrientation getOrientation() {
		switchToMe();
		return rotator.getOrientation();
	}

	/**
	 * @see org.openqa.selenium.Rotatable#rotate(org.openqa.selenium.ScreenOrientation)
	 */
	@Override
	public synchronized void rotate(ScreenOrientation orientation) {
		switchToMe();
		rotator.rotate(orientation);
	}

	@Override
	public WebDriver getWrappedDriver() {
		return driverEncapsulation.getWrappedDriver();
	}		
}
