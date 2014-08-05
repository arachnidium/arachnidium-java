package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;

public abstract class ContextTool extends WebdriverComponent
		implements ContextAware {
	public ContextTool(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
