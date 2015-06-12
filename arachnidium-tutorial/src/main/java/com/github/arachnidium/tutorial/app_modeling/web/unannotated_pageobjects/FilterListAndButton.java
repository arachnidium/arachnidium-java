package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

/**it is the example which demonstrates how to implement a child page object*/
public class FilterListAndButton extends FunctionalPart<Handle> { /** <==
	 * Here I demonstrate something that is supposed to be used by the web and 
	 * mobile testing 
	 */
	
	protected FilterListAndButton(Handle handle) {
		super(handle);
	}

	@FindAll({@FindBy(className = "treedoclistview-root-node-name"),
		@FindBy(xpath = ".//*[contains(@class,'goog-listitem-container')]")})
	private List<WebElement> sections;
	
	@FindBy(xpath = ".//*[contains(@class,'goog-toolbar-item-new')]")
	private WebElement newDocumentButton;
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void choseSection(int sectionNum) {
		sections.get(0).click();
	}
	
	@InteractiveMethod
	public void clickButton(){
		newDocumentButton.click();
	}
	
	//Some more actions could be implemented here
	//.......

}
