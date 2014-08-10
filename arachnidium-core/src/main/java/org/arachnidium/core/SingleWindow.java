package org.arachnidium.core;

import java.net.URL;

import org.arachnidium.core.components.common.NavigationTool;
import org.arachnidium.core.components.common.WindowTool;
import org.arachnidium.core.interfaces.IExtendedWindow;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * @author s.tihomirov It is performs actions on a single window
 */
public class SingleWindow extends Handle implements Navigation,
		IExtendedWindow {
	private final WindowTool windowTool;
	private final NavigationTool navigationTool;

	SingleWindow(String handle, WindowManager windowManager) {
		super(handle, windowManager);
		this.windowTool = driverEncapsulation.getComponent(WindowTool.class);
		this.navigationTool = driverEncapsulation.getComponent(NavigationTool.class);
	}

	@Override
	public synchronized void back() {
		switchToMe();
		navigationTool.back();
	}

	@Override
	public synchronized void close() throws UnclosedWindowException,
	NoSuchWindowException, UnhandledAlertException,
	UnreachableBrowserException {
		try {
			((WindowManager) nativeManager).close(handle);
			destroy();
		} catch (UnhandledAlertException | UnclosedWindowException e) {
			throw e;
		} catch (NoSuchWindowException e) {
			destroy();
			throw e;
		}
	}

	@Override
	public synchronized void forward() {
		switchToMe();
		navigationTool.forward();
	}

	@Override
	public synchronized String getCurrentUrl() throws NoSuchWindowException {
		return ((WindowManager) nativeManager).getWindowURLbyHandle(handle);
	}

	@Override
	public synchronized Point getPosition() {
		switchToMe();
		return windowTool.getPosition();
	}

	@Override
	public synchronized Dimension getSize() {
		switchToMe();
		return windowTool.getSize();
	}

	@Override
	public synchronized String getTitle() {
		return ((WindowManager) nativeManager).getTitleByHandle(handle);
	}

	@Override
	public synchronized void maximize() {
		switchToMe();
		windowTool.maximize();
	}

	@Override
	public synchronized void refresh() {
		switchToMe();
		navigationTool.refresh();
	}

	@Override
	public synchronized void setPosition(Point position) {
		switchToMe();
		windowTool.setPosition(position);
	}

	@Override
	public synchronized void setSize(Dimension size) {
		switchToMe();
		windowTool.setSize(size);
	}

	@Override
	public synchronized void to(String link) {
		switchToMe();
		navigationTool.to(link);
	}

	@Override
	public synchronized void to(URL url) {
		switchToMe();
		navigationTool.to(url);

	}
}