package com.github.arachnidium.mobile.android.selendroid.testapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.WebViewContent;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.DefaultContextIndex;
import com.github.arachnidium.model.support.annotations.ExpectedAndroidActivity;
import com.github.arachnidium.model.support.annotations.Frame;

@DefaultContextIndex(index = 1)
@ExpectedAndroidActivity(regExp = "WebViewActivity")
@Frame(howToGet = How.ID, locator = "iframe1")
public class ImplicitlyDefinedWebViewFrame extends WebViewContent {
	
	@FindBy(xpath = ".//*[@href='/foo']")
	private WebElement fooLink;

	public ImplicitlyDefinedWebViewFrame(MobileScreen screen, HowToGetByFrames howTo, By by) {
		super(screen, howTo, by);
	}
	
	@InteractiveMethod
	public void clickOnFoo(){
		fooLink.click();
	}

}
