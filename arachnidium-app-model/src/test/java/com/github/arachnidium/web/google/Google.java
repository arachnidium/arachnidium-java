package com.github.arachnidium.web.google;

import org.openqa.selenium.internal.WrapsDriver;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserApplication;

public class Google extends BrowserApplication implements IPerformsSearch, ILinkList, WrapsDriver{
		
	private SearchBar<?> searchBar;		
	private LinksAreFound<?> linksAreFound;
	
	protected Google(BrowserWindow browserWindow){
		super(browserWindow);  
		searchBar     = getPart(SearchBar.class);
		linksAreFound = getPart(LinksAreFound.class); 		
	}
	
	public void performSearch(String searchString) {
		searchBar.performSearch(searchString);	
	}

	public void openLinkByIndex(int index) {
		linksAreFound.openLinkByIndex(index);		
	}

	public int getLinkCount() {
		return linksAreFound.getLinkCount();
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
		linksAreFound.clickOnLinkByIndex(index);		
	}
}
