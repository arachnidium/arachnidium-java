package com.github.arachnidium.core.interfaces;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver.Window;

/**
 * For all entities that can be identified by string handle
 * e.g. browser window ({@link Window}) or something that
 * contains context name (see {@link ContextAware})
 */
public interface IHasHandle {
	public String getHandle();
	default void whenIsCreated() {
		//does nothing, for listeners only
	}
}
