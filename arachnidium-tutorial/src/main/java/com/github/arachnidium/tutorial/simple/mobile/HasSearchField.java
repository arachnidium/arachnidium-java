package com.github.arachnidium.tutorial.simple.mobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public abstract class HasSearchField<S extends Handle> extends FunctionalPart<S> {

	@FindBy(id = "android:id/search_button")
	private WebElement searchButton;
	@FindBy(id = "android:id/search_src_text")
	private WebElement searchText;
	
	protected HasSearchField(FunctionalPart<?> parent) {
		super(parent);
	}
	
	@InteractiveMethod
	public void clickSearchButton(){
		searchButton.click();
	}
	
	@InteractiveMethod
	public void enterSearchString(String searchString){
		searchText.sendKeys(searchString);
	}

}
