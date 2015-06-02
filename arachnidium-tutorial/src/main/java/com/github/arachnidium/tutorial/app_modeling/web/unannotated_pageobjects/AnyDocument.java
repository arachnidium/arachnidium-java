package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage;

/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class AnyDocument extends BrowserPage  {/**<-- it is the example which demonstrates component which can be used 
   when only interaction with browser is needed*/

	@FindBy(xpath=".//*[@id='docs-titlebar-share-client-button']/div")
	private WebElement shareButton;
	
	@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]")
	private WebElement visibleProfile;
		
	protected AnyDocument(BrowserWindow window) {
		super(window);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	
	@WithImplicitlyWait(timeOut = 25, /**The presence of @InteractiveMethod activates some useful */
	timeUnit = TimeUnit.SECONDS) /**options like this*/
	public void clickShareButton(){
		shareButton.click();
	}
	
	@InteractiveMethod
	public void clickOnVisibleProfile(){
		visibleProfile.click();
	}
	
	//Common logic of the working with any document in general could be implemented below
	//.........
}
