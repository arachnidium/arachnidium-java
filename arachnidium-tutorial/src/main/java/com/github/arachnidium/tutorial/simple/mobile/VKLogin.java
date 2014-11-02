package com.github.arachnidium.tutorial.simple.mobile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.FunctionalPart;

/**
 * {@link FunctionalPart} is basic class which subclasses help to describe interaction
 * with whole browser page or screen of mobile application, or their part.
 * I.e. by {@link FunctionalPart} subclasses can be used when where are repeatable
 * widgets or element associations. 
 * 
 * Actually it is one more way how to use Page Object design pattern.
 *
 * @param <S> Each page/screen/repeatable element set (instance of {@link FunctionalPart} subclass) 
 * can be bound to a browser or context (NATIVE_APP, WEBVIEW, WEBVIEW_1 and some
 * more) of mobile application. If <b>S extends Handle</b> is declared
 * it means that this UI can be located on a browser window as well as on mobile screen.
 * {@link Handle} is an abstraction which describes basic behavior of {@link BrowserWindow} and
 * {@link MobileScreen}. If you wish to bound this description only to a browser, you have to 
 * declare 
 * <br/><br/> 
 * <b>public class VKLogin extends FunctionalPart<BrowserWindow> {</b>
 * <br/><br/>
 * 
 * The similar way you can to bound your UI description to a mobile screen
 * <br/><br/> 
 * <b>public class VKLogin extends FunctionalPart<MobileScreen> {</b>
 * <br/><br/>
 * 
 * @see VKLoginNativeOnly
 */
public class VKLogin<S extends Handle> extends FunctionalPart<S> {

	@FindBy(id = "com.vkontakte.android:id/auth_login")
	private WebElement login;
	
	@FindBy(id = "com.vkontakte.android:id/auth_pass")
	private WebElement password;
	
	@FindBy(id = "com.vkontakte.android:id/auth_btn")
	private WebElement logInBtn;	
	
	protected VKLogin(S handle) {
		super(handle);
		load();
	}

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
