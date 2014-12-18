package com.github.arachnidium.web.google;

import java.util.List;

import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.browser.BrowserApplication;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.model.support.annotations.rootelements.RootElement;

public class Google extends BrowserApplication implements IPerformsSearch, ILinkList, WrapsDriver{
		
	@Static
	@RootElement(chain = {@FindBy(id = "viewport")})
	@RootElement(chain = {@FindBy(tagName = "body")}) //:)
	private SearchBar<?> searchBar;	
	@Static
	public LinksAreFound<?> linksAreFound;
	
	@Static
	public List<FoundLink> foundLinks1;
	
	@Static
	public List<FoundLink2> foundLinks2;
	
	@Static
	@RootElement(chain = {@FindBy(className="rc")})
	public List<FoundLink> foundLinks3;
	
	@Static
	@RootElement(chain = {@FindBy(className="rc")}, index = 4)
	public List<FoundLink> foundLinks4;
	
	@Static
	@RootElement(chain = {@FindBy(className="rc")}, index = 4)
	public List<FoundLink2> foundLinks5;
	
	@Static
	@RootElement(chain = {@FindBy(className="fake")}, index = 4)
	public List<FoundLink> foundLinks6;
	
	@Static
	@RootElement(chain = {@FindBy(className="fake")}, index = 4)
	public List<FoundLink2> foundLinks7;
	
	@Static
	@ExpectedURL(regExp = "fake")
	@RootElement(chain = {@FindBy(className="rc")})
	public List<FoundLink2> foundLinks8;
	
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
