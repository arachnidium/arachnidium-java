package app_modeling.web.annotated_pageobjects.aggregated_page_objects_with_annotated_fields;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedURL;


@ExpectedURL(regExp = "https://accounts.google.com/ServiceLogin")  /**<== Possible URLs that should be loaded can be declared by annotations*/   
@ExpectedURL(regExp = "accounts.google.com") /**Each one @ExpectedURL means one more possible web address*/
/**Declaration is applied to subclasses till they are annotated by @ExpectedURL with 
another values. Also if the class is going to be instantiated by {@link Application#getPart(Class, com.github.arachnidium.core.fluenthandle.IHowToGetHandle)}
(where IHowToGetHandle is a {@link HowToGetPage} instance) then 
the values contained by given strategy will be used instead of declared by annotations*/

@DefaultPageIndex(index = 0) /**<-Also it is possible to define the default expected window/tab index*/

/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class LoginToGoogleService<T extends Handle> extends FunctionalPart<T> {/**<-- it is the example which demonstrates 
       component which can be used 
	   when interaction with browser and native/webview content is needed*/
	
	@FindBy(name = "Email")
	private WebElement eMail;	
	@FindBy(id="Passwd")
	private WebElement password;
	@FindBy(name="PersistentCookie")
	private WebElement persistentCookie;
	@FindBy(name="signIn")
	private WebElement singIn;
	
	/**
	 * If it is implemented as something general
	 * (general page/screen description) then it 
	 * should have (one of) constructors like these:
	 * 
     * {@link FunctionalPart##FunctionalPart(Handle)}
	 * {@link FunctionalPart##FunctionalPart(Handle, org.openqa.selenium.By) }
	 * {@link FunctionalPart##FunctionalPart(Handle, com.github.arachnidium.model.support.HowToGetByFrames)}
	 * {@link FunctionalPart##FunctionalPart(Handle, com.github.arachnidium.model.support.HowToGetByFrames, org.openqa.selenium.By)}
	 */
	protected LoginToGoogleService(T handle) {/**<-- it is the example which demonstrates 
	       component which can be used 
		   when interaction with browser and native/webview content is needed*/
		super(handle);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void clickOnPersistentCookie(){
		persistentCookie.click();
	}
	
	@InteractiveMethod
	public void setEmail(String eMail){
		this.eMail.sendKeys(eMail);
	}
	
	@InteractiveMethod
	public void setPassword(String password){
		this.password.sendKeys(password);
	}
	
	@InteractiveMethod
	public void singIn(){
		singIn.submit();
	}

}
