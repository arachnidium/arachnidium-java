package com.github.arachnidium.core.components.mobile;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.WebDriver;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * Performs {@link TouchAction} and {@link MultiTouchAction}
 *
 */
public abstract class TouchActionsPerformer extends WebdriverComponent
		implements PerformsTouchActions {
	public TouchActionsPerformer(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
