package app_modeling.web.raw_pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

public class FilterListAndButton extends FunctionalPart<Handle> {

	@FindAll({@FindBy(className = "treedoclistview-root-node-name"),
		@FindBy(xpath = ".//*[contains(@class,'goog-listitem-container')]")})
	private List<WebElement> sections;
	
	@FindBy(xpath = ".//*[contains(@class,'goog-toolbar-item-new')]")
	private WebElement newDocumentButton;
	
	protected FilterListAndButton(FunctionalPart<?> parent, By by) {
		super(parent, by);
	}
	
	@InteractiveMethod
	public void choseSection(int sectionNum) {
		sections.get(0).click();
	}
	
	@InteractiveMethod
	public void clickButton(){
		newDocumentButton.click();
	}
	
	//Some more actions could be implemented here
	//.......

}
