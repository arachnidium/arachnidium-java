package com.github.arachnidium.tutorial.simple.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

/**
 * Object oriented programming is appreciated. So we can to define
 * default behavior and extend it further.
 * 
 * For example. Each opened section (Video, Music, Friend Line and so on)
 * has a search edit field. So this ability we declare once and extend it
 * in subclasses.
 */
public abstract class HasSearchField<S extends Handle> extends FunctionalPart<S> {

	@FindBy(xpath=".//input[contains(@id,'search')]")
	private WebElement search;
	
	/**
	 * This constructor should present 
	 * when an instance of the class is going to
	 * be got from another.<br/>
	 * <br/>
	 * This instantiation means that described specific UI or the fragment is 
	 * on the same window and inside the same frame  as the more generalized "parent".
	 * <br/><br/>
	 * 
	 * As for these example. When user is logged in he/she able no choose sections e.g Music,
	 * Videos, Friend Line. We can describe all possible interactions by one {@link FunctionalPart}
	 * subclass. I don't like god objects. So we can decompose these interactions (logically, for example)
	 * 
	 * TODO
	 * 
	 *@example
	 *If this constructor is present when instance can got by these methods
	 *<br/><br/>
	 *someUIDescriptionInstance.getPart(someUIDescription.class)<br/>
	 *<br/>
	 *<b>someUIDescription.class should have this constructor</b><br/><br/>
	 *Special things will be described in another
	 * chapters.
	 * 
	 *@param parent is considered as a more general UI or the part of client UI
	 *
	 *@see 
	 *{@link Videos}
	 *{@link UserPage}
	 */
	protected HasSearchField(FunctionalPart<?> parent) {
		super(parent);
	}
	
	@InteractiveMethod
	public void enterSearchString(String searchString){
		search.sendKeys(searchString);
	}

}
