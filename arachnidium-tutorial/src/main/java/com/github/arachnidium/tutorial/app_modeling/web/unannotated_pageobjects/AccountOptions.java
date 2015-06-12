package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

/**it is the example which demonstrates how to implement a child page object*/
public class AccountOptions<S extends Handle> extends FunctionalPart<S> { 
	
	protected AccountOptions(S handle) {
		super(handle);
	}

	/** <==
	 * Here I demonstrate something that is supposed to be used by the web and 
	 * mobile testing 
	 */
	
	@FindAll({@FindBy(xpath = ".//*[@class='gbmpalb']/a"),
		@FindBy(xpath = ".//*[@class='gb_Ba']/div[2]/a")})
	private WebElement quitButton;
	
	@FindAll({@FindBy(xpath = ".//*[@class='gb_Xb gb_V']"),
		@FindBy(xpath = ".//*[@class='gbmpala']/a")})
	private WebElement addAccountButton;
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void quit(){
		quitButton.click();
	}
	
	@InteractiveMethod
	public void addAccount(){
		addAccountButton.click();
	}	
	
	//Some more actions could be implemented here
	//.......

}
