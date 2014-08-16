package org.arachnidium.core;

import org.arachnidium.core.components.mobile.Rotator;
import org.arachnidium.core.interfaces.IContext;
import org.arachnidium.core.interfaces.IHasActivity;
import org.openqa.selenium.ScreenOrientation;

public class MobileContext extends Handle implements IHasActivity,
		IContext {

	private final Rotator rotator;

	MobileContext(String context, ContextManager manager) {
		super(context, manager);
		rotator = driverEncapsulation.getComponent(Rotator.class);
	}

	@Override
	public String currentActivity() {
		return ((ContextManager) nativeManager).getActivityByHandle(handle);
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
