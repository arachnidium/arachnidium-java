package org.arachnidium.core.interfaces;

import org.openqa.selenium.Rotatable;

/**
 * An implementor should describe behavior
 * of mobile screen (context)
 * 
 * Extends {@link IHasHandle}
 */
public interface IContext extends Rotatable, ISwitchesToItself, IHasHandle,
		ITakesPictureOfItSelf {
	boolean isSupportActivities();
	String currentActivity();
}
