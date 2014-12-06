package app_modeling.web.raw_pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class AccountOptions<S extends Handle> extends FunctionalPart<S> {
	
	@FindAll({@FindBy(xpath = ".//*[@class='gbmpalb']/a"),
		@FindBy(xpath = ".//*[@class='gb_ra']/div[2]/a")})
	private WebElement quitButton;
	
	@FindAll({@FindBy(xpath = ".//*[@class='gb_Xb gb_V']"),
		@FindBy(xpath = ".//*[@class='gbmpala']/a")})
	private WebElement addAccountButton;

	protected AccountOptions(FunctionalPart<?> parent) {
		super(parent);
	}
	
	@InteractiveMethod
	public void quit(){
		quitButton.click();
	}
	
	@InteractiveMethod
	public void addAccount(){
		addAccountButton.click();
	}	
	
	//Some more actions could be implemented here
	//.......

}
