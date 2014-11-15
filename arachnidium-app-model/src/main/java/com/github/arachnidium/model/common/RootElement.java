package com.github.arachnidium.model.common;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 * This object performs the searching of the desired
 * element which becomes the root element in the chain
 * of further searches.
 */
class RootElement implements WrapsElement {
	static final String ROOT_ELEMENT_FIELD_NAME = "rootElement";
	
    WebElement rootElement;
	
    /**
     * The wrapped root element will be found once
     */
	RootElement(SearchContext searchContext, By by) {
		rootElement = searchContext.findElement(by);
	}
	
	RootElement() {
		super();
	}
	
	/**
     * The wrapped root element is found each time
     * when something makes request to it.
     * the single {@link WebElement} is overridden here 
     */
	void decorateWebElement(FieldDecorator fieldDecorator){
		PageFactory.initElements(fieldDecorator, this);
	}

	@Override
	public WebElement getWrappedElement() {
		return rootElement;
	}

}
