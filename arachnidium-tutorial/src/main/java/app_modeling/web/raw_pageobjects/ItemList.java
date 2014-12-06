package app_modeling.web.raw_pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class ItemList extends FunctionalPart<Handle> {

	@FindBys({@FindBy(className = "doclist-name-wrapper"), @FindBy(tagName = "a")})
	private List<WebElement> documents;

	protected ItemList(FunctionalPart<?> parent, By by) {
		super(parent, by);
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
	
	//the working with the toolbar above the document table could be implemented below
	//.....................

}
