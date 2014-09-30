package com.github.arachnidium.core.components.mobile;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.WebDriver;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * {@link Rotatable} implementor
 */
public abstract class Rotator extends WebdriverComponent implements
Rotatable {
	public Rotator(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
