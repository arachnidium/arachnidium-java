package com.github.arachnidium.web.google;

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

	public void openLinkByIndex(int index) {
		getPart(LinksAreFound.class).openLinkByIndex(index);		
	}

	public int getLinkCount() {
		return getPart(LinksAreFound.class).getLinkCount();
	}
	
	//closes google main page
	public void close()
	{
		//TODO workaround
		((BrowserWindow) handle).close();
		destroy();
	}	

	@Override
	public void clickOnLinkByIndex(int index) {
		getPart(LinksAreFound.class).clickOnLinkByIndex(index);		
	}
}
