package com.github.arachnidium.mobile.ios.iucatalog;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.NativeContent;


public class AlertView extends NativeContent {
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
