package com.github.arachnidium.mobile.android.selendroid.testapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.WebViewContent;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;

@IfMobileDefaultContextIndex(index = 1)
@IfMobileAndroidActivity(regExp = "WebViewActivity")
@Frame(howToGet = How.ID, locator = "iframe1")
public class ImplicitlyDefinedWebViewFrame extends WebViewContent {
	
	@FindBy(xpath = ".//*[@href='/foo']")
	private WebElement fooLink;

	public ImplicitlyDefinedWebViewFrame(MobileScreen screen, HowToGetByFrames howTo) {
		super(screen, howTo);
	}
	
	@InteractiveMethod
	public void clickOnFoo(){
		fooLink.click();
	}

}
