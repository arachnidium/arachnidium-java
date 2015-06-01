package com.github.arachnidium.mobile.android.selendroid.testapp;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.WebViewContent;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.rootelements.RootElement;

@ExpectedURL(regExp = "some/searh/service")
@ExpectedURL(regExp = "google")
@ExpectedURL(regExp = "yahoo")
@ExpectedPageTitle(regExp = "Google")
@DefaultPageIndex(index = 0)

@RootElement(chain = {@FindBy(id = "test_id"),@FindBy(id = "test_id")})
@RootElement(chain = {@FindBy(id = "search"), @FindBy(id = "ires")})
@RootElement(chain = {@FindBy(id = "ires")})
public class LinksAreFoundInsideWebViewByGoogle extends WebViewContent {

	@FindBys({@FindBy(className = "r"), @FindBy(tagName = "a")})
	private List<WebElement> links;
	
	protected LinksAreFoundInsideWebViewByGoogle(MobileScreen context) {
		super(context);
	}

	@InteractiveMethod
	public void clickOnLinkByIndex(int index) {
		links.get(index - 1).click();
	}

}
