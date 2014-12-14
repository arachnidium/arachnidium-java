package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.unannotated_pageobjects;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;

public class InformationAboutTheHermitage extends FunctionalPart<Handle> {
	
	/**
	 * Below is an available option if we want the interaction with
	 * only native mobile client
	 * 
	 *  ...
	 *  import com.github.arachnidium.model.mobile.NativeContent;
	 *  ....
	 * 
	 *  public class InformationAboutTheHermitage extends NativeContent{
	 *  ...
	 *  
	 */
	
	@FindBy(id = "some_title")/**<== Lets imagine that there is the similar 
	browser UI.*/
	@AndroidFindBy(id = "android:id/action_bar_title") /**<==  It will 
	be used by Android*/
	private RemoteWebElement title;
	
	@FindBy(id = "some_back_id")
	@AndroidFindBy(id = "android:id/up")
	private RemoteWebElement back;

	protected InformationAboutTheHermitage(Handle handle, HowToGetByFrames path, By by) {
		super(handle, path, by);
	}
	
	/**
	 * Below is an available option if we want the interaction with
	 * only native mobile client
	 * 
	 *  ...
	 *  import com.github.arachnidium.model.mobile.NativeContent;
	 *  import com.github.arachnidium.core.MobileScreen;
	 *  ....
	 * 
	 *	protected InformationAboutTheHermitage(MobileScreen screen, HowToGetByFrames ignored, By by) {
	 *		super(screen, by);
	 *	}
	 *  
	 */	
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public String getTitle(){
		return title.getText();
	}
	
	@InteractiveMethod
	public void back(){
		back.click();
	}	
	
	/**Some more interesting things could be implemented below*/
	/**.................................*/	

}
