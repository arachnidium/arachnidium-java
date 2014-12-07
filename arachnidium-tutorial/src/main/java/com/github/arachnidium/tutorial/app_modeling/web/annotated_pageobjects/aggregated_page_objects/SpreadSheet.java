package com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.aggregated_page_objects;

import com.github.arachnidium.core.BrowserWindow;
import com.github.arachnidium.model.support.annotations.ExpectedURL;

@ExpectedURL(regExp = "//some/more/url/") /**<== Possible URLs that should be loaded can be declared by annotations*/   
@ExpectedURL(regExp = "//docs.google.com/spreadsheets/") /**Each one @ExpectedURL means one more possible web address*/
/**Declaration is applied to subclasses till they are annotated by @ExpectedURL with 
another values. Also if the class is going to be instantiated by {@link Application#getPart(Class, com.github.arachnidium.core.fluenthandle.IHowToGetHandle)}
(where IHowToGetHandle is a {@link HowToGetPage} instance) then 
the values contained by given strategy will be used instead of declared by annotations*/

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
