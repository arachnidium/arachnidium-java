package com.github.arachnidium.tutorial.simple.mobile_and_web;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;

//
// com.github.arachnidium.model.common.FunctionalPart is the basic class which subclasses help to describe interaction
// with whole browser page or screen of mobile application, or their part.
// I.e. by FunctionalPart subclasses can be used when there are repeatable
// widgets or element associations. 
// 
// Actually it is one more way how to use Page Object design pattern.
//
// @param <S> Each page/screen/repeatable element set (instance of {@link FunctionalPart} subclass) 
// can be bound to a browser or context (NATIVE_APP, WEBVIEW, WEBVIEW_1 and some
// more) of mobile application. If "S extends Handle" is declared
// it means that this UI can be located on a browser window as well as on mobile screen.
// com.github.arachnidium.core.Handle is an abstraction which describes basic behavior of 
//com.github.arachnidium.core.BrowserWindow and com.github.arachnidium.core.MobileScreen. 
//If you wish to bound this description only to a browser, you have to 
// declare 
// 
// public class VKLogin extends FunctionalPart<BrowserWindow>{ 
// 
// 
// The similar way you can to bound your UI description to a mobile screen
//
// public class VKLogin extends FunctionalPart<MobileScreen> {
public class VKLogin<S extends Handle> extends FunctionalPart<S> {

	@FindAll({
		@FindBy(id = "quick_email"),
		@FindBy(id = "email")
	})
	@AndroidFindBy(id = "com.vkontakte.android:id/auth_login")
	//@iOSFindBy(suitable locator)
	//this is situation when browser and native Android/iOS UI are similar.
	//In this case WebElement field can be marked as above 
	private WebElement login;
	//In this case it is possible to declare
	//private RemoteWebElement login;
	
	@FindAll({
		@FindBy(id = "quick_pass"),
		@FindBy(id = "pass")
	})
	@AndroidFindBy(id = "com.vkontakte.android:id/auth_pass")
	private WebElement password;
	
	@FindAll(
			{@FindBy(xpath = "//button[contains(.,'Log in')]"),
			@FindBy(xpath = "//button[contains(.,'Войти')]")
	})
	@AndroidFindBy(id = "com.vkontakte.android:id/auth_btn")
	private WebElement logInBtn;
	
	//
	 // This constructor should present 
	 // when an instance of the class is going to
	 // be got from the given {@link Handle} e.g. browser window
	 // or mobile context. I.e. UI or repeatable widget/element association
	 // is located on the window/native or webview context.
	 //
	 // There are possible more complex variants which will be shown in another
	 // chapters.
	 // 
	 //@param handle is the given browser window
	 //or mobile context
	 //
	 //@example
	 //If this constructor is present then instance can got by these methods (for example)
	 //
	 //applicationInstance.getPart(someUIDescription.class);
	 //applicationInstance.getPart(someUIDescription.class, windowOrContextIndex);
	 //applicationInstance.getPart(someUIDescription.class, howToGetBrowserWindowInstance);
	 //
	 //Special things will be described in another
	 // chapters.
	protected VKLogin(S handle, HowToGetByFrames path, By by) {
		super(handle, path, by);
		load(); //this method instantiates values of 
		//WebElement and List<WebElement> (RemoteWebElement and List<RemoteWebElement> are possible) fields marked by 
		//@FindBy, @FindBys, @FindAll, @AndroidFindBy, @AndroidFindBys, @iOSFindBy, @iOSFindBys.
		//It is the default method which you can use. 
		
		//Another methods which perform the same function:
		//- protected void load(FieldDecorator decorator) - instantiates declared WebElement fields
		//using org.openqa.selenium.support.PageFactory and org.openqa.selenium.support.pagefactory.FieldDecorator implementor
		
		//- protected void load(ElementLocatorFactory factory) - instantiates declared WebElement fields
		//using org.openqa.selenium.support.PageFactory and org.openqa.selenium.support.pagefactory.ElementLocatorFactory implementor
	}
	
	//@InteractiveMethod is very useful annotation.
    //Details will be in another chapters. You should know
	//that it separates methods which simulate interaction
	//from other methods of Java programming language Object 
	@InteractiveMethod
	public void setLogin(String login){
		this.login.sendKeys(login);
	}
	
	@InteractiveMethod
	public void setPassword(String password){
		this.password.sendKeys(password);
	}
	
	@InteractiveMethod
	public void enter(){
		this.logInBtn.click();
	}

}
