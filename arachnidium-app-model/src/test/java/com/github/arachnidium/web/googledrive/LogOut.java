package com.github.arachnidium.web.googledrive;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class LogOut<S extends Handle> extends FunctionalPart<S> {
	
	@FindAll({@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]"),
		@FindBy(id="gbgs4d")})
	private WebElement profile;	
	@FindAll({@FindBy(xpath = ".//*[@class='gbmpalb']/a"),
		@FindBy(xpath = ".//*[@class='gb_ra']/div[2]/a")})
	private WebElement quitButton;

	protected LogOut(FunctionalPart<?> parent) {
		super(parent);
	}
	
	@InteractiveMethod
	public void clickOnProfile(){
		profile.click();
	}
	
	@InteractiveMethod
	public void quit(){
		quitButton.click();
	}
	
	

}
