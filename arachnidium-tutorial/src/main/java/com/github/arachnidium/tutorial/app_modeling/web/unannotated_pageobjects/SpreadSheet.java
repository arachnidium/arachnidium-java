package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects;

import com.github.arachnidium.core.BrowserWindow;

/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class SpreadSheet extends AnyDocument {

	protected SpreadSheet(BrowserWindow window) {
		super(window);
	}
	
	//Specific actions which perform the interaction with the table editor and cells 
	//could be implemented here
	//.......

}
