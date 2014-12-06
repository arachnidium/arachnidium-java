package app_modeling.web.raw_pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage;

public class AnyDocument extends BrowserPage  {

	@FindBy(xpath=".//*[@id='docs-titlebar-share-client-button']/div")
	private WebElement shareButton;
	
	@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]")
	private WebElement visibleProfile;
	
	
	protected AnyDocument(BrowserWindow window) {
		super(window);
	}
	
	@InteractiveMethod
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
