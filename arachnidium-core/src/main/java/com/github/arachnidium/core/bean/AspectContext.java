package com.github.arachnidium.core.bean;

import io.appium.java_client.android.AndroidDriver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import com.github.arachnidium.util.logging.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

import com.github.arachnidium.core.eventlisteners.IContextListener;
import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import com.github.arachnidium.core.settings.ScreenShots;

/**
 * @link{IContextListener general implementor. It listens to mobile context
 *                        events
 */
@Aspect
class AspectContext extends AbstractAspect implements
		IContextListener {

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

	public AspectContext(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IHandletListener#
	 *      beforeIsSwitchedOn(com.github.arachnidium.core.interfaces.IHasHandle)
	 */
	@Override
	//@BeforeTarget(targetClass = IContext.class, targetMethod = "switchToMe")
	public void beforeIsSwitchedOn(@TargetParam IHasHandle handle) {
		Log.debug("Attempt to switch to context " + handle.getHandle());
		contextListenerProxy.beforeIsSwitchedOn(handle);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IHandletListener#
	 *      whenIsSwitchedOn(com.github.arachnidium.core.interfaces.IHasHandle)
	 */
	@Override
	//@AfterTarget(targetClass = IContext.class, targetMethod = "switchToMe")
	public void whenIsSwitchedOn(@TargetParam IHasHandle handle) {
		Log.message("Current context is " + handle.getHandle()
				+ getActivityDescription(handle));
		contextListenerProxy.whenIsSwitchedOn(handle);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IHandletListener#
	 *      whenNewHandleIsAppeared(com.github.arachnidium.core.interfaces.IHasHandle)
	 */
	@Override
	//@AfterTarget(targetClass = IContext.class, targetMethod = "whenIsCreated")
	public void whenNewHandleIsAppeared(@TargetParam IHasHandle handle) {
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

	/**
	 * @see com.github.arachnidium.core.bean.AbstractAspect#
	 *      doAround(org.aspectj.lang.ProceedingJoinPoint)
	 */
	@Override
	@Around("execution(* com.github.arachnidium.core.interfaces.IHasHandle.*(..)) || "
			+ "execution(* com.github.arachnidium.core.interfaces.ISwitchesToItself.*(..)) || "
			+ "execution(* org.openqa.selenium.Rotatable.*(..))")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		launchMethod(point, this, WhenLaunch.BEFORE);
		Object result = null;
		try {
			result = point.proceed();
		} catch (Exception e) {
			throw getRootCause(e);
		}
		launchMethod(point, this, WhenLaunch.AFTER);
		return result;
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IContextListener#beforeIsRotated(com.github.arachnidium.core.interfaces.IHasHandle,
	 *      org.openqa.selenium.ScreenOrientation)
	 */
	@Override
	//@BeforeTarget(targetClass = IContext.class, targetMethod = "rotate")
	public void beforeIsRotated(@TargetParam IHasHandle handle,
			@UseParameter(number = 0) ScreenOrientation orientation) {
		Log.debug("Attempt to rotate screen. Context is " + handle.getHandle()
				+ getActivityDescription(handle) + ", new orientation is "
				+ orientation.toString());
		contextListenerProxy.beforeIsRotated(handle, orientation);
	}

	/**
	 * @see com.github.arachnidium.core.eventlisteners.IContextListener#whenIsRotated(com.github.arachnidium.core.interfaces.IHasHandle,
	 *      org.openqa.selenium.ScreenOrientation)
	 */
	@Override
	//@AfterTarget(targetClass = IContext.class, targetMethod = "rotate")
	public void whenIsRotated(@TargetParam IHasHandle handle,
			@UseParameter(number = 0) ScreenOrientation orientation) {
		Log.debug("Screen was rotated. Context is " + handle.getHandle()
				+ getActivityDescription(handle) + ", new orientation is "
				+ orientation.toString());
		contextListenerProxy.whenIsRotated(handle, orientation);
	}
}
