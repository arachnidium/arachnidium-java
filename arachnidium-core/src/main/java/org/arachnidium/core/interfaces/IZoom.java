package org.arachnidium.core.interfaces;

import org.openqa.selenium.WebElement;

public interface IZoom {
	/**
	 * Convenience method for "zooming in" on an element on the screen.
	 * "zooming in" refers to the action of two appendages pressing the screen
	 * and sliding away from each other
	 */
	public void zoom(WebElement el);

	/**
	 * Convenience method for "zooming in" on an element on the screen.
	 * "zooming in" refers to the action of two appendages pressing the screen
	 * and sliding away from each other.
	 */
	public void zoom(int x, int y);
}
