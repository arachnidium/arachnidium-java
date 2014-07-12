package org.arachnidium.core.interfaces;

import org.openqa.selenium.WebElement;

public interface ITap {
	/**
	 * Convenience method for tapping a position on the screen
	 */
	public void tap(int fingers, int x, int y, int duration);

	/**
	 * Convenience method for tapping the center of an element on the screen
	 */
	public void tap(int fingers, WebElement element, int duration);
}
