package org.arachnidium.core.fluenthandle;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * This interface is implemented by classes which execute 
 * fluent waiting for handles or contexts
 */
public interface IFluentHandleWaiting {
	/**
	 * gets some handle by its index in a set
	 * @param index
	 * @return String handle value
	 */
	ExpectedCondition<String> getHandle(int index);
	
	/**
	 * gets some handle by its string identifier. 
	 * @param identifier Title of a browser page or
	 * context name (NATIVE_APP, WEBWIEV ect) 
	 * @return String handle value
	 */
	ExpectedCondition<String> getHandle(String identifier);
	
	/**
	 * gets some handle by its string identifier and handle index in a set. 
	 * @param index
	 * @param identifier Title of a browser page or
	 * context name (NATIVE_APP, WEBWIEV ect) 
	 * @return String handle value
	 */
	ExpectedCondition<String> getHandle(int index, String identifier);	
	
	/**
	 * gets some handle by its possible unique identifiers
	 * @param uniqueIdentifiers - URLs of the browser pages, Android activities, etc.
	 * @return String handle value
	 */
	ExpectedCondition<String> getHandle(List<String> uniqueIdentifiers);
	
	/**
	 * gets some handle by its possible unique identifiers and handle index in a set.
	 * @param index
	 * @param uniqueIdentifiers - URLs of the browser pages, Android activities, etc.
	 * @return String handle value
	 */
	ExpectedCondition<String> getHandle(int index, List<String> uniqueIdentifiers);	
	
	/**
	 * gets some handle by its possible unique identifiers and 
	 * @param identifier Title of a browser page or
	 * context name (NATIVE_APP, WEBWIEV ect)  
	 * @param uniqueIdentifiers - URLs of the browser pages, Android activities, etc.
	 * @return String handle value
	 */
	ExpectedCondition<String> getHandle(String identifier, List<String> uniqueIdentifiers);	
	
	/**
	 * gets some handle by its possible unique identifiers
	 * @param identifier Title of a browser page or
	 * context name (NATIVE_APP, WEBWIEV ect)  
	 * @param uniqueIdentifiers - URLs of the browser pages, Android activities, etc.
	 * @return String handle value
	 */
	ExpectedCondition<String> getHandle(int index, String identifier, List<String> uniqueIdentifiers);	
}
