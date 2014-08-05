package org.arachnidium.model.mobile.ios;

import org.arachnidium.core.SingleContext;
import org.arachnidium.core.components.mobile.KeyboardHider;
import org.arachnidium.core.components.mobile.NamedTextFieldGetter;
import org.arachnidium.core.components.mobile.Shaker;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Context;
import org.openqa.selenium.WebElement;

/**
 * The same as {@link Context} with some capabilities specifically for iOS
 */
public class IOSContext extends Context {
	protected final KeyboardHider keyboardHider;
	protected final NamedTextFieldGetter namedTextFieldGetter;
	protected final Shaker shaker;

	protected IOSContext(FunctionalPart parent) {
		super(parent);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected IOSContext(FunctionalPart parent, Integer frameIndex) {
		super(parent, frameIndex);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected IOSContext(FunctionalPart parent, String pathToFrame) {
		super(parent, pathToFrame);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected IOSContext(FunctionalPart parent, WebElement frameElement) {
		super(parent, frameElement);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected IOSContext(SingleContext context) {
		super(context);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected IOSContext(SingleContext context, Integer frameIndex) {
		super(context, frameIndex);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected IOSContext(SingleContext context, String pathToFrame) {
		super(context, pathToFrame);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

	protected IOSContext(SingleContext context, WebElement frameElement) {
		super(context, frameElement);
		keyboardHider = driverEncapsulation.getComponent(KeyboardHider.class);
		namedTextFieldGetter = driverEncapsulation.getComponent(NamedTextFieldGetter.class);
		shaker = driverEncapsulation.getComponent(Shaker.class);
	}

}
