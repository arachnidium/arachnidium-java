package org.arachnidium.core.interfaces;

import org.openqa.selenium.WebDriver.Window;

/**
 * This interface extends {@link Window}
 */
public interface IExtendedWindow extends Window, ITakesPictureOfItSelf, ISwitchesToItself,
		IHasHandle {
	/**
	 * @return URL of a page which is loaded in the
	 * given browser window. It returns string value.
	 */
	public String getCurrentUrl();

	/**
	 * @return Title of the given browser window.
	 */
	public String getTitle();
	
	/**
	 * Attempts to close the given browser
	 */
	public void close();
}
