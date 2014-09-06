package org.arachnidium.core.components.common;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;

/**
 * Implements {@link HasInputDevices} and {@link HasTouchScreen }
 */
public abstract class Interaction extends WebdriverComponent
		implements HasInputDevices, HasTouchScreen {

	public Interaction(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
