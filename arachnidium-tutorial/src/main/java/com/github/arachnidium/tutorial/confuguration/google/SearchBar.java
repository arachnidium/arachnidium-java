package com.github.arachnidium.tutorial.confuguration.google;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.core.HowToGetByFrames;

class SearchBar<T extends Handle> extends FunctionalPart<T> implements IPerformsSearch{
	@FindBy(name = "q")
	private WebElement searchInput;
	@FindBy(name="btnG")
	private WebElement searchButton;
	
	protected SearchBar(T handle, HowToGetByFrames path, By by) {
		super(handle, path, by);
	}

	@InteractiveMethod
	public void performSearch(String searchString) {
		searchInput.sendKeys(searchString);
		searchButton.click();
	}
}
