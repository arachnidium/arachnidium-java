package com.github.arachnidium.core.components.common;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.ImeHandler;

import com.github.arachnidium.core.components.WebdriverComponent;

/**
 * 
 * {@link IME} implementor
 *
 */
public abstract class Ime extends WebdriverComponent implements ImeHandler {

	public Ime(WebDriver driver) {
		super(driver);
		delegate = this;
	}

	@Override
	public void activateEngine(String engine) {
		driver.manage().ime().activateEngine(engine);
	}

	@Override
	public void deactivate() {
		driver.manage().ime().deactivate();
	}

	@Override
	public String getActiveEngine() {
		return driver.manage().ime().getActiveEngine();
	}

	@Override
	public List<String> getAvailableEngines() {
		return driver.manage().ime().getAvailableEngines();
	}

	@Override
	public boolean isActivated() {
		return driver.manage().ime().isActivated();
	}
}
