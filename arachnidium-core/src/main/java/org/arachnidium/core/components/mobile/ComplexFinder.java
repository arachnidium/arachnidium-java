package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ComplexFinder extends WebdriverComponent{
	public ComplexFinder(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	public abstract WebElement complexFind(String complex);
	
}
