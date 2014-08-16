package org.arachnidium.mobile.android.bbc;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;

import java.util.List;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class TopicList<T extends Handle> extends FunctionalPart<T> {
	@CacheLookup
	@AndroidFindBys({@AndroidFindBy(id = "bbc.mobile.news.ww:id/personalisationListView"),
		@AndroidFindBy(className = "android.widget.LinearLayout"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/feedTitle\")")})
	private List<WebElement> titles;
	@CacheLookup
	@AndroidFindBys({@AndroidFindBy(id = "bbc.mobile.news.ww:id/personalisationListView"),
		@AndroidFindBy(className = "android.widget.LinearLayout"),
		@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.CheckBox\")")})
	private List<WebElement> checkBoxes;
	
	@FindBy(id = "bbc.mobile.news.ww:id/personlisationOkButton")
	private WebElement okButton;
	
	
	
	protected TopicList(FunctionalPart<?> parent) {
		super(parent);
		load();
	}
	
	@InteractiveMethod
	public void setTopicChecked(String topicText, boolean checked) {
		for (WebElement title: titles){
			if (!title.getText().equals(topicText)){
				continue;
			}
			int index = titles.indexOf(title);
			WebElement checkBox = checkBoxes.get(index);
			if (!checkBox.getAttribute("checked").equals(
					String.valueOf(checked))) {
				checkBox.click();
			}
			return;
		}
		
		throw new NoSuchElementException("There is no topic " + topicText);
	}
	
	@InteractiveMethod
	public void ok(){
		okButton.click();
	}

}
