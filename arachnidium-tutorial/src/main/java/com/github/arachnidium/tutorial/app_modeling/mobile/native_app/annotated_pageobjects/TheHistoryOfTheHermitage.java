package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.annotated_pageobjects;

import java.util.List;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;

public class TheHistoryOfTheHermitage extends InformationAboutTheHermitage{
	
	@FindBy(id = "some_image_id")/**<== Lets imagine that there is the similar 
	browser UI.*/
	@AndroidFindBy(className = "android.widget.Image") /**<==  It will 
	be used by Android*/
	private List<RemoteWebElement> pictures;
	
	@FindBy(id = "some_paragraph_id")
	@AndroidFindBy(className = "android.view.View")
	private List<RemoteWebElement> paragraphs;

	protected TheHistoryOfTheHermitage(Handle handle, By by) {
		super(handle, by);
	}
	
	@InteractiveMethod
	public int getPictiresCount(){
		return pictures.size();
	}
	
	@InteractiveMethod
	public int getParagraphCount(){
		return paragraphs.size();
	}
	
	/**Some more interesting things could be implemented below*/
	/**.................................*/
}
