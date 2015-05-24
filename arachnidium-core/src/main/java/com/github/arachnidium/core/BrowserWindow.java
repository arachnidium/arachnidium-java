package com.github.arachnidium.core;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.github.arachnidium.core.components.common.NavigationTool;
import com.github.arachnidium.core.components.common.WindowTool;
import com.github.arachnidium.core.interfaces.IExtendedWindow;

/**
 * It is the representation of a browser window.
 */
public class BrowserWindow extends Handle implements Navigation,
		IExtendedWindow {
	private final WindowTool windowTool;
	private final NavigationTool navigationTool;

	BrowserWindow(String handle, WindowManager windowManager, By by, 
			HowToGetByFrames howToGetByFramesStrategy) {
		super(handle, windowManager, by, howToGetByFramesStrategy);
		this.windowTool = driverEncapsulation.getComponent(
				WindowTool.class);
		this.navigationTool = driverEncapsulation.getComponent(
				NavigationTool.class);
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#back()
	 */
	@Override
	public synchronized void back() {
		switchToMe();
		navigationTool.back();
	}

	/**
	 * @see com.github.arachnidium.core.interfaces.IExtendedWindow#close()
	 */
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

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#forward()
	 */
	@Override
	public synchronized void forward() {
		switchToMe();
		navigationTool.forward();
	}

	/**
	 * @see com.github.arachnidium.core.interfaces.IExtendedWindow#getCurrentUrl()
	 */
	@Override
	public synchronized String getCurrentUrl() throws NoSuchWindowException {
		switchToMe();
		return driverEncapsulation.getWrappedDriver().getCurrentUrl();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#getPosition()
	 */
	@Override
	public synchronized Point getPosition() {
		switchToMe();
		return windowTool.getPosition();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#getSize()
	 */
	@Override
	public synchronized Dimension getSize() {
		switchToMe();
		return windowTool.getSize();
	}

	/**
	 * @see com.github.arachnidium.core.interfaces.IExtendedWindow#getTitle()
	 */
	@Override
	public synchronized String getTitle() {
		return driverEncapsulation.getWrappedDriver().getTitle();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#maximize()
	 */
	@Override
	public synchronized void maximize() {
		switchToMe();
		windowTool.maximize();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#refresh()
	 */
	@Override
	public synchronized void refresh() {
		switchToMe();
		navigationTool.refresh();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#setPosition(org.openqa.selenium.Point)
	 */
	@Override
	public synchronized void setPosition(Point position) {
		switchToMe();
		windowTool.setPosition(position);
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#setSize(org.openqa.selenium.Dimension)
	 */
	@Override
	public synchronized void setSize(Dimension size) {
		switchToMe();
		windowTool.setSize(size);
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.lang.String)
	 */
	@Override
	public synchronized void to(String link) {
		switchToMe();
		navigationTool.to(link);
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.net.URL)
	 */
	@Override
	public synchronized void to(URL url) {
		switchToMe();
		navigationTool.to(url);

	}
}