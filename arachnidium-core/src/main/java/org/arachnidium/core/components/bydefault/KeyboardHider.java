package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.IHidesKeyboard;
import org.openqa.selenium.WebDriver;

public abstract class KeyboardHider extends WebdriverInterfaceImplementor
		implements IHidesKeyboard {
	public KeyboardHider(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
