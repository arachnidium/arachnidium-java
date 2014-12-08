package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.unannotated_pageobjects;

import java.util.List;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;

public class InformationAboutTickets extends InformationAboutTheHermitage {

	@FindBy(id = "some_entry_price_label_id")/**<== Lets imagine that there is the similar 
	browser UI.*/
	@AndroidFindBy(accessibility = "Entry ticket") /**<==  It will 
	be used by Android*/
	private RemoteWebElement entry_ticket_label;
	
	@FindBy(id = "some_element_id")
	@AndroidFindBys({
		@AndroidFindBy(className = "android.widget.GridView"),
		@AndroidFindBy(className = "android.view.View"),
		@AndroidFindBy(className = "android.view.View"),
		@AndroidFindBy(className = "android.view.View")
		})
	private List<RemoteWebElement> views;
	
	
	protected InformationAboutTickets(Handle handle, By by) {
		super(handle, by);
	}
	
	@InteractiveMethod
	public boolean isEntryTicketLabelVisible(){
		return entry_ticket_label.isDisplayed();
	}
	
	@InteractiveMethod
	public String getEntryPrice(){
		return views.get(1).getText();
	}
	
	/**Some more interesting things could be implemented below*/
	/**.................................*/	
}
