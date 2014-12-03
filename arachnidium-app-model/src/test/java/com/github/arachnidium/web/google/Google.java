package com.github.arachnidium.web.google;

import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserApplication;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.support.annotations.rootelements.RootElement;

public class Google extends BrowserApplication implements IPerformsSearch, ILinkList, WrapsDriver{
		
	@Static
	@RootElement(chain = {@FindBy(id = "viewport")})
	@RootElement(chain = {@FindBy(tagName = "body")}) //:)
	private SearchBar<?> searchBar;	
	@Static
	private LinksAreFound<?> linksAreFound;
	
	protected Google(BrowserWindow browserWindow){
		super(browserWindow);  	
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
