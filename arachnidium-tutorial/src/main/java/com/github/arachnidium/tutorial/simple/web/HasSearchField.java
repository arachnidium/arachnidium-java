package com.github.arachnidium.tutorial.simple.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public abstract class HasSearchField<S extends Handle> extends FunctionalPart<S> {

	@FindBy(xpath=".//input[contains(@id,'search')]")
	private WebElement search;
	
	protected HasSearchField(FunctionalPart<?> parent) {
		super(parent);
	}
	
	@InteractiveMethod
	public void enterSearchString(String searchString){
		search.sendKeys(searchString);
	}

}
