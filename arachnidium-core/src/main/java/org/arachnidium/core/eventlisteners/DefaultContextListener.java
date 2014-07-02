package org.arachnidium.core.eventlisteners;

import org.arachnidium.core.interfaces.IHasActivity;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import org.arachnidium.util.logging.Log;

public class DefaultContextListener extends DefaultHandleListener implements IContextListener {
	
	public DefaultContextListener(){
		super();
	}

	@Override
	public void beforeIsSwitchedOn(IHasHandle handle) {
		Log.debug("Attempt to switch to context "+ handle.getHandle());
	}

	@Override
	public void whenNewHandleIsAppeared(IHasHandle handle) {
		String activity = String.valueOf(((IHasActivity) handle).currentActivity());
		String message = "A new context "
				+ handle.getHandle() + ". Activity is " + activity;
		if (toDoScreenShotsOfANewHandle) {
			((ITakesPictureOfItSelf) handle)
					.takeAPictureOfAnInfo(message);
			return;
		}
		Log.message(message);
	}

	@Override
	public void whenIsSwitchedOn(IHasHandle handle) {
		String activity = String.valueOf(((IHasActivity) handle).currentActivity());
		Log.message("Current context is "
				+ handle.getHandle() + ". Activity is " + activity);
	}

}
