package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.aggregated_page_objects_with_annotated_fields;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage; 
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.support.annotations.Frame;
import com.github.arachnidium.tutorial.app_modeling.web.
       unannotated_pageobjects.ShareSettingsDialog;  /**<== */

/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class AnyDocument extends BrowserPage  {/**<-- it is the example which demonstrates component which can be used 
   when only interaction with browser is needed*/

	@Static /**<== This annotation means that it is content which always/very frequently present
	and frequently used*/
	/**Presence of @Static annotation activates handlers of annotations below: */
	@Frame(howToGet = How.CLASS_NAME, locator = "share-client-content-iframe") /** <==
	If field is annotated by @Frame it means that the desired content is located inside 
	iframe.  All @Frame declarations describe the path to the desired iframe.*/ 
	//@Frame(stringPath = "some|path")
	//@Frame(frameIndex = 1)
	public ShareSettingsDialog shareSettingsDialog; /**It is not necessary to 
	make fields public. You can make them private and implement public getter 
	for each one. I made that just for visibility*/
	
	@FindBy(xpath=".//*[@id='docs-titlebar-share-client-button']/div")
	private WebElement shareButton;
	
	@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]")
	private WebElement visibleProfile;
	
	/**
	 * If it is implemented as something general
	 * (general page/screen description) then it 
	 * should have (one of) constructors like these:
	 * 
     * {@link FunctionalPart##FunctionalPart(Handle)}
	 * {@link FunctionalPart##FunctionalPart(Handle, org.openqa.selenium.By) }
	 * {@link FunctionalPart##FunctionalPart(Handle, com.github.arachnidium.model.support.HowToGetByFrames)}
	 * {@link FunctionalPart##FunctionalPart(Handle, com.github.arachnidium.model.support.HowToGetByFrames, org.openqa.selenium.By)}
	 */	
	protected AnyDocument(BrowserWindow window) {/**<-- it is the example which demonstrates component which can be used 
		   when only interaction with browser is needed*/
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
