package com.github.arachnidium.mobile.android.selendroid.testapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
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
	private RemoteWebElement name;
	@FindBy(name="car")
	private WebElement carSelect;
	@FindBy(xpath = ".//*[@type=\"submit\"]")
	private WebElement sendMeYourName;
	
	protected Webview(MobileScreen context) {
		super(context);
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

	@InteractiveMethod
	public void singleTapOnName() {
		touchActions.singleTap(name.getCoordinates());
		
	}

	@InteractiveMethod
	public void downToName() {
		touchActions.down(name.getLocation().x, name.getLocation().y);		
	}

	@InteractiveMethod
	public void upToName() {
		touchActions.up(name.getLocation().x, name.getLocation().y);
		
	}

	@InteractiveMethod
	public void moveToName() {
		touchActions.move(name.getLocation().x, name.getLocation().y);		
	}

	@InteractiveMethod
	@Deprecated //unimplemented command: session/c3c0a2e6ad76f2b0d849c7200c9759c9/touch/doubleclick
	//It doesn't work for now. Maybe it will fixed later.
	public void doubleTapOnName() {
		touchActions.doubleTap(name.getCoordinates());		
	}

	@InteractiveMethod
	@Deprecated //unimplemented command: session/05c63e51288a3ac3a7c35af19ff30735/touch/longclick
	//It doesn't work for now. Maybe it will fixed later.
	public void longPressName() {
		touchActions.longPress(name.getCoordinates());		
	}

	@InteractiveMethod
	@Deprecated //unimplemented command: session/f70ef7b7afbe9e5222fb2e9a0a18bb9d/touch/scroll
	//It doesn't work for now. Maybe it will fixed later.
	public void scrollToName() {
		touchActions.scroll(name.getLocation().x+20, name.getLocation().y+20);	
		
	}

	@InteractiveMethod
	@Deprecated //It doesn't work for now. Maybe it will fixed later.
	public void flickName() {
		touchActions.flick(name.getLocation().x+20, name.getLocation().y+20);			
	}

}
