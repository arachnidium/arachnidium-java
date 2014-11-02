package com.github.arachnidium.tutorial.simple.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
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
 * @see VKLoginBrowserOnly
 */
public class VKLogin<S extends Handle> extends FunctionalPart<S> {

	@FindAll({
		@FindBy(id = "quick_email"),
		@FindBy(id = "email")
	})
	private WebElement login;
	
	@FindAll({
		@FindBy(id = "quick_pass"),
		@FindBy(id = "pass")
	})
	private WebElement password;
	
	@FindAll(
			{@FindBy(xpath = "//button[contains(.,'Log in')]"),
			@FindBy(xpath = "//button[contains(.,'Войти')]")
	})
	private WebElement logInBtn;
	
	/**
	 * This constructor should present 
	 * when an instance of the class is going to
	 * be got from the given {@link Handle} e.g. browser window
	 * or mobile context. I.e. UI or repeatable widget/element association
	 * is located on the window/native or webview context.
	 * <br/>
	 * There are possible more complex variants which will be shown in another
	 * chapters.
	 * 
	 *@param handle is the given browser window
	 *or mobile context
	 *
	 *@example
	 *If this constructor is present when instance can got by these methods
	 *<br/><br/>
	 *applicationInstance.getPart(someUIDescription.class);<br/>
	 *applicationInstance.getPart(someUIDescription.class, windowOrContextIndex);<br/>
	 *applicationInstance.getPart(someUIDescription.class, howToGetBrowserWindowInstance);<br/>
	 *<br/><br/>
	 *Special things will be described in another
	 * chapters.
	 */
	protected VKLogin(S handle) {
		super(handle);
		load();
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
