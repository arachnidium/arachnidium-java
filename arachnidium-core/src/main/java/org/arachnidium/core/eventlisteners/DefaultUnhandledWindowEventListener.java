package org.arachnidium.core.eventlisteners;

import org.arachnidium.util.logging.Log;
import org.arachnidium.util.logging.Photographer;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

/**
 * @author s.tihomirov Implementation of @link{IUnhandledWindowEventListener} by
 *         default
 */
public class DefaultUnhandledWindowEventListener implements
		IUnhandledWindowEventListener {

	@Override
	public void whenUnhandledWindowIsFound(WebDriver webdriver) {
		Photographer.takeAPictureOfAWarning(webdriver,  "There is an unhandled window!");
	}

	@Override
	public void whenUnhandledAlertIsFound(Alert alert) {
		String alertText = alert.getText();
		String msg = "Unhandled alert has been caught out!";
		if (alertText != null) {
			msg = msg + " Text is: " + alertText;
		}
		Log.warning(msg);
	}

	@Override
	public void whenUnhandledWindowIsNotClosed(WebDriver webdriver) {
		Log.warning("Unhandled window hasn't been closed!");	
	}

	@Override
	public void whenUnhandledWindowIsAlreadyClosed(WebDriver weddriver) {
		Log.debug("Window is already closed. Window handle is ");
	}

	@Override
	public void whenNoAlertThere(WebDriver weddriver) {
		Log.debug("There is not any alert! It disappeared...");		
	}

}
