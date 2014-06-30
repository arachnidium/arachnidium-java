package org.arachnidium.model.mobile.ios;

import org.openqa.selenium.WebElement;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Context;
import org.arachnidium.core.SingleContext;
import org.arachnidium.core.components.bydefault.KeyboardHider;
import org.arachnidium.core.components.bydefault.NamedTextFieldGetter;
import org.arachnidium.core.components.bydefault.Shaker;

/**
 * The same as {@link Context} with some capabilities specifically for iOS
 */
public class IOSContext extends Context {
	protected final KeyboardHider keyboardHider;
	protected final NamedTextFieldGetter namedTextFieldGetter;
	protected final Shaker shaker;

	protected IOSContext(SingleContext context) {
		super(context);
		keyboardHider        = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker               = getComponent(Shaker.class);
	}

	protected IOSContext(FunctionalPart parent) {
		super(parent);
		keyboardHider        = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker               = getComponent(Shaker.class);
	}

	protected IOSContext(SingleContext context, Integer frameIndex) {
		super(context, frameIndex);
		keyboardHider        = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker               = getComponent(Shaker.class);
	}

	protected IOSContext(FunctionalPart parent, Integer frameIndex) {
		super(parent, frameIndex);
		keyboardHider        = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker               = getComponent(Shaker.class);
	}

	protected IOSContext(SingleContext context, String pathToFrame) {
		super(context, pathToFrame);
		keyboardHider        = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker               = getComponent(Shaker.class);
	}

	protected IOSContext(FunctionalPart parent, String pathToFrame) {
		super(parent, pathToFrame);
		keyboardHider        = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker               = getComponent(Shaker.class);
	}

	protected IOSContext(SingleContext context, WebElement frameElement) {
		super(context, frameElement);
		keyboardHider        = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker               = getComponent(Shaker.class);
	}

	protected IOSContext(FunctionalPart parent, WebElement frameElement) {
		super(parent, frameElement);
		keyboardHider        = getComponent(KeyboardHider.class);
		namedTextFieldGetter = getComponent(NamedTextFieldGetter.class);
		shaker               = getComponent(Shaker.class);
	}

}
