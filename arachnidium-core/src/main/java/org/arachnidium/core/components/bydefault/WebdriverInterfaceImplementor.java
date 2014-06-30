package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;

public abstract class WebdriverInterfaceImplementor extends WebdriverComponent {
	Object delegate;
	
	public WebdriverInterfaceImplementor(WebDriver driver) {
		super(driver);
	}

}
