package org.arachnidium.core.eventlisteners;

import org.arachnidium.core.interfaces.IExtendedWindow;
import org.arachnidium.core.interfaces.IHasHandle;
import org.arachnidium.core.interfaces.ITakesPictureOfItSelf;
import org.arachnidium.util.configuration.interfaces.IConfigurable;
import org.arachnidium.util.logging.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

/**
 * @author s.tihomirov Implementation of @link{IWindowListener} by default
 */
@Deprecated //TODO Refactor/Remove
public class DefaultWindowListener extends DefaultHandleListener implements
		IWindowListener, IConfigurable {

	public DefaultWindowListener() {
		super();
	}

	@Override
	public void beforeIsSwitchedOn(IHasHandle handle) {
		Log.debug("Attempt to switch window on by handle " + handle.getHandle());
	}

	@Override
	public void beforeWindowIsClosed(IExtendedWindow window) {
		Log.message("Attempt to close window...");
		postWindowUrl(window);
	}

	@Override
	public void beforeWindowIsMaximized(IExtendedWindow window) {
		Log.message("Attempt to maximize window");
		postWindowUrl(window);
	}

	@Override
	public void beforeWindowIsMoved(IExtendedWindow window, Point point) {
		Log.message("Attempt to change window position. X "
				+ Integer.toString(point.getX()) + " Y "
				+ Integer.toString(point.getY()));
		postWindowUrl(window);
	}

	@Override
	public void beforeWindowIsRefreshed(IExtendedWindow window) {
		Log.message("Attempt to refresh window");
		postWindowUrl(window);
	}

	@Override
	public void beforeWindowIsResized(IExtendedWindow window,
			Dimension dimension) {
		Log.message("Attempt to change window size. New height is "
				+ Integer.toString(dimension.getHeight()) + " new width is "
				+ Integer.toString(dimension.getWidth()));
		postWindowUrl(window);
	}

	private void postWindowUrl(IExtendedWindow window) {
		Log.message("URL is " + window.getCurrentUrl());
	}

	@Override
	public void whenIsSwitchedOn(IHasHandle handle) {
		postWindowUrl((IExtendedWindow) handle);
	}

	@Override
	public void whenNewHandleIsAppeared(IHasHandle handle) {
		if (toDoScreenShotsOfANewHandle)
			((ITakesPictureOfItSelf) handle)
					.takeAPictureOfAnInfo("The new window");

	}

	@Override
	public void whenWindowIsClosed(IExtendedWindow window) {
		Log.message("Not any problem has occurred when window was closed...");
	}

	@Override
	public void whenWindowIsMaximized(IExtendedWindow window) {
		Log.message("Window has been maximized");
	}

	@Override
	public void whenWindowIsMoved(IExtendedWindow window, Point point) {
		Log.message("Window position has been changed to X "
				+ Integer.toString(point.getX()) + " Y "
				+ Integer.toString(point.getY()));
		postWindowUrl(window);
	}

	@Override
	public void whenWindowIsRefreshed(IExtendedWindow window) {
		Log.message("Current window has been refreshed");
		postWindowUrl(window);
	}

	@Override
	public void whenWindowIsResized(IExtendedWindow window, Dimension dimension) {
		Log.message("Window size has been changed! New height is "
				+ Integer.toString(dimension.getHeight()) + " new width is "
				+ Integer.toString(dimension.getWidth()));
		postWindowUrl(window);
	}

}
