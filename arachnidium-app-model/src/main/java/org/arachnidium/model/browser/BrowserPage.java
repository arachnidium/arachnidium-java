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
 */
public abstract class BrowserPage extends FunctionalPart<BrowserWindow> implements Navigation,
		Window {

	protected BrowserPage(FunctionalPart<BrowserWindow> parent) {
		this(parent, new HowToGetByFrames());
	}

	protected BrowserPage(FunctionalPart<BrowserWindow> parent, HowToGetByFrames pathStrategy) {
		super(parent, pathStrategy);
	}

	protected BrowserPage(BrowserWindow window) {
		this(window, new HowToGetByFrames());
	}

	protected BrowserPage(BrowserWindow window, HowToGetByFrames pathStrategy) {
		super(window, pathStrategy);
	}

	@Override
	public void back() {
		((BrowserWindow) handle).back();
	}

	/**
	 * Closes browser window and destroys all page objects that are placed on it
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

	@Override
	public void forward() {
		((BrowserWindow) handle).forward();
	}

	@Override
	public Point getPosition() {
		return ((BrowserWindow) handle).getPosition();
	}

	@Override
	public Dimension getSize() {
		return ((BrowserWindow) handle).getSize();
	}

	@Override
	public void maximize() {
		((BrowserWindow) handle).maximize();
	}

	@Override
	public void refresh() {
		((BrowserWindow) handle).refresh();
	}

	@Override
	public void setPosition(Point point) {
		((BrowserWindow) handle).setPosition(point);
	}

	@Override
	public void setSize(Dimension size) {
		((BrowserWindow) handle).setSize(size);
	}

	@Override
	public void to(String link) {
		((BrowserWindow) handle).to(link);

	}

	@Override
	public void to(URL url) {
		((BrowserWindow) handle).to(url);
	}

}
