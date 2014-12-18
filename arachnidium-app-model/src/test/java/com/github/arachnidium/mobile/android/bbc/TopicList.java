package com.github.arachnidium.mobile.android.bbc;

import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.rootelements.RootAndroidElement;

@RootAndroidElement(chain = {
		@AndroidFindBy(id = "android:id/content")
		})
public class TopicList<T extends Handle> extends FunctionalPart<T> {
	
	@Static
	private List<Topic> topics;
	
	@FindBy(id = "bbc.mobile.news.ww:id/personlisationOkButton")
	private WebElement okButton;
	
	
	
	protected TopicList(FunctionalPart<?> parent, HowToGetByFrames path, By by) {
		super(parent, path, by);
	}
	
	@InteractiveMethod
	public void setTopicChecked(String topicText, boolean checked) {
		for (Topic topic: topics){
			if (!topic.getTitle().equals(topicText)){
				continue;
			}
			topic.setChecked(checked);
			return;
		}
		
		throw new NoSuchElementException("There is no topic " + topicText);
	}
	
	@InteractiveMethod
	public void ok(){
		okButton.click();
	}

}
