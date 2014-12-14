package com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.unannotated_pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;

/**
 * This can describe apple.com web page as well 
 * content of web view.
 *
 */
public class AppleCom extends FunctionalPart<Handle> {
	
	/**
	 * Below is an available option if we want the interaction with
	 * only web view of mobile client
	 * 
	 *  ...
	 *  import com.github.arachnidium.model.mobile.WebViewContent;
	 *  ....
	 * 
	 *  public class AppleCom extends WebViewContent{
	 *  ...
	 *  
	 */	

	@FindBy(className = "gh-tab-link")
	private List<WebElement> links;
	
	protected AppleCom(Handle handle, HowToGetByFrames path, By by) {
		super(handle, path, by);
	}
	
	/**
	 * Below is an available option if we want the interaction with
	 * only web view of mobile client
	 * 
	 *  ...
	 *  import com.github.arachnidium.model.mobile.WebViewContent;
	 *  import com.github.arachnidium.core.MobileScreen;
	 *  ....
	 * 
	 *	protected AppleCom(MobileScreen screen, HowToGetByFrames path, By by) {
	 *		super(screen);
	 *	}
	 *  
	 */		
	
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
