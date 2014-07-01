package org.arachnidium.mobile.ios.iucatalog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.core.Handle;

public class ActionSheets extends FunctionalPart {
	@FindBy(name = "Okay / Cancel")
	private WebElement ok_cancel;
	@FindBy(name = "Other")
	private WebElement other;
	
	public ActionSheets(Handle handle) {
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
		driverEncapsulation.getWrappedDriver().findElement(By.name(name)).click();
	}

}
