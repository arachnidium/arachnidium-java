package com.github.arachnidium.tutorial.simple.mobile;

import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.NativeContent;
import com.github.arachnidium.model.support.HowToGetByFrames;

//
// com.github.arachnidium.model.mobile.NativeContent is a com.github.arachnidium.model.common.FunctionalPart subclass which
// is adapted to describe interaction only with native app content.
// It is useful when only interaction with native app content has to be checked.
// Actions like rotation, touching and scrolling implemented here. 
//
// What actually is it? See below
//
// package com.github.arachnidium.model.mobile;
// ...
//public abstract class NativeContent extends FunctionalPart<MobileScreen> implements Rotatable, 
// DeviceActionShortcuts, TouchShortcuts, ScrollsTo {
public class VKLoginNativeOnly extends NativeContent {

	@FindBy(id = "com.vkontakte.android:id/auth_login")
	private AndroidElement login;
	
	@FindBy(id = "com.vkontakte.android:id/auth_pass")
	private AndroidElement password;
	
	@FindBy(xpath = "com.vkontakte.android:id/auth_btn")
	private AndroidElement logInBtn;
	
	public VKLoginNativeOnly(MobileScreen context, HowToGetByFrames path, By by) {
		super(context, path, by);
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
