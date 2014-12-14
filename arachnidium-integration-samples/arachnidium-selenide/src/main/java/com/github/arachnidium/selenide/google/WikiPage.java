package com.github.arachnidium.selenide.google;

import org.openqa.selenium.By;

import com.github.arachnidium.model.browser.BrowserPage;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.core.BrowserWindow;

@ExpectedURL(regExp = "https://ru.wikipedia.org/wiki")
@ExpectedURL(regExp = "wikipedia.org")
@ExpectedPageTitle(regExp = "^*[?[Hello]\\?[world]]")
public class WikiPage extends BrowserPage {

	public WikiPage(BrowserWindow browserWindow, HowToGetByFrames path, By by){
		super(browserWindow, path, by);
	}
}
