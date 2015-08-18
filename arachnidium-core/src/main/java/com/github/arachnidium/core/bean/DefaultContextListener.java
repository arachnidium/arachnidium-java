package com.github.arachnidium.core.bean;

import io.appium.java_client.android.AndroidDriver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

import com.github.arachnidium.core.eventlisteners.IContextListener;
import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import com.github.arachnidium.core.settings.ScreenShots;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import com.github.arachnidium.util.logging.Log;

public class DefaultContextListener extends DefaultHandleListener implements IContextListener {
	
	private final List<IContextListener> contextEventListeners = new ArrayList<IContextListener>() {
		private static final long serialVersionUID = 1L;
		{ // SPI
			Iterator<?> providers = ServiceLoader.load(IContextListener.class)
					.iterator();
			while (providers.hasNext())
				add((IContextListener) providers.next());
		}
	};
	private final InvocationHandler contextListenerInvocationHandler = (proxy,
			method, args) -> {
		contextEventListeners.forEach((eventListener) -> {
			try {
				method.invoke(eventListener, args);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			;
		});
		return null;
	};

	private final IContextListener contextListenerProxy = (IContextListener) Proxy
			.newProxyInstance(IContextListener.class.getClassLoader(),
					new Class[] { IContextListener.class },
					contextListenerInvocationHandler);	
	
	public DefaultContextListener(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}

	@Override
	public void beforeIsSwitchedOn(IHasHandle handle) {
		Log.debug("Attempt to switch to context " + handle.getHandle());
		contextListenerProxy.beforeIsSwitchedOn(handle);
	}

	@Override
	public void whenIsSwitchedOn(IHasHandle handle) {
		Log.message("Current context is " + handle.getHandle()
				+ getActivityDescription(handle));
		contextListenerProxy.whenIsSwitchedOn(handle);
	}

	@Override
	public void whenNewHandleIsAppeared(IHasHandle handle) {
		String message = "A new context " + handle.getHandle()
				+ getActivityDescription(handle);
		if (configurationWrapper.getWrappedConfiguration()
				.getSection(ScreenShots.class)
				.getToTakeScreenShotsOfNewHandles()) {
			((ITakesPictureOfItSelf) handle).takeAPictureOfAnInfo(message);
		} else {
			Log.message(message);
		}
		contextListenerProxy.whenNewHandleIsAppeared(handle);
	}

	@Override
	public void beforeIsRotated(IHasHandle handle, ScreenOrientation orientation) {
		Log.debug("Attempt to rotate screen. Context is " + handle.getHandle()
				+ getActivityDescription(handle) + ", new orientation is "
				+ orientation.toString());
		contextListenerProxy.beforeIsRotated(handle, orientation);
	}

	@Override
	public void whenIsRotated(IHasHandle handle, ScreenOrientation orientation) {
		Log.debug("Screen was rotated. Context is " + handle.getHandle()
				+ getActivityDescription(handle) + ", new orientation is "
				+ orientation.toString());
		contextListenerProxy.whenIsRotated(handle, orientation);
	}
	
	private String getActivityDescription(IHasHandle handle) {
		WebDriver wrappedDriver = ((WrapsDriver) handle).getWrappedDriver();
		Class<? extends WebDriver> driverClass = wrappedDriver.getClass();
		if (!AndroidDriver.class.isAssignableFrom(driverClass)) {
			Log.message("Activities are not supported...");
			return "";
		}
		return " Activity is "
				+ ((AndroidDriver<?>) wrappedDriver).currentActivity();
	}
}
