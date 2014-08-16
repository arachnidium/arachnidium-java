package org.arachnidium.mobile.android.selendroid.testapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.mobile.Context;
import org.arachnidium.core.MobileContext;


public class RegisterANewUser extends Context {
    @FindBy(id="inputUsername")
	private WebElement inputUsername;
    @FindBy(id="inputEmail")
    private WebElement inputEmail;
    @FindBy(id="inputPassword")
    private WebElement inputPassword;
    @FindBy(id="inputName")
    private WebElement inputName;
    @FindBy(id = "input_preferedProgrammingLanguage")
    private WebElement input_preferedProgrammingLanguage;
    @FindBy(id = "btnRegisterUser")
    private WebElement btnRegisterUser;
    @FindBy(id = "buttonRegisterUser")
    private WebElement buttonRegisterUser;
    
	protected RegisterANewUser(MobileContext context) {
		super(context);
		load();
	}

	@InteractiveMethod
	public void inputUsername(String userName){
		inputUsername.sendKeys(userName);
	}
	
	@InteractiveMethod
	public void inputEmail(String eMail){
		inputEmail.sendKeys(eMail);
	}
	
	@InteractiveMethod
	public void inputPassword(String password){
		inputPassword.sendKeys(password);
	}
	
	@InteractiveMethod
	public void inputName(String name){
		inputName.clear();
		inputName.sendKeys(name);
	}
	
	
	@InteractiveMethod
	public void clickVerifyUser(){
		btnRegisterUser.click();
	}
	
	@InteractiveMethod
	public void clickRegisterUser(){
		buttonRegisterUser.click();
	}
}
