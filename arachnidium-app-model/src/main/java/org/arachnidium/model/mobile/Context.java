package org.arachnidium.model.mobile;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.core.SingleContext;
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
import org.arachnidium.model.support.FramePathStrategy;
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
public abstract class Context extends FunctionalPart implements IHasActivity,
Rotatable {

	/**
	 * {@link WebElement} implementations are not instances of
	 * {@link RemoteWebElement} some times They wrap object of
	 * {@link RemoteWebElement}. So it should be unpacked before some touch
	 * action is performed
	 *
	 */
	protected class TouchActions {
		private final Context context;

		private TouchActions(Context context) {
			this.context = context;
		}

		public TouchAction getTouchAction() {
			return EnhancedProxyFactory.getProxy(TouchAction.class,
					new Class<?>[] { MobileDriver.class },
					new Object[] { (MobileDriver) context.driverEncapsulation
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

	protected Context(FunctionalPart parent) {
		this(parent, new FramePathStrategy());
	}

	protected Context(FunctionalPart parent, FramePathStrategy pathStrategy) {
		super(parent, pathStrategy);
		touchActionsPerformer = driverEncapsulation.getComponent(TouchActionsPerformer.class);
		keyEventSender = driverEncapsulation.getComponent(KeyEventSender.class);
		tap = driverEncapsulation.getComponent(Tap.class);
		swipe = driverEncapsulation.getComponent(Swipe.class);
		pinch = driverEncapsulation.getComponent(Pinch.class);
		zoomer = driverEncapsulation.getComponent(Zoomer.class);
		scroller = driverEncapsulation.getComponent(ScrollerTo.class);
		complexFinder = driverEncapsulation.getComponent(ComplexFinder.class);
	}

	protected Context(SingleContext context) {
		this(context, new FramePathStrategy());
	}

	protected Context(SingleContext context, FramePathStrategy pathStrategy) {
		super(context, pathStrategy);
		touchActionsPerformer = driverEncapsulation.getComponent(TouchActionsPerformer.class);
		keyEventSender = driverEncapsulation.getComponent(KeyEventSender.class);
		tap = driverEncapsulation.getComponent(Tap.class);
		swipe = driverEncapsulation.getComponent(Swipe.class);
		pinch = driverEncapsulation.getComponent(Pinch.class);
		zoomer = driverEncapsulation.getComponent(Zoomer.class);
		scroller = driverEncapsulation.getComponent(ScrollerTo.class);
		complexFinder = driverEncapsulation.getComponent(ComplexFinder.class);
	}

	@Override
	public String currentActivity() {
		return ((SingleContext) handle).currentActivity();
	}

	@Override
	public ScreenOrientation getOrientation() {
		return ((SingleContext) handle).getOrientation();
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		((SingleContext) handle).rotate(orientation);
	}

}
