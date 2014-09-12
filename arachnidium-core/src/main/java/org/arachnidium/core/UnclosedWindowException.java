/**
 *
 */
package org.arachnidium.core;

import org.openqa.selenium.WebDriverException;

/**
 * This exception is thrown when 
 * browser window is not closed 
 */
public class UnclosedWindowException extends WebDriverException {
	/**
	 *
	 */
	private static final long serialVersionUID = -323127995328722233L;

	public UnclosedWindowException(String message, Exception e) {
		super(message, e);
	}
}
