package com.github.arachnidium.mobile.android.selendroid.testapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.WebViewContent;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;

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
