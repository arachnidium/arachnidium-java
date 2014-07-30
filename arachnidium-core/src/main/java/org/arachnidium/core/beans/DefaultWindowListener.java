package org.arachnidium.core.beans;

import org.arachnidium.core.eventlisteners.IWindowListener;
import org.arachnidium.core.interfaces.IExtendedWindow;
import org.arachnidium.core.interfaces.IHasHandle;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

@Aspect
class DefaultWindowListener implements IWindowListener {

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

	@Override
	public void beforeWindowIsClosed(IExtendedWindow window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeWindowIsMaximized(IExtendedWindow window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeWindowIsMoved(IExtendedWindow window, Point point) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeWindowIsRefreshed(IExtendedWindow window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeWindowIsResized(IExtendedWindow window,
			Dimension dimension) {
		// TODO Auto-generated method stub

	}

	@Override
	public void whenWindowIsClosed(IExtendedWindow window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void whenWindowIsMaximized(IExtendedWindow window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void whenWindowIsMoved(IExtendedWindow window, Point point) {
		// TODO Auto-generated method stub

	}

	@Override
	public void whenWindowIsRefreshed(IExtendedWindow window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void whenWindowIsResized(IExtendedWindow window, Dimension dimension) {
		// TODO Auto-generated method stub

	}

}
