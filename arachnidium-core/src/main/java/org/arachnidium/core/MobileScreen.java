package org.arachnidium.core;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.components.mobile.Rotator;
import org.arachnidium.core.interfaces.IContext;
import org.openqa.selenium.ScreenOrientation;

public class MobileScreen extends Handle implements IContext {

	private final Rotator rotator;

	MobileScreen(String context, ScreenManager manager) {
		super(context, manager);
		rotator = getDriverEncapsulation().getComponent(Rotator.class);
	}

	@Override
	public synchronized ScreenOrientation getOrientation() {
		switchToMe();
		return rotator.getOrientation();
	}

	@Override
	public synchronized void rotate(ScreenOrientation orientation) {
		switchToMe();
		rotator.rotate(orientation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScreenManager getManager() {
		return super.getManager();
	}

	@Override
	public boolean isSupportActivities() {
		return getManager().isSupportActivities();
	}

	@Override
	public synchronized String currentActivity() {
		if (!isSupportActivities()) {
			
			return "";
		}
		return ((AppiumDriver) getDriverEncapsulation().getWrappedDriver())
				.currentActivity();
	}
	
	
}
