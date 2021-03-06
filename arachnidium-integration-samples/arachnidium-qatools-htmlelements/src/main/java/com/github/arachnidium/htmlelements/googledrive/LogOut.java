package com.github.arachnidium.htmlelements.googledrive;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class LogOut<S extends Handle> extends FunctionalPart<S> {
	
	protected LogOut(S handle) {
		super(handle);
		// (!!!)
		HtmlElementLoader.populatePageObject(this,
								getWrappedDriver());
	}

	@FindAll({@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]"),
		@FindBy(id="gbgs4d")})
	private Link profile;	
	@FindAll({@FindBy(xpath = ".//*[@class='gbmpalb']/a"),
		@FindBy(xpath = ".//*[@class='gb_Ba']/div[2]/a")})
	private Button quitButton;
	
	@InteractiveMethod
	public void clickOnProfile(){
		profile.click();
	}
	
	@InteractiveMethod
	public void quit(){
		quitButton.click();
	}
	
	

}
