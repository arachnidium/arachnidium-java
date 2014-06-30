package org.arachnidium.core.components.overriden;

import java.util.List;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.ImeHandler;

public class Ime extends WebdriverComponent implements ImeHandler {

	public Ime(WebDriver driver) {
		super(driver);
	}

	@Override
	public List<String> getAvailableEngines() {
		return driver.manage().ime().getAvailableEngines();
	}

	@Override
	public String getActiveEngine() {
		return driver.manage().ime().getActiveEngine();
	}

	@Override
	public boolean isActivated() {
		return driver.manage().ime().isActivated();
	}

	@Override
	public void deactivate() {
		driver.manage().ime().deactivate();		
	}

	@Override
	public void activateEngine(String engine) {
		driver.manage().ime().activateEngine(engine);		
	}
}
