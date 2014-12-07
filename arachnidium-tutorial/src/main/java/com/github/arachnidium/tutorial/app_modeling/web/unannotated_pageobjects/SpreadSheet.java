package com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects;

import com.github.arachnidium.core.BrowserWindow;

/**it is the example which demonstrates how to implement a page object
 * which is supposed to be something generalized. It can be a description of interaction
 * with the page/screen in general*/
public class SpreadSheet extends AnyDocument {

	/**
	 * If it is implemented as something general
	 * (general page/screen description) then it 
	 * should have (one of) constructors like these:
	 * 
     * {@link FunctionalPart##FunctionalPart(Handle)}
	 * {@link FunctionalPart##FunctionalPart(Handle, org.openqa.selenium.By) }
	 * {@link FunctionalPart##FunctionalPart(Handle, com.github.arachnidium.model.support.HowToGetByFrames)}
	 * {@link FunctionalPart##FunctionalPart(Handle, com.github.arachnidium.model.support.HowToGetByFrames, org.openqa.selenium.By)}
	 */	
	protected SpreadSheet(BrowserWindow window) {/**<-- it is the example which demonstrates component which can be used 
		   when only interaction with browser is needed*/
		super(window);
	}
	
	//Specific actions which perform the interaction with the table editor and cells 
	//could be implemented here
	//.......

}
