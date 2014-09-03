package org.arachnidium.model.mobile;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.core.components.mobile.ComplexFinder;
import org.arachnidium.core.components.mobile.KeyEventSender;
import org.arachnidium.core.components.mobile.Pinch;
import org.arachnidium.core.components.mobile.ScrollerTo;
import org.arachnidium.core.components.mobile.Swipe;
import org.arachnidium.core.components.mobile.Tap;
import org.arachnidium.core.components.mobile.TouchActionsPerformer;
import org.arachnidium.core.components.mobile.Zoomer;
import org.arachnidium.core.interfaces.IHasActivity;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.util.proxy.DefaultInterceptor;
import org.arachnidium.util.proxy.EnhancedProxyFactory;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.remote.RemoteWebElement;

/**
 * Can be used to describe a single mobile app context or its fragment
 */
public abstract class Screen extends FunctionalPart<MobileScreen> implements IHasActivity,
Rotatable {

	/**
	 * {@link WebElement} implementations are not instances of
	 * {@link RemoteWebElement} some times They wrap object of
	 * {@link RemoteWebElement}. So it should be unpacked before some touch
	 * action is performed
	 *
	 */
	protected class TouchActions {
		private final Screen screen;

		private TouchActions(Screen screen) {
			this.screen = screen;
		}

		public TouchAction getTouchAction() {
			return EnhancedProxyFactory.getProxy(TouchAction.class,
					new Class<?>[] { MobileDriver.class },
					new Object[] { (MobileDriver) screen
				.getWrappedDriver() },
				new TouchActionsInterceptor());
		}
	}

	private static class TouchActionsInterceptor extends DefaultInterceptor {
		/**
		 * Unpacks wrapped {@link RemoteWebElement} before some method of a
		 * {@link TouchAction} instance is performed
		 */
		@Override
		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable {
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				if (arg instanceof WebElement) {
					while (arg instanceof WrapsElement)
						arg = ((WrapsElement) arg).getWrappedElement();
					args[i] = arg;
				}
			}
			return super.intercept(obj, method, args, proxy);
		}
	}
	
	protected final TouchActionsPerformer touchActionsPerformer;
	protected final KeyEventSender keyEventSender;
	protected final TouchActions touchActions = new TouchActions(this);
	protected final Tap tap;
	protected final Swipe swipe;
	protected final Pinch pinch;
	protected final Zoomer zoomer;
	protected final ScrollerTo scroller;
	protected final ComplexFinder complexFinder;

	protected Screen(FunctionalPart<MobileScreen> parent) {
		this(parent, new HowToGetByFrames());
	}

	protected Screen(FunctionalPart<MobileScreen> parent, HowToGetByFrames path) {
		super(parent, path);
		touchActionsPerformer = getComponent(TouchActionsPerformer.class);
		keyEventSender = getComponent(KeyEventSender.class);
		tap = getComponent(Tap.class);
		swipe = getComponent(Swipe.class);
		pinch = getComponent(Pinch.class);
		zoomer = getComponent(Zoomer.class);
		scroller = getComponent(ScrollerTo.class);
		complexFinder = getComponent(ComplexFinder.class);
	}

	protected Screen(MobileScreen context) {
		this(context, new HowToGetByFrames());
	}

	protected Screen(MobileScreen context, HowToGetByFrames path) {
		super(context, path);
		touchActionsPerformer = getComponent(TouchActionsPerformer.class);
		keyEventSender = getComponent(KeyEventSender.class);
		tap = getComponent(Tap.class);
		swipe = getComponent(Swipe.class);
		pinch = getComponent(Pinch.class);
		zoomer = getComponent(Zoomer.class);
		scroller = getComponent(ScrollerTo.class);
		complexFinder = getComponent(ComplexFinder.class);
	}

	@Override
	public String currentActivity() {
		return ((MobileScreen) handle).currentActivity();
	}

	@Override
	public ScreenOrientation getOrientation() {
		return ((MobileScreen) handle).getOrientation();
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		((MobileScreen) handle).rotate(orientation);
	}

}
