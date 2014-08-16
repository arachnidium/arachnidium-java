package org.arachnidium.model.mobile.ios;

import org.arachnidium.core.MobileContext;
import org.arachnidium.core.components.mobile.KeyboardHider;
import org.arachnidium.core.components.mobile.NamedTextFieldGetter;
import org.arachnidium.core.components.mobile.Shaker;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Context;
import org.arachnidium.model.support.HowToGetByFrames;

/**
 * The same as {@link Context} with some capabilities specifically for iOS
 */
public abstract class IOSContext extends Context {
	protected final KeyboardHider keyboardHider;
	protected final NamedTextFieldGetter namedTextFieldGetter;
	protected final Shaker shaker;

	protected IOSContext(FunctionalPart parent) {
		this(parent, new HowToGetByFrames());
	}

	protected IOSContext(FunctionalPart parent, HowToGetByFrames pathStrategy) {
		super(parent, pathStrategy);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected IOSContext(MobileContext context) {
		this(context, new HowToGetByFrames());
	}

	protected IOSContext(MobileContext context, HowToGetByFrames pathStrategy) {
		super(context, pathStrategy);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}
}
