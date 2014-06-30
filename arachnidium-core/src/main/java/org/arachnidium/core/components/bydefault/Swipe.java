package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.ISwipe;
import org.openqa.selenium.WebDriver;

public abstract class Swipe extends WebdriverInterfaceImplementor implements
		ISwipe {
	public Swipe(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
