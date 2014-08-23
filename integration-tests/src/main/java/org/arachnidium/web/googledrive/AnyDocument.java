package org.arachnidium.web.googledrive;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.arachnidium.model.support.annotations.classdeclaration.TimeOut;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@IfBrowserURL(regExp = "docs.google.com/document/")
@IfBrowserURL(regExp = "/document/")
@TimeOut(timeOut = 10)
public abstract class AnyDocument<T extends Handle> extends FunctionalPart<T> {
	@FindBy(xpath=".//*[@id='docs-titlebar-share-client-button']/div")
	private WebElement shareButton;
	
	protected AnyDocument(T handle) {
		super(handle);
		load();
	}
	
	@InteractiveMethod
	public void clickShareButton(){
		shareButton.click();
	}

}
