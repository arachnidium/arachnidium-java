package com.github.arachnidium.tutorial.simple.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class UserPage<S extends Handle> extends FunctionalPart<S> {
   
	@FindBy(className="top_home_link_td")
	private WebElement home; 
	@FindBys({@FindBy(id="l_vid"),
		@FindBy(xpath = ".//*[@class = 'left_label inl_bl']")})
	private WebElement videos;
	
	protected UserPage(S handle) {
		super(handle);
		load();
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
