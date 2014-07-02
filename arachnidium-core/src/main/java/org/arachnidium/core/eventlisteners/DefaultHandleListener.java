package org.arachnidium.core.eventlisteners;

import org.arachnidium.core.webdriversettings.ScreenShots;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IConfigurable;


public abstract class DefaultHandleListener implements IHandletListener,
		IConfigurable {

	boolean toDoScreenShotsOfANewHandle = true;

	@Override
	public void resetAccordingTo(Configuration config) {
		Boolean toDoScreenshots = config.getSection(ScreenShots.class).getToDoScreenShotsOfNewHandles();
		if (toDoScreenshots!=null)
		{
			toDoScreenShotsOfANewHandle = toDoScreenshots;
		}
	}

}
