package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.IGetsNamedTextField;
import org.openqa.selenium.WebDriver;

public abstract class NamedTextFieldGetter extends
WebdriverInterfaceImplementor implements IGetsNamedTextField {
	public NamedTextFieldGetter(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
