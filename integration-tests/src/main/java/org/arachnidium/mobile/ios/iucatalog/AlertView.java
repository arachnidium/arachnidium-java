package org.arachnidium.mobile.ios.iucatalog;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.mobile.Context;
import org.arachnidium.core.MobileContext;


public class AlertView extends Context {
	@FindBy(name = "Simple")
	private WebElement simpleAlert;
	
	protected AlertView(MobileContext context) {
		super(context);
		load();
	}
	
	@InteractiveMethod
	public void invokeSimpleAlert(){
		simpleAlert.click();
	}


}
