package com.github.arachnidium.tutorial.app_modeling.mobile.native_app.annotated_pageobjects;

import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;

/**Let it be without annotations*/
public class HermitageMuseumMainScreen extends FunctionalPart<Handle> {
	/**
	 * Below is an available option if we want the interaction with
	 * only native mobile client
	 * 
	 *  ...
	 *  import com.github.arachnidium.model.mobile.NativeContent;
	 *  ....
	 * 
	 *  public class HermitagemuseumMain extends NativeContent{
	 *  ...
	 *  
	 */
	
	@FindBy(id = "some_id_for_the_history_of_the_museum") /**<== Lets imagine that there is the similar 
	browser UI.*/
	@AndroidFindBy(id = "org.hermitagemuseum:id/frg_museum_item_history") /**<==  It will 
	be used by Android*/
	private RemoteWebElement history;
	
	@FindBy(id = "some_id_for_the_tickets_purchasing") 
	@AndroidFindBy(id = "org.hermitagemuseum:id/frg_museum_item_tickets")
	private RemoteWebElement tickets;
	
	@FindBy(id = "some_id_for_the_direction") 
	@AndroidFindBy(id = "org.hermitagemuseum:id/frg_museum_item_direction")
	private RemoteWebElement direction;

	protected HermitageMuseumMainScreen(Handle handle) {
		super(handle);
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
	 *	protected HermitagemuseumMain(MobileScreen screen) {
	 *		super(screen);
	 *	}
	 *  
	 */	
	
	@InteractiveMethod
	public void clickHistory(){
		history.click();
	}
	
	@InteractiveMethod
	public void clickTickets(){
		tickets.click();
	}
	
	@InteractiveMethod
	public void clickDirection(){
		direction.click();
	}	
	
	/**Some more interesting things could be implemented below*/
	/**.................................*/
	

}
