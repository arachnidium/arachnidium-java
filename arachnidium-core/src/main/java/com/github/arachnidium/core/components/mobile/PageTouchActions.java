package com.github.arachnidium.core.components.mobile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * {@link TouchScreen} default implementor
 *
 */
public abstract class PageTouchActions extends WebdriverComponent implements
		TouchScreen {
	public PageTouchActions(WebDriver driver) {
		super(driver);
		delegate = new RemoteTouchScreen(new RemoteExecuteMethod(
				(RemoteWebDriver) this.driver));
	}

}
