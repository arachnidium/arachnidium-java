package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.ITap;
import org.openqa.selenium.WebDriver;

public abstract class Tap extends WebdriverInterfaceImplementor implements ITap {
	public Tap(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
