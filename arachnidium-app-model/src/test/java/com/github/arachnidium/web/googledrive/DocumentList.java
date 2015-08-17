package com.github.arachnidium.web.googledrive;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedURL;

@ExpectedURL(regExp = "drive.google.com/drive/")
@ExpectedURL(regExp = "drive.google.com")
@DefaultPageIndex(index = 0)
public class DocumentList<S extends Handle> extends FunctionalPart<S> {

	@Static
	public LogOut<?> logOut;
	
	@FindBy(className = "a-pa-P")
	private List<WebElement> sections;

	@FindBy(className = "k-ta-P-x")
	private List<WebElement> documents;

	protected DocumentList(S handle) {
		super(handle);
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
						Actions a = new Actions(getWrappedDriver());
						a.doubleClick(document);
						a.perform();
						areFound.add(document);
					}
				});
		if (areFound.size() == 0) {
			throw new NoSuchElementException("There is no document named "
					+ name);
		}
	}

}
