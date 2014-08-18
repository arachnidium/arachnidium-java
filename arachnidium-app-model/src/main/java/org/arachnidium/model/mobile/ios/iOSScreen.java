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

	protected iOSScreen(FunctionalPart<MobileScreen> parent) {
		this(parent, new HowToGetByFrames());
	}

	protected iOSScreen(FunctionalPart<MobileScreen> parent, HowToGetByFrames path) {
		super(parent, path);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected iOSScreen(MobileScreen context) {
		this(context, new HowToGetByFrames());
	}

	protected iOSScreen(MobileScreen context, HowToGetByFrames path) {
		super(context, path);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}
}
