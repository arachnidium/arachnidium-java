package com.github.arachnidium.tutorial.confuguration.google;

import org.openqa.selenium.internal.WrapsDriver;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserApplication;

public class Google extends BrowserApplication implements IPerformsSearch, ILinkList, WrapsDriver{

	protected Google(BrowserWindow browserWindow){
		super(browserWindow);
	}
	
	public void performSearch(String searchString) {
		getPart(SearchBar.class).performSearch(searchString);		
	}

	public int getLinkCount() {
		return getPart(LinksAreFound.class).getLinkCount();
	}

	@Override
	public void openLinkByIndex(int index) {
		getPart(LinksAreFound.class).openLinkByIndex(index);		
	}
}
