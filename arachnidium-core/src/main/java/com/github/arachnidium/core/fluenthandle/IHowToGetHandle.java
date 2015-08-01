package com.github.arachnidium.core.fluenthandle;

import java.util.List;

/**
 * Returns an {@link ExpectedCondition} 
 * by {@link IFluentHandleWaiting}
 */
public interface IHowToGetHandle {
	/**
	 * @param It is an expected index in the set 
	 * of present windows/contexts 
	 */
	public void setExpected(int index);
	
	/**
	 * @param It is an expected window title or
	 * context name
	 */
	public void setExpected(String stringIdentifier);
	
	/**
	 * @param It is an expected page URLs or Android activities,
	 */	
	public void setExpected(List<String> uniqueIdentifiers);
	
	/**
	 * @param fluentHandleWaiting where given parameter are set up
	 * @return {@link ExpectedCondition} which waits for some window/context
	 */
	public IFunctionalHandleCondition getExpectedCondition(IFluentHandleWaiting fluentHandleWaiting);
}
