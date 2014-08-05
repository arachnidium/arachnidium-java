package org.arachnidium.model.mobile.android;

import org.arachnidium.core.SingleContext;
import org.arachnidium.core.components.mobile.AppStringGetter;
import org.arachnidium.core.components.mobile.MetastateKeyEventSender;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Context;
import org.openqa.selenium.WebElement;

/**
 * The same as {@link Context} with some capabilities specifically for Android
 */
public abstract class AndroidContext extends Context {

	protected final MetastateKeyEventSender metastateKeyEventSender;
	protected final AppStringGetter appStringGetter;

	protected AndroidContext(FunctionalPart parent) {
		super(parent);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidContext(FunctionalPart parent, Integer frameIndex) {
		super(parent, frameIndex);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidContext(FunctionalPart parent, String pathToFrame) {
		super(parent, pathToFrame);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidContext(FunctionalPart parent, WebElement frameElement) {
		super(parent, frameElement);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidContext(SingleContext context) {
		super(context);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidContext(SingleContext context, Integer frameIndex) {
		super(context, frameIndex);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidContext(SingleContext context, String pathToFrame) {
		super(context, pathToFrame);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidContext(SingleContext context, WebElement frameElement) {
		super(context, frameElement);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

}
