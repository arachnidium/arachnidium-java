package org.arachnidium.web.googledrive;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

@IfBrowserURL(regExp = "drive.google.com/drive/")
@IfBrowserURL(regExp = "drive.google.com")
@IfBrowserDefaultPageIndex(index = 0)
public class DocumentList<S extends Handle> extends FunctionalPart<S> {

	@FindAll({@FindBy(className = "treedoclistview-root-node-name"),
		@FindBy(xpath = ".//*[contains(@class,'goog-listitem-container')]")})
	private List<WebElement> sections;

	@FindBys({@FindBy(className = "doclist-name-wrapper"), @FindBy(tagName = "a")})
	private List<WebElement> documents;

	protected DocumentList(S handle) {
		super(handle);
		load();
	}

	@InteractiveMethod
	public void choseSection(int sectionNum) {
		sections.get(0).click();
	}

	@InteractiveMethod
	public void clickOnDoc(String name) {
		List<WebElement> areFound = new ArrayList<WebElement>();
		documents
				.forEach((document) -> {
					if (document.getText().equals(name)) {
						document.click();
						areFound.add(document);
					}
				});
		if (areFound.size() == 0) {
			throw new NoSuchElementException("There is no document named "
					+ name);
		}
	}

}
