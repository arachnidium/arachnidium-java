package com.github.arachnidium.tutorial.simple.mobile_and_web;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class User<S extends Handle> extends FunctionalPart<S> {
   
	@FindBy(className="top_home_link_td")
	@AndroidFindBy(uiAutomator="new UiSelector().resourceId(\"android:id/home\")")
	private WebElement home; 
	@FindBys({@FindBy(id="l_vid"),
		@FindBy(xpath = ".//*[@class = 'left_label inl_bl']")})
	@AndroidFindBy(uiAutomator="new UiSelector().text(\"Videos\")")
	private WebElement videos;
	
	protected User(S handle) {
		super(handle);
	}
	
	@InteractiveMethod
	public void goHome(){
		home.click();
	}
	
	@InteractiveMethod
	public void selectVideos(){
		videos.click();
	}

}
