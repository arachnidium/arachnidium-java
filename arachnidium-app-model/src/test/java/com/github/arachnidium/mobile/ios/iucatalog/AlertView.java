package com.github.arachnidium.mobile.ios.iucatalog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.NativeContent;
import com.github.arachnidium.model.support.HowToGetByFrames;


public class AlertView extends NativeContent {
	@FindBy(name = "Simple")
	private WebElement simpleAlert;
	
	protected AlertView(MobileScreen context, HowToGetByFrames path, By by) {
		super(context, path, by);
	}
	
	@InteractiveMethod
	public void invokeSimpleAlert(){
		simpleAlert.click();
	}


}
