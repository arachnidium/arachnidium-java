package com.github.arachnidium.mobile.ios.iucatalog;

import java.util.List;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class ActionSheets<T extends Handle> extends FunctionalPart<T> {
	@FindBy(name = "Okay / Cancel")
	private WebElement ok_cancel;
	@FindBy(name = "Other")
	private IOSElement other;
	@iOSFindBy(xpath = "//UIACollectionCell[1]/UIAButton")
	private List<IOSElement> buttons;
	
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
		for (IOSElement button: buttons){
			if (button.getText().equals(name)){
				button.click();
				return;
			}
		}
		throw new NoSuchElementException("There is no button named " + name);
	}

}
