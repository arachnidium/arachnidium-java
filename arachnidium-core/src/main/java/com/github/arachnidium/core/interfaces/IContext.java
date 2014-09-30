package com.github.arachnidium.core.interfaces;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * An implementor should describe behavior
 * of mobile screen (context)
 * 
 * Extends {@link IHasHandle}
 */
public interface IContext extends Rotatable, ISwitchesToItself, IHasHandle,
		ITakesPictureOfItSelf, WrapsDriver {
}
