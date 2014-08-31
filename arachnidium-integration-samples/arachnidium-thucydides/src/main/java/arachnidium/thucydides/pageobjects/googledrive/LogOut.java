package arachnidium.thucydides.pageobjects.googledrive;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class LogOut<S extends Handle> extends FunctionalPart<S> {
	
	@FindAll({@FindBy(xpath = ".//*[contains(@href,'https://profiles.google.com/')]"),
		@FindBy(id="gbgs4d")})
	private WebElement profile;	
	@FindAll({@FindBy(xpath = ".//*[@class='gbmpalb']/a"),
		@FindBy(xpath = ".//*[@class='gb_ka']/div[2]/a")})
	private WebElement quitButton;

	protected LogOut(FunctionalPart<?> parent) {
		super(parent);
		load();
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
