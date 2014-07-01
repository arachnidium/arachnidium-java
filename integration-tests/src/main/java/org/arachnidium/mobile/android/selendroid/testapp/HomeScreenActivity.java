package org.arachnidium.mobile.android.selendroid.testapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.arachnidium.model.mobile.Context;
import org.arachnidium.core.SingleContext;

public class HomeScreenActivity extends Context {
	@FindBy(id = "my_text_field")
	private WebElement myTextField;	
	@FindBy(id = "visibleButtonTest")
	private WebElement visibleButtonTest;
	@FindBy(id = "visibleTextView")
	private WebElement visibleTextView;	
	@FindBy(id = "showPopupWindowButton")
	private WebElement showPopupWindowButton;
	@FindBy(id = "waitingButtonTest")
	private WebElement waitingButtonTest;
	@FindBy(id = "buttonStartWebview")
	private WebElement buttonStartWebview;
	@FindBy(id = "goBack")
	private WebElement goBack;
	
	public HomeScreenActivity(SingleContext context) {
		super(context);
		load();
	}
	
	@InteractiveMethod
	public void fillMyTextField(String text){
		myTextField.sendKeys(text);
	}
	
	@InteractiveMethod
	public void clickOnVisibleButtonTest(){
		visibleButtonTest.click();
	}
	
	@InteractiveMethod
	public void waitForVisibleTextIsVisible(long secTimeOut){
		awaiting.awaitCondition(secTimeOut, ExpectedConditions.visibilityOf(visibleTextView));
	}
	
	@InteractiveMethod
	public String getVisibleTextView(){
		return visibleTextView.getText();
	}
	
	@InteractiveMethod
	public void showPopupWindowButton(){
		showPopupWindowButton.click();
	}
	
	@InteractiveMethod
	public void waitingButtonTestClick(){
		waitingButtonTest.click();
	}
	
	@InteractiveMethod
	public void startWebviewClick(){
		buttonStartWebview.click();
	}
	
	@InteractiveMethod
	public void goBackClick(){
		goBack.click();
	}	
}
