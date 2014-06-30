package org.arachnidium.core.interfaces;

import org.openqa.selenium.WebElement;

public interface IGetsNamedTextField {
	 /**
	 * In iOS apps, named TextFields have the same accessibility Id as their containing TableElement.
	 * This is a convenience method for getting the named TextField, rather than its containing element.
	 */
	 public WebElement getNamedTextField(String name);
}
