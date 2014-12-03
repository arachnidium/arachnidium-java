package com.github.arachnidium.thucydides.pageobjects.bbc;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.ExpectedAndroidActivity;
import com.github.arachnidium.model.support.annotations.ExpectedContext;
import com.github.arachnidium.model.support.annotations.ExpectedURL;

/**
 * Imagine that we have to check browser and Android versions
 * How?! See below.
 */
@ExpectedURL(regExp = "http://www.bbc.com/news/")
@ExpectedContext(regExp = "NATIVE_APP")
@ExpectedAndroidActivity(regExp = "PersonalisationActivity")
public class TopicList extends FunctionalPart<Handle> {
	
	@CacheLookup
	@FindBys({@FindBy(linkText = "someLink"), @FindBy(linkText = "someLink2"), @FindBy(linkText = "someLink2")})
	@AndroidFindBys({@AndroidFindBy(id = "bbc.mobile.news.ww:id/personalisationListView"),
		@AndroidFindBy(className = "android.widget.LinearLayout"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/feedTitle\")")})
	private List<WebElement> titles;
	
	@CacheLookup
	@FindBys({@FindBy(linkText = "someLink3"), @FindBy(linkText = "someLink4"), @FindBy(linkText = "someLink5")})
	@AndroidFindBys({@AndroidFindBy(id = "bbc.mobile.news.ww:id/personalisationListView"),
		@AndroidFindBy(className = "android.widget.LinearLayout"),
		@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.CheckBox\")")})
	private List<WebElement> checkBoxes;
	
	@AndroidFindBy(id = "bbc.mobile.news.ww:id/personlisationOkButton")
	private WebElement okButton;
	
	
	
	protected TopicList(Handle context) {
		super(context);
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
