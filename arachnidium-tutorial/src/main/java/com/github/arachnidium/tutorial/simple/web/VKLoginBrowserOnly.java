package com.github.arachnidium.tutorial.simple.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserPage;
import com.github.arachnidium.core.HowToGetByFrames;

//
 // com.github.arachnidium.model.browser.BrowserPage is a com.github.arachnidium.model.common.FunctionalPart subclass which
 // is adapted to describe interaction only with browser pages.
 // It is useful when only browser interaction has to be checked.
 // Actions like URL navigation, page refreshing and window size
 // changing are already implemented here. 
 //
 //What actually is it? See below
 //
 // package com.github.arachnidium.model.browser;
 // ...
 // public abstract class BrowserPage extends FunctionalPart<BrowserWindow> implements Navigation,
 //		Window{
 // ...
public class VKLoginBrowserOnly extends BrowserPage {

	@FindAll({
		@FindBy(id = "quick_email"),
		@FindBy(id = "email")
	})
	private WebElement login;
	
	@FindAll({
		@FindBy(id = "quick_pass"),
		@FindBy(id = "pass")
	})
	private WebElement password;
	
	@FindAll(
			{@FindBy(xpath = "//button[contains(.,'Log in')]"),
			@FindBy(xpath = "//button[contains(.,'Войти')]")
	})
	private WebElement logInBtn;
	
	protected VKLoginBrowserOnly(BrowserWindow window, HowToGetByFrames path, By by) {
		super(window, path, by);
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
