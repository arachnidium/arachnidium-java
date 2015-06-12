package com.github.arachnidium.tutorial.simple.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

//
 // Object oriented programming is appreciated. So we can to define
 // default behavior and extend it further.
 // 
 // For example. Each opened section (Video, Music, Friend Line and so on)
 // has a search edit field. So this ability we declare once and extend it
 // in subclasses.
public abstract class HasSearchField<S extends Handle> extends FunctionalPart<S> {

	protected HasSearchField(S handle) {
		super(handle);
	}

	@FindBy(xpath=".//input[contains(@id,'search')]")
	private WebElement search;
	
	@InteractiveMethod
	public void enterSearchString(String searchString){
		search.sendKeys(searchString);
	}

}
