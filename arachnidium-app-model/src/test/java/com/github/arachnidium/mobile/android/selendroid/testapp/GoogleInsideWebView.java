package com.github.arachnidium.mobile.android.selendroid.testapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.WebViewContent;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;

@ExpectedURL(regExp = "some/searh/service")
@ExpectedURL(regExp = "google")
@ExpectedURL(regExp = "yahoo")
@ExpectedPageTitle(regExp = "Google")
@DefaultPageIndex(index = 0)
public class GoogleInsideWebView extends WebViewContent {
	@FindBy(name = "q")
	private WebElement searchInput;
	@FindBy(name="btnG")
	private WebElement searchButton;

	public GoogleInsideWebView(MobileScreen context, HowToGetByFrames path, By by) {
		super(context, path, by);
	}
	
	@InteractiveMethod
	public void performSearch(String searchString) {
		searchInput.sendKeys(searchString);
		searchButton.click();
	}

}
