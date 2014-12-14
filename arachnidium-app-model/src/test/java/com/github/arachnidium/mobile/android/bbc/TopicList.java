package com.github.arachnidium.mobile.android.bbc;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.rootelements.RootAndroidElement;

@RootAndroidElement(chain = {
		@AndroidFindBy(id = "android:id/content")
		})
public class TopicList<T extends Handle> extends FunctionalPart<T> {
	@CacheLookup
	@AndroidFindBys({@AndroidFindBy(className = "android.widget.LinearLayout"),
		@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/feedTitle\")")})
	private List<WebElement> titles;
	@CacheLookup
	@AndroidFindBys({@AndroidFindBy(className = "android.widget.LinearLayout"),
		@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.CheckBox\")")})
	private List<WebElement> checkBoxes;
	
	@FindBy(id = "bbc.mobile.news.ww:id/personlisationOkButton")
	private WebElement okButton;
	
	
	
	protected TopicList(FunctionalPart<?> parent, HowToGetByFrames path, By by) {
		super(parent, path, by);
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
