package org.arachnidium.web.google;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.core.Handle;

public class SearchBar extends FunctionalPart implements IPerformsSearch{
	@FindBy(name = "q")
	private WebElement searchInput;
	@FindBy(name="btnG")
	private WebElement searchButton;
	
	protected SearchBar(Handle handle) {
		super(handle);
		load();
	}

	@InteractiveMethod
	public void performSearch(String searchString) {
		searchInput.sendKeys(searchString);
		searchButton.click();
	}
}
