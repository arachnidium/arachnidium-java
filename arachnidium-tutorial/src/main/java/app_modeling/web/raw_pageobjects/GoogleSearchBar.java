package app_modeling.web.raw_pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class GoogleSearchBar<S extends Handle> extends FunctionalPart<S> {
	@FindBy(name = "q")
	private WebElement searchInput;
	@FindBy(name="btnG")
	private WebElement searchButton;
	
	protected GoogleSearchBar(FunctionalPart<?> parent, By by) {
		super(parent, by);
	}
	
	@InteractiveMethod
	public void performSearch(String searchString) {
		searchInput.sendKeys(searchString);
		searchButton.click();
	}

}
