package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.aggregated_page_objects_with_annotated_fields;

import com.github.arachnidium.core.BrowserWindow;


/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class Document extends AnyDocument {

	protected Document(BrowserWindow window) {
		super(window);
	}
	
	//Specific actions which perform the interaction with the text editor could be implemented here
	//.......

}
