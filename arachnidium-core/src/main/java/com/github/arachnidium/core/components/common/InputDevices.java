package com.github.arachnidium.core.components.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * Implements {@link HasInputDevices} and {@link HasTouchScreen }
 */
public abstract class InputDevices extends WebdriverComponent
		implements HasInputDevices{

	public InputDevices(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
