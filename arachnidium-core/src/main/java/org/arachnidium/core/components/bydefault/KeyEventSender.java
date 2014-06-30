package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.ISendsKeyEvent;
import org.openqa.selenium.WebDriver;

public abstract class KeyEventSender extends WebdriverInterfaceImplementor implements
		ISendsKeyEvent {
	public KeyEventSender(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
