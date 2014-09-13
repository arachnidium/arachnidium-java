package org.arachnidium.model.browser;

import java.net.URL;

import org.arachnidium.core.BrowserWindow;
import org.arachnidium.core.UnclosedWindowException;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.HowToGetByFrames;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * Can be used to describe a single browser page or its fragment
 * 
 * @see FunctionalPart
 */
public abstract class BrowserPage extends FunctionalPart<BrowserWindow> implements Navigation,
		Window {

	/**
	 * @see {@link FunctionalPart#FunctionalPart(FunctionalPart)}
	 */
	protected BrowserPage(FunctionalPart<BrowserWindow> parent) {
		this(parent, new HowToGetByFrames());
	}

	/**
	 * @see {@link FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)}
	 */
	protected BrowserPage(FunctionalPart<BrowserWindow> parent, HowToGetByFrames path) {
		super(parent, path);
	}

	/**
	 * @see {@link FunctionalPart#FunctionalPart(org.arachnidium.core.Handle)
	 */
	protected BrowserPage(BrowserWindow window) {
		this(window, new HowToGetByFrames());
	}

	/**
	 * @see {@link FunctionalPart#FunctionalPart(org.arachnidium.core.Handle, HowToGetByFrames))
	 */
	protected BrowserPage(BrowserWindow window, HowToGetByFrames path) {
		super(window, path);
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#back()
	 */
	@Override
	public void back() {
		((BrowserWindow) handle).back();
	}

	/**
	 * Closes browser window and destroys all page objects that are placed on it
	 * 
	 * @throws UnclosedWindowException
	 * @throws NoSuchWindowException
	 * @throws UnhandledAlertException
	 * @throws UnreachableBrowserException
	 */
	public void close() throws UnclosedWindowException, NoSuchWindowException,
	UnhandledAlertException, UnreachableBrowserException {
		try {
			((BrowserWindow) handle).close();
			destroy();
		} catch (UnclosedWindowException e) {
			throw e;
		} catch (NoSuchWindowException e) {
			destroy();
			throw e;
		} catch (UnreachableBrowserException e) {
			destroy();
			throw e;
		}
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#forward()
	 */
	@Override
	public void forward() {
		((BrowserWindow) handle).forward();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#getPosition()
	 */
	@Override
	public Point getPosition() {
		return ((BrowserWindow) handle).getPosition();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#getSize()
	 */
	@Override
	public Dimension getSize() {
		return ((BrowserWindow) handle).getSize();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#maximize()
	 */
	@Override
	public void maximize() {
		((BrowserWindow) handle).maximize();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#refresh()
	 */
	@Override
	public void refresh() {
		((BrowserWindow) handle).refresh();
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#setPosition(org.openqa.selenium.Point)
	 */
	@Override
	public void setPosition(Point point) {
		((BrowserWindow) handle).setPosition(point);
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Window#setSize(org.openqa.selenium.Dimension)
	 */
	@Override
	public void setSize(Dimension size) {
		((BrowserWindow) handle).setSize(size);
	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.lang.String)
	 */
	@Override
	public void to(String link) {
		((BrowserWindow) handle).to(link);

	}

	/**
	 * @see org.openqa.selenium.WebDriver.Navigation#to(java.net.URL)
	 */
	@Override
	public void to(URL url) {
		((BrowserWindow) handle).to(url);
	}

}
