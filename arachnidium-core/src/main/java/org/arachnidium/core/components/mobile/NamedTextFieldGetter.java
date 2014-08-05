package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class NamedTextFieldGetter extends WebdriverComponent {
	public NamedTextFieldGetter(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	/**
	 * In iOS apps, named TextFields have the same accessibility Id as their
	 * containing TableElement. This is a convenience method for getting the
	 * named TextField, rather than its containing element.
	 */
	public abstract WebElement getNamedTextField(String name);	
}
