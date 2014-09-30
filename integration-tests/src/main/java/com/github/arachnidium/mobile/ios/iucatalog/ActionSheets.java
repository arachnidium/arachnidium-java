package com.github.arachnidium.mobile.ios.iucatalog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class ActionSheets<T extends Handle> extends FunctionalPart<T> {
	@FindBy(name = "Okay / Cancel")
	private WebElement ok_cancel;
	@FindBy(name = "Other")
	private WebElement other;
	
	public ActionSheets(T handle) {
		super(handle);
		load();
	}
	
	@InteractiveMethod
	public void clickOnOk_Cancel(){
		ok_cancel.click();
	}
	
	@InteractiveMethod
	public void clickOnOther(){
		other.click();
	}
	
	@InteractiveMethod
	public void clickOnSplashButton(String name){
		getWrappedDriver().findElement(By.name(name)).click();
	}

}
