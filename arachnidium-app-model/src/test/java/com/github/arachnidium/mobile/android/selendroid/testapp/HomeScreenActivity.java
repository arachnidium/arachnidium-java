package com.github.arachnidium.mobile.android.selendroid.testapp;

import java.util.List;
import java.util.NoSuchElementException;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.NativeContent;

public class HomeScreenActivity extends NativeContent {
	@FindBy(id = "io.selendroid.testapp:id/my_text_field")
	private MobileElement myTextField;	
	@FindBy(id = "io.selendroid.testapp:id/visibleButtonTest")
	private AndroidElement visibleButtonTest;
	@FindBy(id = "io.selendroid.testapp:id/visibleTextView")
	private RemoteWebElement visibleTextView;	
	@AndroidFindBy(accessibility = "showPopupWindowButtonCD")
	private WebElement showPopupWindowButton;
	@AndroidFindBy(accessibility = "waitingButtonTestCD")
	private WebElement waitingButtonTest;
	@AndroidFindBy(accessibility = "buttonStartWebviewCD")
	private WebElement buttonStartWebview;
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"io.selendroid.testapp:id/goBack\")")
	private MobileElement goBack;
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"io.selendroid.testapp:id/spinner_webdriver_test_data\")")
	private WebElement spinner;
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/text1\")")
	private List<WebElement> itemsForSelect;
	
	public HomeScreenActivity(MobileScreen context) {
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
	
	@InteractiveMethod
	public void clickOnSpinner(){
		spinner.click();
	}
	
	@InteractiveMethod
	public void selectSpinnerItem(String item){
		for (WebElement element: itemsForSelect){
			if (element.getText().equals(item)){
				element.click();
				return;
			}
		}
		throw new NoSuchElementException("There is no item " + item);
	}
}
