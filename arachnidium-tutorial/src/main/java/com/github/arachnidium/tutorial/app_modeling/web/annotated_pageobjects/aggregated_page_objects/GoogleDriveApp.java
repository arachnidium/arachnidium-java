package com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.aggregated_page_objects;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.common.Static;
import com.github.arachnidium.tutorial.app_modeling.web.  /**<== */
      annotated_pageobjects.LoginToGoogleService;

/**
 * This is a demonstration how we can model default application structure and 
 * behavior. Here I use annotated classes which describe interaction
 */
public class GoogleDriveApp extends Application<Handle, IHowToGetHandle> {
   /**If there is only interaction with browser then below is a possible option*/
	//public class GoogleDriveApp extends BrowserApplication 
	
	@Static /**<== This annotation means that it is content which always/very frequently present
	and frequently used*/
	public LoginToGoogleService<?> loginToGoogleService; /**It is not necessary to 
	make fields public. You can make them private and implement public getter 
	for each one. I made that just for visibility*/
	@Static
	public GoogleDriveMainPage googleDriveMainPage;
	@Static
	public Document document;
	@Static
	public SpreadSheet spreadSheet;
	
	protected GoogleDriveApp(Handle handle) {
		super(handle);
	}

	/**If there is only interaction with browser then below is a possible constructor*/
	//protected GoogleDriveApp(BrowserWindow window) {
	//	super(window);
	//}
}
