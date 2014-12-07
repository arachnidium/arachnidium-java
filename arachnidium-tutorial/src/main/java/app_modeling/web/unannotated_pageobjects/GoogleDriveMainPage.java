package app_modeling.web.unannotated_pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage;

/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class GoogleDriveMainPage extends BrowserPage {/**<-- it is the example which demonstrates component which can be used 
	   when only interaction with browser is needed*/

	@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]")
	private WebElement visibleProfile;
	
	protected GoogleDriveMainPage(BrowserWindow window) {/**<-- it is the example which demonstrates component which can be used 
		   when only interaction with browser is needed*/
		super(window);
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
