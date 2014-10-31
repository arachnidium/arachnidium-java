package com.github.arachnidium.tutorial.simple.mobile_and_web;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class VKLogin<S extends Handle> extends FunctionalPart<S> {

	@FindAll({
		@FindBy(id = "quick_email"),
		@FindBy(id = "email")
	})
	@AndroidFindBy(id = "com.vkontakte.android:id/auth_login")
	private WebElement login;
	
	@FindAll({
		@FindBy(id = "quick_pass"),
		@FindBy(id = "pass")
	})
	@AndroidFindBy(id = "com.vkontakte.android:id/auth_pass")
	private WebElement password;
	
	@FindAll(
			{@FindBy(xpath = "//button[contains(.,'Log in')]"),
			@FindBy(xpath = "//button[contains(.,'Войти')]")
	})
	@AndroidFindBy(id = "com.vkontakte.android:id/auth_btn")
	private WebElement logInBtn;
	
	protected VKLogin(S handle) {
		super(handle);
		load();
	}
	
	@InteractiveMethod
	public void setLogin(String login){
		this.login.sendKeys(login);
	}
	
	@InteractiveMethod
	public void setPassword(String password){
		this.password.sendKeys(password);
	}
	
	@InteractiveMethod
	public void enter(){
		this.logInBtn.click();
	}

}
