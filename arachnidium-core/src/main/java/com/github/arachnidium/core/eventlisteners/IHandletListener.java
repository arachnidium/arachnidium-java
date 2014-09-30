package com.github.arachnidium.core.eventlisteners;

import com.github.arachnidium.core.interfaces.IHasHandle;

/**
 * Implementors of this interface will listen to the
 * handle (window or mobile screen/context) events 
 */
public interface IHandletListener {

	/**
	 * It will be invoked before {@link IHasHandle} will be
	 * focused
	 * 
	 * @param handle Browser window or mobile screen/context
	 */
	public void beforeIsSwitchedOn(IHasHandle handle);
	
	/**
	 * It will be invoked when {@link IHasHandle} is
	 * focused
	 * 
	 * @param handle Browser window or mobile screen/context
	 */
	public void whenIsSwitchedOn(IHasHandle handle);

	/**
	 * It will be invoked when a new {@link IHasHandle} 
	 * appears
	 * 
	 * @param handle Browser window or mobile screen/context
	 */	
	public void whenNewHandleIsAppeared(IHasHandle handle);

}
