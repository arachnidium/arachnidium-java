package org.arachnidium.core.beans;

import org.arachnidium.core.eventlisteners.IContextListener;
import org.arachnidium.core.interfaces.IHasHandle;
import org.aspectj.lang.annotation.Aspect;

@Aspect
class DefaultContextListener implements IContextListener {

	@Override
	public void beforeIsSwitchedOn(IHasHandle handle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void whenIsSwitchedOn(IHasHandle handle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void whenNewHandleIsAppeared(IHasHandle handle) {
		// TODO Auto-generated method stub

	}

}
