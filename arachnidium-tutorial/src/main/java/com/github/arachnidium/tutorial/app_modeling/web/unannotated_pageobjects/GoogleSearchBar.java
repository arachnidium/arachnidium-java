package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.core.HowToGetByFrames;

/**it is the example which demonstrates how to implement a child page object*/
public class GoogleSearchBar extends FunctionalPart<Handle> {/** <==
	 * Here I demonstrate something that is supposed to be used by the web and 
	 * mobile testing 
	 */
	@FindBy(name = "q")
	private WebElement searchInput;
	@FindAll({
		@FindBy(id="gbqfb"),
		@FindBy(name="btnG")
		})
	private WebElement searchButton;
	
	/**
	 * If you want to represent some page object as a 
	 * "child" component of any page/screen then your implementation 
	 * should have constructor like this:
	 * 
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart, com.github.arachnidium.model.support.HowToGetByFrames, By)}
	 * 
	 * As you can see the class should have (one of) constructors which instantiate it
	 *  class as a child of more generalized parent
	 */
	protected GoogleSearchBar(FunctionalPart<?> parent, HowToGetByFrames path, By by) {
		super(parent, path, by);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void performSearch(String searchString) {
		searchInput.clear();
		searchInput.sendKeys(searchString);
		searchButton.click();
	}

}
