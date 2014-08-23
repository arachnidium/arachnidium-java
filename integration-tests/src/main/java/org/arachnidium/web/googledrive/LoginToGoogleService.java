package org.arachnidium.web.googledrive;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login form
 *
 */
@IfBrowserDefaultPageIndex(index = 0)
@IfBrowserURL(regExp = "https://accounts.google.com/ServiceLogin")
@IfBrowserURL(regExp = "accounts.google.com")
public class LoginToGoogleService<T extends Handle> extends FunctionalPart<T> {
	
	@FindBy(name = "Email")
	private WebElement eMail;	
	@FindBy(id="Passwd")
	private WebElement password;
	@FindBy(name="PersistentCookie")
	private WebElement persistentCookie;
	@FindBy(name="signIn")
	private WebElement singIn;
	
	protected LoginToGoogleService(T handle) {
		super(handle);
		load();
	}
	
	@InteractiveMethod
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
