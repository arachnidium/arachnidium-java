package org.arachnidium.model.mobile.ios;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.core.components.mobile.KeyboardHider;
import org.arachnidium.core.components.mobile.NamedTextFieldGetter;
import org.arachnidium.core.components.mobile.Shaker;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Screen;
import org.arachnidium.model.support.HowToGetByFrames;

/**
 * The same as {@link Screen} with some capabilities specifically for iOS
 */
public abstract class iOSScreen extends Screen {
	protected final KeyboardHider keyboardHider;
	protected final NamedTextFieldGetter namedTextFieldGetter;
	protected final Shaker shaker;

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart)
	 * 
	 * @see Screen#Screen(FunctionalPart)
	 */
	protected iOSScreen(FunctionalPart<MobileScreen> parent) {
		this(parent, new HowToGetByFrames());
	}

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)
	 * 
	 * @see Screen#Screen(FunctionalPart, HowToGetByFrames)
	 */	
	protected iOSScreen(FunctionalPart<MobileScreen> parent, HowToGetByFrames path) {
		super(parent, path);
		keyboardHider = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker = getComponent(Shaker.class);
	}

	/**
	 * @see FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)
	 * 
	 * @see Screen#Screen(FunctionalPart, HowToGetByFrames)
	 */	
	protected iOSScreen(MobileScreen context) {
		this(context, new HowToGetByFrames());
	}

	/**
	 * @see FunctionalPart#FunctionalPart(org.arachnidium.core.Handle, HowToGetByFrames))
	 * 
	 * @see Screen#Screen(MobileScreen, HowToGetByFrames)
	 */		
	protected iOSScreen(MobileScreen context, HowToGetByFrames path) {
		super(context, path);
		keyboardHider = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker = getComponent(Shaker.class);
	}
}
