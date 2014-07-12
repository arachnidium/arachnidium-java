package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.IShakes;
import org.openqa.selenium.WebDriver;

public abstract class Shaker extends WebdriverInterfaceImplementor implements
IShakes {
	public Shaker(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
