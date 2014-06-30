package org.arachnidium.core.interfaces;

import org.openqa.selenium.WebDriver.Window;

/**
 * @author s.tihomirov
 *
 */
public interface IExtendedWindow extends Window, ITakesPictureOfItSelf, IHasHandle{
	public String getCurrentUrl();	
	public String getTitle();
}
