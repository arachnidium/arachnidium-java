package com.github.arachnidium.selenide.google;

import com.github.arachnidium.model.browser.BrowserPage;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import com.github.arachnidium.core.BrowserWindow;

@IfBrowserURL(regExp = "https://ru.wikipedia.org/wiki")
@IfBrowserURL(regExp = "[wikipedia//org//wiki]")
@IfBrowserPageTitle(regExp = "^*[?[Hello]\\?[world]]")
public class WikiPage extends BrowserPage {

	public WikiPage(BrowserWindow browserWindow){
		super(browserWindow);
	}
}
