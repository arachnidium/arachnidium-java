package com.github.arachnidium.core.components.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;

import com.github.arachnidium.core.components.WebdriverComponent;

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
