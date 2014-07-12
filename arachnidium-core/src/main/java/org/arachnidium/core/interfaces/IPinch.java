package org.arachnidium.core.interfaces;

import org.openqa.selenium.WebElement;

public interface IPinch {
	/**
	 * Convenience method for pinching an element on the screen. "pinching"
	 * refers to the action of two appendages pressing the screen and sliding
	 * towards each other.
	 */
	public void pinch(int x, int y);

	/**
	 * Convenience method for pinching an element on the screen. "pinching"
	 * refers to the action of two appendages pressing the screen and sliding
	 * towards each other.
	 */
	public void pinch(WebElement el);
}
