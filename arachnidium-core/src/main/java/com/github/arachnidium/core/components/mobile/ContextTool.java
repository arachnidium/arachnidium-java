package com.github.arachnidium.core.components.mobile;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * {@link ContextAware} implementor
 */
public abstract class ContextTool extends WebdriverComponent
		implements ContextAware {
	public ContextTool(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
