package org.arachnidium.core.eventlisteners;

import org.arachnidium.core.interfaces.IExtendedWindow;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

/**
 * Implementors of this interface will listen to the
 * {@link IExtendedWindow} events
 */
public interface IWindowListener extends IHandletListener {
	
	/**
	 * It will be invoked before browser window is closed
	 * @param window Browser window which has to be closed
	 */
	public void beforeWindowIsClosed(IExtendedWindow window);

	/**
	 * It will be invoked before browser window is maximized
	 * @param window Browser window which has to be maximized
	 */
	public void beforeWindowIsMaximized(IExtendedWindow window);

	/**
	 * It will be invoked before browser window is moved.
	 * 
	 * @param window Browser window which has to be moved
	 * @param point A new point where window will be moved to.
	 */
	public void beforeWindowIsMoved(IExtendedWindow window, Point point);

	/**
	 * It will be invoked before browser window is refreshed.
	 * 
	 * @param window Browser window which has to be refreshed.
	 */
	public void beforeWindowIsRefreshed(IExtendedWindow window);

	/**
     * It will be invoked before browser window is resized.
	 * 
	 * @param window Browser window which has to be resized.
	 * @param dimension New size
	 */
	public void beforeWindowIsResized(IExtendedWindow window,
			Dimension dimension);

	/**
	 * It will be invoked when browser window is closed
	 * @param window Browser window which has been closed
	 */	
	public void whenWindowIsClosed(IExtendedWindow window);

	/**
	 * It will be invoked when browser window is maximized
	 * @param window Browser window which has been maximized
	 */
	public void whenWindowIsMaximized(IExtendedWindow window);

	/**
	 * It will be invoked when browser window is moved.
	 * 
	 * @param window Browser window which has been moved
	 * @param point A new point where window has been moved to.
	 */	
	public void whenWindowIsMoved(IExtendedWindow window, Point point);

	/**
	 * It will be invoked when browser window is refreshed.
	 * 
	 * @param window Browser window which has been refreshed.
	 */	
	public void whenWindowIsRefreshed(IExtendedWindow window);

	/**
     * It will be invoked when browser window is resized.
	 * 
	 * @param window Browser window which has been resized.
	 * @param dimension Current window size
	 */	
	public void whenWindowIsResized(IExtendedWindow window, Dimension dimension);
}
