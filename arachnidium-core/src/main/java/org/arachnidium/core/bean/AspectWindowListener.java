package org.arachnidium.core.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.arachnidium.core.eventlisteners.IWindowListener;
import org.arachnidium.core.interfaces.IExtendedWindow;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import org.arachnidium.core.webdriversettings.ScreenShots;
import org.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import org.arachnidium.util.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

/**
 * @author s.tihomirov Implementation of @link{IWindowListener} by default
 * Listens to browser window events
 */
@Aspect
class AspectWindowListener extends DefaultHandleListener implements
		IWindowListener {
	
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
	/**
	 * It listens to window events and invokes listener methods
	 */
	private final IWindowListener windowListenerProxy = (IWindowListener) Proxy
			.newProxyInstance(IWindowListener.class.getClassLoader(),
					new Class[] { IWindowListener.class },
					windowListenerInvocationHandler);	
	
	public AspectWindowListener(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}

	@Override
	@BeforeTarget(targetClass = IExtendedWindow.class, targetMethod = "switchToMe")
	public void beforeIsSwitchedOn(@TargetParam IHasHandle handle) {
		Log.debug("Attempt to switch window on by handle " + handle.getHandle());
		windowListenerProxy.beforeIsSwitchedOn(handle);
	}

	@Override
	@BeforeTarget(targetClass = IExtendedWindow.class, targetMethod = "close")
	public void beforeWindowIsClosed(@TargetParam IExtendedWindow window) {
		Log.message("Attempt to close window...");
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsClosed(window);
	}

	@Override
	@BeforeTarget(targetClass = IExtendedWindow.class, targetMethod = "maximize")
	public void beforeWindowIsMaximized(@TargetParam IExtendedWindow window) {
		Log.message("Attempt to maximize window");
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsMaximized(window);
	}

	@Override
	@BeforeTarget(targetClass = IExtendedWindow.class, targetMethod = "setPosition")
	public void beforeWindowIsMoved(@TargetParam IExtendedWindow window, @UseParameter(number =0) Point point) {
		Log.message("Attempt to change window position. X "
				+ Integer.toString(point.getX()) + " Y "
				+ Integer.toString(point.getY()));
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsMoved(window, point);
	}

	@Override
	@BeforeTarget(targetClass = IExtendedWindow.class, targetMethod = "refresh")
	public void beforeWindowIsRefreshed(@TargetParam IExtendedWindow window) {
		Log.message("Attempt to refresh window");
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsRefreshed(window);
	}

	@Override
	@BeforeTarget(targetClass = IExtendedWindow.class, targetMethod = "setSize")
	public void beforeWindowIsResized(@TargetParam  IExtendedWindow window,
			@UseParameter(number=0) Dimension dimension) {
		Log.message("Attempt to change window size. New height is "
				+ Integer.toString(dimension.getHeight()) + " new width is "
				+ Integer.toString(dimension.getWidth()));
		postWindowUrl(window);
		windowListenerProxy.beforeWindowIsResized(window, dimension);
	}

	private void postWindowUrl(IExtendedWindow window) {
		Log.message("URL is " + window.getCurrentUrl());
	}

	@Override
	@AfterTarget(targetClass = IExtendedWindow.class, targetMethod = "switchToMe")
	public void whenIsSwitchedOn(@TargetParam IHasHandle handle) {
		postWindowUrl((IExtendedWindow) handle);
		windowListenerProxy.whenIsSwitchedOn(handle);
	}

	@Override
	@AfterTarget(targetClass = IExtendedWindow.class, targetMethod = "whenIsCreated")
	public void whenNewHandleIsAppeared(@TargetParam IHasHandle handle) {
		if (configurationWrapper.getWrappedConfiguration()
				.getSection(ScreenShots.class).getToDoScreenShotsOfNewHandles()) {
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
	@AfterTarget(targetClass = IExtendedWindow.class, targetMethod = "close")
	public void whenWindowIsClosed(@TargetParam IExtendedWindow window) {
		Log.message("Not any problem has occurred when window was closed...");
		windowListenerProxy.whenWindowIsClosed(window);
	}

	@Override
	@AfterTarget(targetClass = IExtendedWindow.class, targetMethod = "maximize")
	public void whenWindowIsMaximized(@TargetParam IExtendedWindow window) {
		Log.message("Window has been maximized");
		windowListenerProxy.whenWindowIsMaximized(window);
	}

	@Override
	@AfterTarget(targetClass = IExtendedWindow.class, targetMethod = "setPosition")
	public void whenWindowIsMoved(@TargetParam IExtendedWindow window, @UseParameter(number =0) Point point) {
		Log.message("Window position has been changed to X "
				+ Integer.toString(point.getX()) + " Y "
				+ Integer.toString(point.getY()));
		postWindowUrl(window);
		windowListenerProxy.whenWindowIsMoved(window, point);
	}

	@Override
	@AfterTarget(targetClass = IExtendedWindow.class, targetMethod = "refresh")
	public void whenWindowIsRefreshed(@TargetParam IExtendedWindow window) {
		Log.message("Current window has been refreshed");
		postWindowUrl(window);
		windowListenerProxy.whenWindowIsRefreshed(window);
	}

	@Override
	@AfterTarget(targetClass = IExtendedWindow.class, targetMethod = "setSize")
	public void whenWindowIsResized(@TargetParam IExtendedWindow window, @UseParameter(number=0) Dimension dimension) {
		Log.message("Window size has been changed! New height is "
				+ Integer.toString(dimension.getHeight()) + " new width is "
				+ Integer.toString(dimension.getWidth()));
		postWindowUrl(window);
		windowListenerProxy.whenWindowIsResized(window, dimension);
	}

	@Override
	@Around("execution(* org.arachnidium.core.interfaces.IExtendedWindow.*(..)) || "
			+ "execution(* org.arachnidium.core.interfaces.IHasHandle.*(..)) || "
			+ "execution(* org.arachnidium.core.interfaces.ISwitchesToItself.*(..)) || " +
			"execution(* org.openqa.selenium.WebDriver.Window.*(..))")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		launchMethod(point, this, WhenLaunch.BEFORE);
		Object result = null;
		try {
			result = point.proceed();
		} catch (Exception e) {
			throw e;
		}
		launchMethod(point, this, WhenLaunch.AFTER);	
		return result;
	}

}
