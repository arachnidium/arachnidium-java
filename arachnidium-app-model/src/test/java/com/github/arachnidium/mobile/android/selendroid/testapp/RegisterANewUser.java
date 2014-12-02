package com.github.arachnidium.mobile.android.selendroid.testapp;

import io.appium.java_client.MobileElement;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.NativeContent;


public class RegisterANewUser extends NativeContent {
    @FindBy(id="io.selendroid.testapp:id/inputUsername")
	private MobileElement inputUsername;
    @FindBy(id="io.selendroid.testapp:id/inputEmail")
    private MobileElement inputEmail;
    @FindBy(id="io.selendroid.testapp:id/inputPassword")
    private MobileElement inputPassword;
    @FindBy(id="io.selendroid.testapp:id/inputName")
    private MobileElement inputName;
    @FindBy(id = "input_preferedProgrammingLanguage")
    private WebElement input_preferedProgrammingLanguage;
    @FindBy(id = "io.selendroid.testapp:id/btnRegisterUser")
    private WebElement btnRegisterUser;
    @FindBy(id = "io.selendroid.testapp:id/buttonRegisterUser")
    private WebElement buttonRegisterUser;
    
	protected RegisterANewUser(MobileScreen context) {
		super(context);
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
