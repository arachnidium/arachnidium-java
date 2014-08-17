package org.arachnidium.model.mobile.android;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.core.components.mobile.AppStringGetter;
import org.arachnidium.core.components.mobile.MetastateKeyEventSender;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.mobile.Screen;
import org.arachnidium.model.support.HowToGetByFrames;
/**
 * The same as {@link Screen} with some capabilities specifically for Android
 */
public abstract class AndroidScreen extends Screen {

	protected final MetastateKeyEventSender metastateKeyEventSender;
	protected final AppStringGetter appStringGetter;

	protected AndroidScreen(FunctionalPart<MobileScreen> parent) {
		this(parent, new HowToGetByFrames());
	}

	protected AndroidScreen(FunctionalPart<MobileScreen> parent, HowToGetByFrames pathStrategy) {
		super(parent, pathStrategy);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

	protected AndroidScreen(MobileScreen context) {
		this(context, new HowToGetByFrames());
	}

	protected AndroidScreen(MobileScreen context, HowToGetByFrames pathStrategy) {
		super(context, pathStrategy);
		metastateKeyEventSender = driverEncapsulation.getComponent(MetastateKeyEventSender.class);
		appStringGetter = driverEncapsulation.getComponent(AppStringGetter.class);
	}

}
