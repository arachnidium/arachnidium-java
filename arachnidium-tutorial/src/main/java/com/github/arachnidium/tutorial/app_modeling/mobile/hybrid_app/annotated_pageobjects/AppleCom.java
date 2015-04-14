package com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.annotated_pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.core.HowToGetByFrames;
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
@ExpectedURL(regExp = ".apple.com") /**<== Possible URLs that should be loaded can be declared by annotations*/
/**Each one @ExpectedURL means one more possible web address*/
/**Declaration is applied to subclasses till they are annotated by @ExpectedURL with 
another values. Also if the class is going to be instantiated by {@link Application#getPart(Class, com.github.arachnidium.core.fluenthandle.IHowToGetHandle)}
then the values contained by given strategy will be used instead of declared by annotations*/
@DefaultPageIndex(index = 0) /**<-Also it is possible to define the default expected window/tab index*/
@ExpectedPageTitle(regExp = "Apple") /**and page title*/

@ExpectedContext(regExp = MobileContextNamePatterns.WEBVIEW) /**Here is the name of the expected mobile application context - 
NATIVE_APP, name of WebView*/
/**Declaration is applied to subclasses till they are annotated by @ExpectedContext with 
another values. Also if the class is going to be instantiated by {@link Application#getPart(Class, com.github.arachnidium.core.fluenthandle.IHowToGetHandle)}
(where IHowToGetHandle is a {@link HowToGetMobileScreen} instance) then 
the values contained by given strategy will be used instead of declared by annotation*/
/**
 * If {@link WebViewContent} is extended then there is no need to annotate class 
 * by @ExpectedContext(regExp = MobileContextNamePatterns.WEBVIEW) because
 * {@link WebViewContent} is already annotated that. :)  
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
