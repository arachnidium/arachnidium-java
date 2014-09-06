package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.WebDriver;

/**
 * {@link Rotatable} implementor
 */
public abstract class Rotator extends WebdriverComponent implements
Rotatable {
	public Rotator(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
