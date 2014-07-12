package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.ISendsMetastateKeyEvent;
import org.openqa.selenium.WebDriver;

public abstract class MetastateKeyEventSender extends
		WebdriverInterfaceImplementor implements ISendsMetastateKeyEvent {
	public MetastateKeyEventSender(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
