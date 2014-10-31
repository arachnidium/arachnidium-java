package com.github.arachnidium.tutorial.simple.mobile;

import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.NativeContent;

public class VKLoginNativeOnly extends NativeContent {

	@FindBy(id = "com.vkontakte.android:id/auth_login")
	private AndroidElement login;
	
	@FindBy(id = "com.vkontakte.android:id/auth_pass")
	private AndroidElement password;
	
	@FindBy(xpath = "com.vkontakte.android:id/auth_btn")
	private AndroidElement logInBtn;
	
	public VKLoginNativeOnly(MobileScreen context) {
		super(context);
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
