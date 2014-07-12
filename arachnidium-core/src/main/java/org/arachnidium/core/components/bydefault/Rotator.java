package org.arachnidium.core.components.bydefault;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.WebDriver;

public abstract class Rotator extends WebdriverInterfaceImplementor implements
Rotatable {
	public Rotator(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
