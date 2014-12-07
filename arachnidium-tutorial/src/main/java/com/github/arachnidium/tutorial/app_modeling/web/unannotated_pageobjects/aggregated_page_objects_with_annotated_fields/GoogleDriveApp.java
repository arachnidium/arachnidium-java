package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.aggregated_page_objects_with_annotated_fields;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;
import com.github.arachnidium.tutorial.app_modeling.web.
       unannotated_pageobjects.LoginToGoogleService;  /**<== */

/**
 * This is a demonstration how we can model default application structure and 
 * behavior. Here I use annotated classes which describe interaction
 */
public class GoogleDriveApp extends Application<Handle, IHowToGetHandle> {
   /**If there is only interaction with browser then below is a possible option*/
	//public class GoogleDriveApp extends BrowserApplication 
	
	@Static /**<== This annotation means that it is content which always/very frequently present
	and frequently used*/
	/**Presence of @Static annotation activates handlers of annotations below: */
	@ExpectedURL(regExp = "https://accounts.google.com/ServiceLogin")  /**<== Possible URLs that should be loaded can be declared by annotations*/   
	@ExpectedURL(regExp = "accounts.google.com") /**Each one @ExpectedURL means one more possible web address*/
	@DefaultPageIndex(index = 0) /**<-Also it is possible to define the default expected window/tab index*/
	public LoginToGoogleService<?> loginToGoogleService; /**It is not necessary to 
	make fields public. You can make them private and implement public getter 
	for each one. I made that just for visibility*/
	
	@Static
	@ExpectedURL(regExp = "someservice/someurl")
	@ExpectedURL(regExp = "https://drive.google.com")
	@DefaultPageIndex(index = 0) /**<-Also it is possible to define the default expected window/tab index*/
	@ExpectedPageTitle(regExp = "Google") /**and page title*/
	public GoogleDriveMainPage googleDriveMainPage;
	
	@Static
	@ExpectedURL(regExp = "//some/more/url/")
	@ExpectedURL(regExp = "//docs.google.com/document/")
	public Document document;
	
	@Static
	@ExpectedURL(regExp = "//some/more/url/")
	@ExpectedURL(regExp = "//docs.google.com/spreadsheets/") 
	public SpreadSheet spreadSheet;
	
	protected GoogleDriveApp(Handle handle) {
		super(handle);
	}

	/**If there is only interaction with browser then below is a possible constructor*/
	//protected GoogleDriveApp(BrowserWindow window) {
	//	super(window);
	//}
}
