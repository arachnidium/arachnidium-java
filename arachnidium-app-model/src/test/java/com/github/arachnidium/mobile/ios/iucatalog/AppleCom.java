package com.github.arachnidium.mobile.ios.iucatalog;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

/**
 * This can describe apple.com web page as well 
 * content of web view.
 *
 */
public class AppleCom extends FunctionalPart<Handle> {

	@FindBy(className = "gh-tab-link")
	private List<WebElement> links;
	
	protected AppleCom(FunctionalPart<?> parent) {
		super(parent);
	}
	
	@InteractiveMethod
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
