package com.github.arachnidium.htmlelements.googledrive;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class LogOut<S extends Handle> extends FunctionalPart<S> {
	
	@FindAll({@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]"),
		@FindBy(id="gbgs4d")})
	private Link profile;	
	@FindAll({@FindBy(xpath = ".//*[@class='gbmpalb']/a"),
		@FindBy(xpath = ".//*[@class='gb_pa']/div[2]/a")})
	private Button quitButton;

	protected LogOut(FunctionalPart<?> parent) {
		super(parent);
		// (!!!)
		HtmlElementLoader.populatePageObject(this,
						getWrappedDriver());
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
