/**
 * 
 */
package org.arachnidium.core;

import org.openqa.selenium.WebDriverException;

/**
 * @author s.tihomirov
 *
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
