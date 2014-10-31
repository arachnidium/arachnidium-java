package com.github.arachnidium.tutorial.simple.mobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class VKLogin<S extends Handle> extends FunctionalPart<S> {

	@FindBy(id = "com.vkontakte.android:id/auth_login")
	private WebElement login;
	
	@FindBy(id = "com.vkontakte.android:id/auth_pass")
	private WebElement password;
	
	@FindBy(id = "com.vkontakte.android:id/auth_btn")
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
