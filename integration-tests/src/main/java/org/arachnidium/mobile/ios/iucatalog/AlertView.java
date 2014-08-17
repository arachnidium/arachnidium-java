package org.arachnidium.mobile.ios.iucatalog;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.mobile.Screen;
import org.arachnidium.core.MobileScreen;


public class AlertView extends Screen {
	@FindBy(name = "Simple")
	private WebElement simpleAlert;
	
	protected AlertView(MobileScreen context) {
		super(context);
		load();
	}
	
	@InteractiveMethod
	public void invokeSimpleAlert(){
		simpleAlert.click();
	}


}
