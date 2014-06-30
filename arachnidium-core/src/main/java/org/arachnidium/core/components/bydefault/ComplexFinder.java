package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.IComplexFind;
import org.openqa.selenium.WebDriver;

public abstract class ComplexFinder extends WebdriverInterfaceImplementor
		implements IComplexFind {
	public ComplexFinder(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
