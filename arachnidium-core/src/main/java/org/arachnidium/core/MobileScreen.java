package org.arachnidium.core;

import org.arachnidium.core.components.mobile.Rotator;
import org.arachnidium.core.interfaces.IContext;
import org.arachnidium.core.interfaces.IHasActivity;
import org.openqa.selenium.ScreenOrientation;

public class MobileScreen extends Handle implements IHasActivity,
		IContext {

	private final Rotator rotator;

	MobileScreen(String context, ScreenManager manager) {
		super(context, manager);
		rotator = driverEncapsulation.getComponent(Rotator.class);
	}

	@Override
	public String currentActivity() {
		return ((ScreenManager) nativeManager).getActivityByHandle(handle);
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

}
