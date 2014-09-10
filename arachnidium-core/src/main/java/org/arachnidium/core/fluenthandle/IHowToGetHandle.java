package org.arachnidium.core.fluenthandle;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Returns an {@link ExpectedCondition} 
 * using {@link IFluentHandleWaiting}
 */
public interface IHowToGetHandle {
	/**
	 * @param It is an expected index in the set 
	 * of active windows/contexts 
	 */
	public void setExpected(int index);
	
	/**
	 * @param It is an expected window title or
	 * context name
	 */
	public void setExpected(String stringIdentifier);
	
	/**
	 * @param It is an expected page URLs or Android activities,
	 * for example
	 */	
	public void setExpected(List<String> uniqueIdentifiers);
	
	/**
	 * @param fluentHandleWaiting where given parameter are set up
	 * @return {@link ExpectedCondition} which waits for some window/contexts
	 */
	public ExpectedCondition<String> getExpectedCondition(IFluentHandleWaiting fluentHandleWaiting);
}
