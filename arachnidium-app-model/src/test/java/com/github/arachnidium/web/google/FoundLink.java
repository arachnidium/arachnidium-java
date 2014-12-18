package com.github.arachnidium.web.google;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;

public class FoundLink extends FunctionalPart<Handle> {

	@FindBys({@FindBy(className = "r"),@FindBy(tagName = "a")})
	private List<WebElement> lins; 
	
	protected FoundLink(FunctionalPart<?> parent, HowToGetByFrames path, By by) {
		super(parent, path, by);
	}
	
	protected FoundLink(Handle h, HowToGetByFrames path, By by) {
		super(h, path, by);
	}
	
	@InteractiveMethod
	public int getLinkCount(){
		return lins.size();
	}

}
