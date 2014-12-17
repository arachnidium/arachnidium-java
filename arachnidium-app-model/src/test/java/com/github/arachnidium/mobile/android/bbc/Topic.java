package com.github.arachnidium.mobile.android.bbc;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.rootelements.RootAndroidElement;

@RootAndroidElement(chain = {@AndroidFindBy(className = "android.widget.LinearLayout")})
public class Topic extends FunctionalPart<Handle> {
	
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/feedTitle\")")
	private WebElement title;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.CheckBox\")")
	private WebElement checkBox;
	

	protected Topic(FunctionalPart<?> parent, HowToGetByFrames path, By by) {
		super(parent, path, by);
	}
	
	@InteractiveMethod
    public String getTitle(){
    	return title.getText();
    }
	
	@InteractiveMethod
    public void setChecked(boolean isChected){
		if (!checkBox.getAttribute("checked").equals(
				String.valueOf(isChected))) {
			checkBox.click();
		}
    }


}
