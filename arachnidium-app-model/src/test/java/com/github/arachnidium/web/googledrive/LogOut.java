package com.github.arachnidium.web.googledrive;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class LogOut<S extends Handle> extends FunctionalPart<S> {
	
	protected LogOut(S handle) {
		super(handle);
	}

	@FindBy(xpath = ".//*[contains(@href,'https://accounts.google.com/SignOutOptions')]")
	private WebElement profile;	
	@FindBy(xpath = ".//*[contains(@href,'https://www.google.com/accounts/Logout')]")
	private WebElement quitButton;

	
	@InteractiveMethod
	public void clickOnProfile(){
		profile.click();
	}
	
	@InteractiveMethod
	public void quit(){
		quitButton.click();
	}
	
	

}
