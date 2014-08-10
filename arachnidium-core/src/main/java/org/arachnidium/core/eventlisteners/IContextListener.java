package org.arachnidium.core.eventlisteners;

import org.arachnidium.core.interfaces.IHasHandle;
import org.openqa.selenium.ScreenOrientation;

public interface IContextListener extends IHandletListener {
	public void beforeIsRotated(IHasHandle handle, ScreenOrientation orientation);
	public void whenIsRotated(IHasHandle handle, ScreenOrientation orientation);
}
