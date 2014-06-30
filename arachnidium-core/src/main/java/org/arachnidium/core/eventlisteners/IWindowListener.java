package org.arachnidium.core.eventlisteners;

import org.arachnidium.core.interfaces.IExtendedWindow;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

/**
* @author s.tihomirov
*Objects of classes that will implement this listens to window events
*/
public interface IWindowListener extends IHandletListener {
	public void beforeWindowIsClosed(IExtendedWindow window);
	public void whenWindowIsClosed(IExtendedWindow window);
	
	public void beforeWindowIsMaximized(IExtendedWindow window);
	public void whenWindowIsMaximized(IExtendedWindow window);
	
	public void beforeWindowIsRefreshed(IExtendedWindow window);
	public void whenWindowIsRefreshed(IExtendedWindow window);
	
	public void beforeWindowIsMoved(IExtendedWindow window, Point point);
	public void whenWindowIsMoved(IExtendedWindow window, Point point);
	
	public void beforeWindowIsResized(IExtendedWindow window, Dimension dimension);
	public void whenWindowIsResized(IExtendedWindow window, Dimension dimension);	
}
