package org.arachnidium.model.mobile.android;

import org.arachnidium.core.SingleContext;
import org.arachnidium.core.components.mobile.AppStringGetter;
import org.arachnidium.core.components.mobile.MetastateKeyEventSender;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Context;
import org.arachnidium.model.support.FramePathStrategy;
/**
 * The same as {@link Context} with some capabilities specifically for Android
 */
public abstract class AndroidContext extends Context {

	protected final MetastateKeyEventSender metastateKeyEventSender;
	protected final AppStringGetter appStringGetter;

	protected AndroidContext(FunctionalPart parent) {
		this(parent, new FramePathStrategy());
	}

	protected AndroidContext(FunctionalPart parent, FramePathStrategy pathStrategy) {
		super(parent, pathStrategy);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidContext(SingleContext context) {
		this(context, new FramePathStrategy());
	}

	protected AndroidContext(SingleContext context, FramePathStrategy pathStrategy) {
		super(context, pathStrategy);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

}
