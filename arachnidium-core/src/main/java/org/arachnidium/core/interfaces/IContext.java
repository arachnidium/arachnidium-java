package org.arachnidium.core.interfaces;

import org.openqa.selenium.Rotatable;

/**
 * Interface of a mobile context
 */
public interface IContext extends Rotatable, ISwitchesToItself, IHasHandle,
		ITakesPictureOfItSelf {
	boolean isSupportActivities();
	String currentActivity();
}
