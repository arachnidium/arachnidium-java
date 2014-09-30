package org.arachnidium.mobile.android.selendroid.testapp;

import org.arachnidium.core.MobileScreen;
import org.arachnidium.model.mobile.WebViewContent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 * This is HTML content
 *
 */
public class Webview extends WebViewContent {

	@FindBy(id = "name_input")
	private WebElement name;
	@FindBy(name="car")
	private WebElement carSelect;
	@FindBy(xpath = ".//*[@type=\"submit\"]")
	private WebElement sendMeYourName;
	
	protected Webview(MobileScreen context) {
		super(context);
		load();
	}
	
	@InteractiveMethod
	public void setName(String name){
		this.name.clear();
		this.name.sendKeys(name);
	}
	
	@InteractiveMethod
	public void selectCar(String car){
		highlightAsInfo(carSelect, "By this element " + car + " will be selected");
		Select select = new Select(carSelect);
		select.selectByValue(car);
	}
	
	@InteractiveMethod
	public void sendMeYourName() {
		sendMeYourName.submit();
	}
}
