package com.github.arachnidium.web.google;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.rootelements.RootElement;

@RootElement(chain = {@FindBy(className="rc")})
public class FoundLink2 extends FunctionalPart<Handle> {

	@FindBys({@FindBy(className = "r"),@FindBy(tagName = "a")})
	private List<WebElement> lins; 
	
	protected FoundLink2(Handle h) {
		super(h);
	}

	@InteractiveMethod
	public int getLinkCount(){
		return lins.size();
	}
}
