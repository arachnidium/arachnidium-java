package org.arachnidium.core.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;

public abstract class Interaction extends WebdriverInterfaceImplementor
		implements HasInputDevices, HasTouchScreen {

	public Interaction(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
