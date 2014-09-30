package com.github.arachnidium.htmlelements.googledrive;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

/**
 * Login form
 *
 */
@IfBrowserDefaultPageIndex(index = 0)
@IfBrowserURL(regExp = "https://accounts.google.com/ServiceLogin")
@IfBrowserURL(regExp = "accounts.google.com")
public class LoginToGoogleService<T extends Handle> extends FunctionalPart<T> {
	
	@FindBy(name = "Email")
	private TextInput eMail;	
	@FindBy(id="Passwd")
	private TextInput password;
	@FindBy(name="PersistentCookie")
	private CheckBox persistentCookie;
	@FindBy(name="signIn")
	private Button singIn;
	
	protected LoginToGoogleService(T handle) {
		super(handle);
		// (!!!)
		HtmlElementLoader.populatePageObject(this,
						getWrappedDriver());
	}
	
	@InteractiveMethod
	public void clickOnPersistentCookie(){
		persistentCookie.set(false);
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
		singIn.click();
	}

}
