package com.github.arachnidium.core.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;

import com.github.arachnidium.core.eventlisteners.IWindowListener;
import com.github.arachnidium.core.interfaces.IExtendedWindow;
import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import com.github.arachnidium.core.settings.ScreenShots;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import com.github.arachnidium.util.logging.Log;

public class DefaultWindowListener extends DefaultHandleListener implements IWindowListener {

	private final List<IWindowListener> windowEventListeners = new ArrayList<IWindowListener>(){
		private static final long serialVersionUID = 1L;
		{   //SPI
			Iterator<?> providers = ServiceLoader.load(
					IWindowListener.class).iterator();
			while (providers.hasNext())
				add((IWindowListener) providers.next());	
		}		
	};
	private final InvocationHandler windowListenerInvocationHandler = (proxy,
			method, args) -> {
		windowEventListeners.forEach((eventListener) -> {
			try {
				method.invoke(eventListener, args);
			} catch (Exception e) {
				throw new RuntimeException(e);
			};
		});
		return null;
	};

	private final IWindowListener windowListenerProxy = (IWindowListener) Proxy
			.newProxyInstance(IWindowListener.class.getClassLoader(),
					new Class[] { IWindowListener.class },
					windowListenerInvocationHandler);	
	
	public DefaultWindowListener(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}	
	
	@Override
	public void beforeIsSwitchedOn(IHasHandle handle) {
		Log.debug("Attempt to switch window on by handle " + handle.getHandle());
		windowListenerProxy.beforeIsSwitchedOn(handle);		
	}

	@Override
	public void whenIsSwitchedOn(IHasHandle handle) {
		postWindowUrl((IExtendedWindow) handle);
		windowListenerProxy.whenIsSwitchedOn(handle);		
	}

	@Override
	public void whenNewHandleIsAppeared(IHasHandle handle) {
		if (configurationWrapper.getWrappedConfiguration()
				.getSection(ScreenShots.class).getToTakeScreenShotsOfNewHandles()) {
			((ITakesPictureOfItSelf) handle)
					.takeAPictureOfAnInfo("The new window/tab");
		}
		else {
			Log.message("A new window/tab is here");
			postWindowUrl((IExtendedWindow) handle); 
		}
		windowListenerProxy.whenNewHandleIsAppeared(handle);	
	}

	@Override
	public void beforeWindowIsClosed(IExtendedWindow window) {
		Log.message("Attempt to close window...");
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsClosed(window);		
	}

	@Override
	public void beforeWindowIsMaximized(IExtendedWindow window) {
		Log.message("Attempt to maximize window");
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsMaximized(window);		
	}

	@Override
	public void beforeWindowIsMoved(IExtendedWindow window, Point point) {
		Log.message("Attempt to change window position. X "
				+ Integer.toString(point.getX()) + " Y "
				+ Integer.toString(point.getY()));
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsMoved(window, point);		
	}

	@Override
	public void beforeWindowIsRefreshed(IExtendedWindow window) {
		Log.message("Attempt to refresh window");
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsRefreshed(window);		
	}

	@Override
	public void beforeWindowIsResized(IExtendedWindow window,
			Dimension dimension) {
		Log.message("Attempt to change window size. New height is "
				+ Integer.toString(dimension.getHeight()) + " new width is "
				+ Integer.toString(dimension.getWidth()));
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsResized(window, dimension);
		
	}

	@Override
	public void whenWindowIsClosed(IExtendedWindow window) {
		Log.message("Not any problem has occurred when window was closed...");
		windowListenerProxy.whenWindowIsClosed(window);		
	}

	@Override
	public void whenWindowIsMaximized(IExtendedWindow window) {
		Log.message("Window has been maximized");
		windowListenerProxy.whenWindowIsMaximized(window);		
	}

	@Override
	public void whenWindowIsMoved(IExtendedWindow window, Point point) {
		Log.message("Window position has been changed to X "
				+ Integer.toString(point.getX()) + " Y "
				+ Integer.toString(point.getY()));
		postWindowUrl(window);
		windowListenerProxy.whenWindowIsMoved(window, point);		
	}

	@Override
	public void whenWindowIsRefreshed(IExtendedWindow window) {
		Log.message("Current window has been refreshed");
		postWindowUrl(window);
		windowListenerProxy.whenWindowIsRefreshed(window);		
	}

	@Override
	public void whenWindowIsResized(IExtendedWindow window, Dimension dimension) {
		Log.message("Window size has been changed! New height is "
				+ Integer.toString(dimension.getHeight()) + " new width is "
				+ Integer.toString(dimension.getWidth()));
		postWindowUrl(window);
		windowListenerProxy.whenWindowIsResized(window, dimension);		
	}
	
	private void postWindowUrl(IExtendedWindow window) {
		try {
			Log.message("URL is " + window.getCurrentUrl());
		} catch (TimeoutException e) {
			Log.debug("Couldn't get the current URL. " + e.getClass() + " :"
					+ e.getMessage() + " was caught");
		}
	}
}
