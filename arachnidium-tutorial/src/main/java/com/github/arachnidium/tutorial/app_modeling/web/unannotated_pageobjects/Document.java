package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects;

import com.github.arachnidium.core.BrowserWindow;

/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class Document extends AnyDocument {

	/**
	 * If it is implemented as something general
	 * (general page/screen description) then it 
	 * should have constructor like this:
	 * 
	 * {@link FunctionalPart##FunctionalPart(Handle, com.github.arachnidium.model.support.HowToGetByFrames, org.openqa.selenium.By)}
	 */	
	protected Document(BrowserWindow window) {
		super(window);
	}
	
	//Specific actions which perform the interaction with the text editor could be implemented here
	//.......

}
