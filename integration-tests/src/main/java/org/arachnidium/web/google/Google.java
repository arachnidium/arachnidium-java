package org.arachnidium.web.google;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.model.browser.BrowserApplication;
import org.arachnidium.model.browser.WebFactory;
import org.arachnidium.core.SingleWindow;
import org.arachnidium.core.WebDriverEncapsulation;

public class Google extends BrowserApplication implements IPerformsSearch, ILinkList, WrapsDriver{
	
	private final static String url = "http://www.google.com/";
	private SearchBar searchBar;
	private LinksAreFound linksAreFound;
	
	protected Google(SingleWindow browserWindow){
		super(browserWindow);
		searchBar     = getPart(SearchBar.class);    
		linksAreFound = getPart(LinksAreFound.class); 
	}
	
	public static Google getNew()
	{
		return WebFactory.getApplication(Google.class, url);
	}
	
	public static Google getNew(Configuration config)
	{
		return WebFactory.getApplication(Google.class, config, url);
	}
	
	public static Google getNew(WebDriverEncapsulation externalEncapsulation)
	{
		return WebFactory.getApplication(Google.class, externalEncapsulation, url);
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
		((SingleWindow) handle).close();
		destroy();
	}	

	@Override
	public WebDriver getWrappedDriver() {
		return driverEncapsulation.getWrappedDriver();
	}

	@Override
	public void clickOnLinkByIndex(int index) {
		linksAreFound.clickOnLinkByIndex(index);		
	}
}
