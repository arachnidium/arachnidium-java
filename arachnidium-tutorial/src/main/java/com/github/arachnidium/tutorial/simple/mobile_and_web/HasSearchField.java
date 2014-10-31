package com.github.arachnidium.tutorial.simple.mobile_and_web;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public abstract class HasSearchField<S extends Handle> extends FunctionalPart<S> {

	@FindBy(xpath=".//input[contains(@id,'search')]")
	@AndroidFindBy(id = "android:id/search_src_text")
	private WebElement search;
	
	protected HasSearchField(FunctionalPart<?> parent) {
		super(parent);
	}
	
	@InteractiveMethod
	public void enterSearchString(String searchString){
		if (AndroidDriver.class.isAssignableFrom(getWrappedDriver().getClass())){
			getWrappedDriver().findElement(By.id("android:id/search_button")).click();
		}
		search.sendKeys(searchString);
	}

}
