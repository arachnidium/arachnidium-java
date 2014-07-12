package org.arachnidium.core.eventlisteners;

import org.arachnidium.core.interfaces.IHasHandle;

public interface IHandletListener {

	public void beforeIsSwitchedOn(IHasHandle handle);

	public void whenIsSwitchedOn(IHasHandle handle);

	public void whenNewHandleIsAppeared(IHasHandle handle);

}
