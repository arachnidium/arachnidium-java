package org.arachnidium.model.mobile.android;

import org.openqa.selenium.WebElement;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Context;
import org.arachnidium.core.SingleContext;
import org.arachnidium.core.components.bydefault.AppStringGetter;
import org.arachnidium.core.components.bydefault.MetastateKeyEventSender;

/**
 * The same as {@link Context} with some capabilities specifically for Android
 */
public abstract class AndroidContext extends Context {

	protected final MetastateKeyEventSender metastateKeyEventSender;
	protected final AppStringGetter appStringGetter;
	
	protected AndroidContext(SingleContext context) {
		super(context);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);
	}

	protected AndroidContext(FunctionalPart parent) {
		super(parent);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);
	}

	protected AndroidContext(SingleContext context, Integer frameIndex) {
		super(context, frameIndex);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(FunctionalPart parent, Integer frameIndex) {
		super(parent, frameIndex);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(SingleContext context, String pathToFrame) {
		super(context, pathToFrame);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(FunctionalPart parent, String pathToFrame) {
		super(parent, pathToFrame);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(SingleContext context, WebElement frameElement) {
		super(context, frameElement);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(FunctionalPart parent, WebElement frameElement) {
		super(parent, frameElement);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

}
