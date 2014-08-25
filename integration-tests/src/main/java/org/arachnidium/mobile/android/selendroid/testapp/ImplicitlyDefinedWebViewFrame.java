package org.arachnidium.mobile.android.selendroid.testapp;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.mobile.Screen;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.annotations.classdeclaration.Frame;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

@IfMobileDefaultContextIndex(index = 1)
@IfMobileContext(regExp = "WEBVIEW")
@IfMobileAndroidActivity(regExp = "WebViewActivity")
@Frame(howToGet = How.ID, locator = "iframe1")
public class ImplicitlyDefinedWebViewFrame extends Screen {
	
	@FindBy(xpath = ".//*[@href='/foo']")
	private WebElement fooLink;

	public ImplicitlyDefinedWebViewFrame(MobileScreen screen, HowToGetByFrames howTo) {
		super(screen, howTo);
		load();
	}
	
	@InteractiveMethod
	public void clickOnFoo(){
		fooLink.click();
	}

}
