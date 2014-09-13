package org.arachnidium.model.mobile.android;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.core.components.mobile.AppStringGetter;
import org.arachnidium.core.components.mobile.KeyEventSender;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Screen;
import org.arachnidium.model.support.HowToGetByFrames;

/**
 * The same as {@link Screen} with some capabilities specifically for Android
 */
public abstract class AndroidScreen extends Screen {

	protected final KeyEventSender keyEventSender;
	protected final AppStringGetter appStringGetter;

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 * 
	 * @see Screen#Screen(FunctionalPart)
	 */
	protected AndroidScreen(FunctionalPart<MobileScreen> parent) {
		this(parent, new HowToGetByFrames());
	}

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)
	 * 
	 * @see Screen#Screen(FunctionalPart, HowToGetByFrames)
	 */
	protected AndroidScreen(FunctionalPart<MobileScreen> parent, HowToGetByFrames path) {
		super(parent, path);
		keyEventSender = getComponent(KeyEventSender.class);
		appStringGetter = getComponent(AppStringGetter.class);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle)
	 * 
	 * @see Screen#Screen(MobileScreen)
	 */
	protected AndroidScreen(MobileScreen context) {
		this(context, new HowToGetByFrames());
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle, HowToGetByFrames))
	 * 
	 * @see Screen#Screen(MobileScreen, HowToGetByFrames)
	 */	
	protected AndroidScreen(MobileScreen context, HowToGetByFrames path) {
		super(context, path);
		keyEventSender = getComponent(KeyEventSender.class);
		appStringGetter = getComponent(AppStringGetter.class);
	}
	
	public String currentActivity(){
		return ((MobileScreen) handle).currentActivity();
	}

}
