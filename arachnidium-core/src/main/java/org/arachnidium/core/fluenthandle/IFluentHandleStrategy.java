package org.arachnidium.core.fluenthandle;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Returns an {@link ExpectedCondition} 
 * using {@link IFluentHandleWaiting}
 */
public interface IFluentHandleStrategy {
	
	public void setExpected(int index);
	
	public void setExpected(String stringIdentifier);
	
	public void setExpected(List<String> uniqueIdentifiers);
	
	public ExpectedCondition<String> getExpectedCondition(IFluentHandleWaiting fluentHandleWaiting);
}
