package com.github.arachnidium.core.fluenthandle;

import java.util.List;
/**
 * This interface is implemented by classes which execute 
 * fluent waiting for window handles or contexts
 */
public interface IFluentHandleWaiting {
	/**
	 * It gets some handle by its index in the set
	 * @param expected index
	 * @return String handle value
	 */
	IFunctionalExpectedCondition getHandle(int index);
	
	/**
	 * It gets some handle by its expected string identifier. 
	 * @param identifier Is an expected title of a browser page or
	 * context name (NATIVE_APP, WEBWIEV etc.) 
	 * @return String handle value
	 */
	IFunctionalExpectedCondition getHandle(String identifier);
	
	/**
	 * It gets some handle by its string identifier (title 
	 * or context name) and handle index in the set. 
	 * @param index
	 * @param identifier Is an expected title of a browser page or
	 * context name (NATIVE_APP, WEBWIEV etc.) 
	 * @return String handle value
	 */
	IFunctionalExpectedCondition getHandle(int index, String identifier);	
	
	/**
	 * It gets some handle by its possible unique identifiers
	 * @param uniqueIdentifiers - URLs of the browser pages, Android activities, etc.
	 * @return String handle value
	 */
	IFunctionalExpectedCondition getHandle(List<String> uniqueIdentifiers);
	
	/**
	 * It gets some handle by its possible unique identifiers and handle index in the set.
	 * @param index
	 * @param uniqueIdentifiers - URLs of the browser pages, Android activities, etc.
	 * @return String handle value
	 */
	IFunctionalExpectedCondition getHandle(int index, List<String> uniqueIdentifiers);	
	
	/**
	 * It gets some handle by its possible unique identifiers and 
	 * its string identifier (title 
	 * or context name)
	 * @param identifier Title of a browser page or
	 * context name (NATIVE_APP, WEBWIEV ect)  
	 * @param uniqueIdentifiers - URLs of the browser pages, Android activities, etc.
	 * @return String handle value
	 */
	IFunctionalExpectedCondition getHandle(String identifier, List<String> uniqueIdentifiers);	
	
	/**
	 * It gets some handle by its index in the set, 
	 * string identifier (title or context name) and
	 * possible unique identifiers (e.g. URLs and activities)
	 * 
	 * @param expected index
	 * @param identifier Title of a browser page or
	 * context name (NATIVE_APP, WEBWIEV ect)  
	 * @param uniqueIdentifiers - URLs of the browser pages, Android activities, etc.
	 * @return String handle value
	 */
	IFunctionalExpectedCondition getHandle(int index, String identifier, List<String> uniqueIdentifiers);	
}
