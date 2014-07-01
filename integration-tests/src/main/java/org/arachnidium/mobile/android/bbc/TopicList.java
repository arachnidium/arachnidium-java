package org.arachnidium.mobile.android.bbc;

import io.appium.java_client.FindsByAndroidUIAutomator;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import  org.arachnidium.model.common.FunctionalPart;

public class TopicList extends FunctionalPart {

	@FindBy(id = "bbc.mobile.news.ww:id/personalisationListView")
	private WebElement topicList;	
	@FindBy(id = "bbc.mobile.news.ww:id/personlisationOkButton")
	private WebElement okButton;
	
	protected TopicList(FunctionalPart parent) {
		super(parent);
		load();
	}
	
	@InteractiveMethod
	public void setTopicChecked(String topicText, boolean checked) {
		List<WebElement> topics = topicList.findElements(By
				.className("android.widget.LinearLayout"));
		for (WebElement topic : topics) {
			if (((FindsByAndroidUIAutomator) topic)
					.findElementByAndroidUIAutomator(
							"new UiSelector().resourceId(\"bbc.mobile.news.ww:id/feedTitle\")")
					.getText().equals(topicText)) {
				WebElement checkBox = ((FindsByAndroidUIAutomator) topic)
						.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.CheckBox\")");
				if (!checkBox.getAttribute("checked").equals(
						String.valueOf(checked))) {
					checkBox.click();
				}
				return;
			}
		}
		throw new NoSuchElementException("There is no topic " + topicText);
	}
	
	@InteractiveMethod
	public void ok(){
		okButton.click();
	}

}
