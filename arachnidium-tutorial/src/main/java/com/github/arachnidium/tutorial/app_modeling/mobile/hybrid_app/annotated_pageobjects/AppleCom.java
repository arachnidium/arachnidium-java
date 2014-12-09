package com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.annotated_pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedContext;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.MobileContextNamePatterns;

/**
 * This can describe apple.com web page as well 
 * content of web view.
 *
 */
@ExpectedURL(regExp = ".apple.com")
@DefaultPageIndex(index = 0)
@ExpectedPageTitle(regExp = "Apple")

@ExpectedContext(regExp = MobileContextNamePatterns.WEBVIEW)
public class AppleCom extends FunctionalPart<Handle> {

	@FindBy(className = "gh-tab-link")
	private List<WebElement> links;
	
	protected AppleCom(Handle handle) {
		super(handle);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void selectLink(String text){
		for (WebElement link: links){
			if (link.getText().equals(text)){
				link.click();
				return;
			}
		}
		throw new NoSuchElementException("There is no link with text " + text);	
	}
	
	@InteractiveMethod
	public void selectShop(String shop) {
		getWrappedDriver().findElement(
				By.xpath(".//*[@data-display-name='" + shop + "']")).click();
	}
}
