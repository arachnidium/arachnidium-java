package com.github.arachnidium.tutorial.simple.mobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

//
 // Object oriented programming is appreciated. So we can to define
 // default behavior and extend it further.
 // 
 // For example. Each opened section (Video, Music, Friend Line and so on)
 // has an ability to perform search. So this ability we declare once and extend it
 // in subclasses.
 //
public abstract class HasSearchField<S extends Handle> extends FunctionalPart<S> {

	protected HasSearchField(S handle) {
		super(handle);
	}

	@FindBy(id = "android:id/search_button")
	private WebElement searchButton;
	@FindBy(id = "android:id/search_src_text")
	private WebElement searchText;
	

	
	@InteractiveMethod
	public void clickSearchButton(){
		searchButton.click();
	}
	
	@InteractiveMethod
	public void enterSearchString(String searchString){
		searchText.sendKeys(searchString);
	}

}
