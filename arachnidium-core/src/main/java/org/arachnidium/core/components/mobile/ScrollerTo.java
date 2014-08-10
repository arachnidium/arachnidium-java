package org.arachnidium.core.components.mobile;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ScrollerTo extends WebdriverComponent{

	public ScrollerTo(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
	
	public abstract WebElement scrollTo(String text);

	public abstract WebElement scrollToExact(String text);	

}