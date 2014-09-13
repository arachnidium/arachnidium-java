package org.arachnidium.model.mobile;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.core.components.mobile.KeyEventSender;
import org.arachnidium.core.components.mobile.Pinch;
import org.arachnidium.core.components.mobile.ScrollerTo;
import org.arachnidium.core.components.mobile.Swipe;
import org.arachnidium.core.components.mobile.Tap;
import org.arachnidium.core.components.mobile.TouchActionsPerformer;
import org.arachnidium.core.components.mobile.Zoomer;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.HowToGetByFrames;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;

/**
 * Can be used to describe a single mobile app screen or its fragment
 */
public abstract class Screen extends FunctionalPart<MobileScreen> implements Rotatable {

	protected final TouchActionsPerformer touchActionsPerformer;
	protected final KeyEventSender keyEventSender;
	protected final Tap tap;
	protected final Swipe swipe;
	protected final Pinch pinch;
	protected final Zoomer zoomer;
	protected final ScrollerTo scroller;
	
	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 */	
	protected Screen(FunctionalPart<MobileScreen> parent) {
		this(parent, new HowToGetByFrames());
	}

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)
	 */
	protected Screen(FunctionalPart<MobileScreen> parent, HowToGetByFrames path) {
		super(parent, path);
		touchActionsPerformer = getComponent(TouchActionsPerformer.class);
		keyEventSender = getComponent(KeyEventSender.class);
		tap = getComponent(Tap.class);
		swipe = getComponent(Swipe.class);
		pinch = getComponent(Pinch.class);
		zoomer = getComponent(Zoomer.class);
		scroller = getComponent(ScrollerTo.class);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle)
	 */
	protected Screen(MobileScreen context) {
		this(context, new HowToGetByFrames());
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle, HowToGetByFrames)
	 */
	protected Screen(MobileScreen context, HowToGetByFrames path) {
		super(context, path);
		touchActionsPerformer = getComponent(TouchActionsPerformer.class);
		keyEventSender = getComponent(KeyEventSender.class);
		tap = getComponent(Tap.class);
		swipe = getComponent(Swipe.class);
		pinch = getComponent(Pinch.class);
		zoomer = getComponent(Zoomer.class);
		scroller = getComponent(ScrollerTo.class);
	}

	/**
	 * @see org.openqa.selenium.Rotatable#getOrientation()
	 */
	@Override
	public ScreenOrientation getOrientation() {
		return ((MobileScreen) handle).getOrientation();
	}

	/**
	 * @see org.openqa.selenium.Rotatable#rotate(org.openqa.selenium.ScreenOrientation)
	 */
	@Override
	public void rotate(ScreenOrientation orientation) {
		((MobileScreen) handle).rotate(orientation);
	}
}
