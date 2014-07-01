package org.arachnidium.mobile.ios.iucatalog;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.mobile.Context;
import org.arachnidium.core.SingleContext;


public class AlertView extends Context {
	@FindBy(name = "Simple")
	private WebElement simpleAlert;
	
	protected AlertView(SingleContext context) {
		super(context);
		load();
	}
	
	@InteractiveMethod
	public void invokeSimpleAlert(){
		simpleAlert.click();
	}


}
