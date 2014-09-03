package org.arachnidium.web.google;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.core.Handle;

public class LinksAreFound<T extends Handle> extends FunctionalPart<T> implements ILinkList {
	
	@FindBy(id = "ires")
	private RemoteWebElement result;
	
	protected LinksAreFound(T handle) {
		super(handle);
		load();
	}

	@InteractiveMethod
	public void openLinkByIndex(int index) {
		String reference = result.findElements(By.xpath(".//*[@class='r']/a")).
				get(index - 1).getAttribute("href");
		scriptExecutor.executeScript("window.open('" + reference + "');");
	}

	@InteractiveMethod
	public int getLinkCount() {
		return result.findElements(By.xpath(".//*[@class='r']/a")).size();
	}

	@InteractiveMethod
	public void clickOnLinkByIndex(int index) {
		Actions clickAction = new Actions(getWrappedDriver());
		clickAction.click(result.findElements(By.xpath(".//*[@class='r']/a")).get(index - 1));
		clickAction.perform();
	}

}
