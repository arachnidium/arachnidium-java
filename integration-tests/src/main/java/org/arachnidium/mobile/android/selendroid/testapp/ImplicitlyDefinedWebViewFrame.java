package org.arachnidium.mobile.android.selendroid.testapp;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.mobile.WebViewContent;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.annotations.classdeclaration.Frame;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@IfMobileDefaultContextIndex(index = 1)
@IfMobileAndroidActivity(regExp = "WebViewActivity")
@Frame(stringPath = "iframe1-name")
public class ImplicitlyDefinedWebViewFrame extends WebViewContent {
	
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
