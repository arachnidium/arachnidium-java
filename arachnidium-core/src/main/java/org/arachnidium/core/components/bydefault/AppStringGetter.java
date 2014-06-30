package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.IGetsAppStrings;
import org.openqa.selenium.WebDriver;

public abstract class AppStringGetter extends WebdriverInterfaceImplementor implements
		IGetsAppStrings {

	public AppStringGetter(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
