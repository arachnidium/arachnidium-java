package com.github.arachnidium.core.eventlisteners;

import org.openqa.selenium.ScreenOrientation;

import com.github.arachnidium.core.interfaces.IHasHandle;

/**
 * Implementors of this interface will listen to the
 * context/mobile screen events
 */
public interface IContextListener extends IHandletListener {
	/**
	 * It will be invoked before context/mobile screen is rotated
	 * 
	 * @param handle Context/mobile screen which has to be rotated
	 * @param orientation New screen orientation 
	 */
	public void beforeIsRotated(IHasHandle handle, ScreenOrientation orientation);
	
	/**
	 * It will be invoked when context/mobile screen is rotated
	 * 
	 * @param handle Context/mobile screen which has been rotated
	 * @param orientation Current screen orientation 
	 */
	public void whenIsRotated(IHasHandle handle, ScreenOrientation orientation);
}
