package com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.unannotated_pageobjects;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.ios.iOSNativeContent;
import com.github.arachnidium.model.support.HowToGetByFrames;

public class UICatalog extends iOSNativeContent {
	@FindBy(name = "UICatalog")
	private MobileElement backToMe;
	
	public UICatalog(MobileScreen context, HowToGetByFrames path, By by) {
		super(context, path, by);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void selectItem(String item){
		TouchAction touchAction = new TouchAction((MobileDriver) getWrappedDriver());
		scrollTo(item);
		touchAction.tap(getNamedTextField(item));
		touchActions.performTouchAction(touchAction);
	}
	
	@InteractiveMethod
	public void backToMe(){
		backToMe.click();
	}
	
	/**Some more interesting things could be implemented below*/
	/**.................................*/		
}
