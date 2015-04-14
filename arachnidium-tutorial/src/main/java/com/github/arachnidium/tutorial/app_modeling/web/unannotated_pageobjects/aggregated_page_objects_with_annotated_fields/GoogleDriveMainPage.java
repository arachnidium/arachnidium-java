package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.aggregated_page_objects_with_annotated_fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.core.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.rootelements.RootElement;
import com.github.arachnidium.tutorial.app_modeling.web.
       unannotated_pageobjects.AccountOptions;  /**<== */
import com.github.arachnidium.tutorial.app_modeling.web.
       unannotated_pageobjects.FilterListAndButton;  /**<== */
import com.github.arachnidium.tutorial.app_modeling.web.
       unannotated_pageobjects.GoogleSearchBar;  /**<== */
import com.github.arachnidium.tutorial.app_modeling.web.
       unannotated_pageobjects.ItemList;  /**<== */

/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class GoogleDriveMainPage extends BrowserPage {/**<-- it is the example which demonstrates component which can be used 
	   when only interaction with browser is needed*/

	@Static /**<== This annotation means that it is content which always/very frequently present
	and frequently used*/	
	/**Presence of @Static annotation activates handlers of annotations below: */	
	@RootElement(chain = {@FindBy(id = "someIdForAnotherService_or_Component")}) /**<--It is the demonstration of the ability
	to define the default root element for the whole page object. All declared elements will be found from this element 
	instead of WebDriver. We can define it as a chain of searches*/
	@RootElement(chain = {@FindBy(id = "viewpane")})   /**We can define it as a set of possible element
	chains.*/
	//@Frame(howToGet = How.CLASS_NAME, locator = "share-client-content-iframe") 
	/**	If field is annotated by @Frame it means that the desired content is located inside 
	iframe.  All @Frame declarations describe the path to the desired iframe.*/ 
	public ItemList itemList; /**It is not necessary to 
	make fields public. You can make them private and implement public getter 
	for each one. I made that just for visibility*/
	
	@Static
	@RootElement(chain = {@FindBy(id = "someIdForAnotherService_or_Component")}) 
	@RootElement(chain = {@FindBy(id = "navpane")})
	
	/**These annotations are ignored here:*/
	@ExpectedURL(regExp = "someservice/someurl")
	@ExpectedURL(regExp = "https://drive.google.com")
	@DefaultPageIndex(index = 0) /**<-Also it is possible to define the default expected window/tab index*/
	@ExpectedPageTitle(regExp = "Google") /**and page title*/
	public FilterListAndButton filterListAndButton;
	
	@Static
	@RootElement(chain = {@FindBy(id = "someIdForAnotherService_or_Component")})
	@RootElement(chain = {@FindBy(xpath = ".//*[@class='gb_ka gb_F']")}) 
	public AccountOptions<?> accountOptions;
	
	@Static
	@RootElement(chain = {@FindBy(id = "someIdForAnotherService_or_Component")})
	@RootElement(chain = {@FindBy(id = "gbqfw")}) 
	public GoogleSearchBar googleSearchBar;
	
	@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]")
	private WebElement visibleProfile;
	
	protected GoogleDriveMainPage(BrowserWindow window, HowToGetByFrames path, By by) {/**<-- it is the example which demonstrates component which can be used 
		   when only interaction with browser is needed*/
		super(window, path, by);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void clickOnVisibleProfile(){
		visibleProfile.click();
	}
	
	//Common logic of the working with the Drive Google main page could be implemented below 
	//.........

}
