package org.arachnidium.core.components.bydefault;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;

public abstract class ContextTool extends WebdriverInterfaceImplementor
		implements ContextAware {
	public ContextTool(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
