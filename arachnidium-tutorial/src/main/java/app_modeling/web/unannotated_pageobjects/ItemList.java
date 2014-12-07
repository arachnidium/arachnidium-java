package app_modeling.web.unannotated_pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

/**it is the example which demonstrates how to implement a child page object*/
public class ItemList extends FunctionalPart<Handle> { /** <==
	 * Here I demonstrate something that is supposed to be used by the web and 
	 * mobile testing 
	 */

	@FindBys({@FindBy(className = "doclist-name-wrapper"), @FindBy(tagName = "a")})
	private List<WebElement> documents;

	
	/**
	 * If you want to represent some page object as a 
	 * "child" component of any page/screen then your implementation 
	 * should have constructors like these
	 * 
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart)}
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart, By)}
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart, com.github.arachnidium.model.support.HowToGetByFrames)}
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart, com.github.arachnidium.model.support.HowToGetByFrames, By)}
	 * 
	 * As you can see the class should have (one of) constructors which instantiate it
	 *  class as a child of more generalized parent
	 */
	protected ItemList(FunctionalPart<?> parent, By by) {
		super(parent, by);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
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
