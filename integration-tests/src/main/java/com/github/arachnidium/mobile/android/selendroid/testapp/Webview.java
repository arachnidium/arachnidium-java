package com.github.arachnidium.mobile.android.selendroid.testapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.WebViewContent;

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
