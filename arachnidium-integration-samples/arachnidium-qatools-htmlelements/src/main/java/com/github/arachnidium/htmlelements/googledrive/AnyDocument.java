package com.github.arachnidium.htmlelements.googledrive;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.TimeOut;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

@ExpectedURL(regExp = "docs.google.com/document/")
@ExpectedURL(regExp = "/document/")
@TimeOut(timeOut = 10)
public abstract class AnyDocument<T extends Handle> extends FunctionalPart<T> {
	@FindBy(xpath = ".//*[@id='docs-titlebar-share-client-button']/div")
	private Button shareButton;

	protected AnyDocument(T handle, HowToGetByFrames path, By by) {
		super(handle, path, by);
		// (!!!)
		HtmlElementLoader.populatePageObject(this,
				getWrappedDriver());
	}

	@InteractiveMethod
	public void clickShareButton() {
		shareButton.click();
	}

}
