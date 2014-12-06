package app_modeling.web.raw_pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage;

public class GoogleDriveMainPage extends BrowserPage {

	@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]")
	private WebElement visibleProfile;
	
	protected GoogleDriveMainPage(BrowserWindow window) {
		super(window);
	}
	
	@InteractiveMethod
	public void clickOnVisibleProfile(){
		visibleProfile.click();
	}
	
	//Common logic of the working with the Drive Google main page could be implemented below 
	//.........

}
