package org.arachnidium.core.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.arachnidium.core.eventlisteners.IContextListener;
import org.arachnidium.core.interfaces.IContext;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import org.arachnidium.core.settings.ScreenShots;
import org.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import org.arachnidium.util.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.ScreenOrientation;

/**
 * @link{IContextListener} general implementor.
 * It listens to mobile context events
 */
@Aspect
public class AspectContextListener extends DefaultHandleListener implements
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

	public AspectContextListener(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}

	/**
	 * @see org.arachnidium.core.eventlisteners.IHandletListener#
	 * beforeIsSwitchedOn(org.arachnidium.core.interfaces.IHasHandle)
	 */
	@Override
	@BeforeTarget(targetClass = IContext.class, targetMethod = "switchToMe")
	public void beforeIsSwitchedOn(@TargetParam IHasHandle handle) {
		Log.debug("Attempt to switch to context " + handle.getHandle());
		contextListenerProxy.beforeIsSwitchedOn(handle);
	}

	/**
	 * @see org.arachnidium.core.eventlisteners.IHandletListener#
	 * whenIsSwitchedOn(org.arachnidium.core.interfaces.IHasHandle)
	 */
	@Override
	@AfterTarget(targetClass = IContext.class, targetMethod = "switchToMe")
	public void whenIsSwitchedOn(@TargetParam IHasHandle handle) {
		Log.message("Current context is " + handle.getHandle()
				+ getActivityDescription(handle));
		contextListenerProxy.whenIsSwitchedOn(handle);
	}

	/**
	 * @see org.arachnidium.core.eventlisteners.IHandletListener#
	 * whenNewHandleIsAppeared(org.arachnidium.core.interfaces.IHasHandle)
	 */
	@Override
	@AfterTarget(targetClass = IContext.class, targetMethod = "whenIsCreated")
	public void whenNewHandleIsAppeared(@TargetParam IHasHandle handle) {
		String message = "A new context " + handle.getHandle()
				+ getActivityDescription(handle);
		if (configurationWrapper.getWrappedConfiguration()
				.getSection(ScreenShots.class).getToTakeScreenShotsOfNewHandles()) {
			((ITakesPictureOfItSelf) handle).takeAPictureOfAnInfo(message);
		} else {
			Log.message(message);
		}
		contextListenerProxy.whenNewHandleIsAppeared(handle);
	}

	private String getActivityDescription(IHasHandle handle) {
		if (!((IContext) handle).isSupportActivities()){
			Log.message("Activities are not supported...");
			return "";
		}
		return " Activity is " + ((IContext) handle).currentActivity();
	}

	/**
	 * @see org.arachnidium.core.bean.AbstractAspect#
	 * doAround(org.aspectj.lang.ProceedingJoinPoint)
	 */
	@Override
	@Around("execution(* org.arachnidium.core.interfaces.IHasHandle.*(..)) || "
			+ "execution(* org.arachnidium.core.interfaces.ISwitchesToItself.*(..)) || "
			+ "execution(* org.openqa.selenium.Rotatable.*(..))")
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

	/**
	 * @see org.arachnidium.core.eventlisteners.IContextListener#beforeIsRotated(org.arachnidium.core.interfaces.IHasHandle,
	 *      org.openqa.selenium.ScreenOrientation)
	 */
	@Override
	@BeforeTarget(targetClass = IContext.class, targetMethod = "rotate")
	public void beforeIsRotated(@TargetParam IHasHandle handle,
			@UseParameter(number = 0) ScreenOrientation orientation) {
		Log.debug("Attempt to rotate screen. Context is " + handle.getHandle()
				+ getActivityDescription(handle) + ", new orientation is "
				+ orientation.toString());
		contextListenerProxy.beforeIsRotated(handle, orientation);
	}

	/**
	 * @see org.arachnidium.core.eventlisteners.IContextListener#whenIsRotated(org.arachnidium.core.interfaces.IHasHandle,
	 *      org.openqa.selenium.ScreenOrientation)
	 */
	@Override
	@AfterTarget(targetClass = IContext.class, targetMethod = "rotate")
	public void whenIsRotated(@TargetParam IHasHandle handle,
			@UseParameter(number = 0) ScreenOrientation orientation) {
		Log.debug("Screen was rotated. Context is " + handle.getHandle()
				+ getActivityDescription(handle) + ", new orientation is "
				+ orientation.toString());
		contextListenerProxy.whenIsRotated(handle, orientation);
	}
}
