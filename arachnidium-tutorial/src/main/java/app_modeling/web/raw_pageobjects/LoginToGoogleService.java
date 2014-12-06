package app_modeling.web.raw_pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
/**
 * Login form
 *
 */
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
