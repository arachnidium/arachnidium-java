package org.arachnidium.model.browser;

import java.net.URL;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.core.SingleWindow;
import org.arachnidium.core.UnclosedWindowException;

/**
 * Can be used to describe a single browser page or its fragment
 */
public abstract class BrowserPage extends FunctionalPart implements Navigation, Window{

	protected BrowserPage(SingleWindow window) {
		super(window);
	}

	protected BrowserPage(FunctionalPart parent) {
		super(parent);
	}

	protected BrowserPage(SingleWindow window, Integer frameIndex) {
		super(window, frameIndex);
	}

	protected BrowserPage(FunctionalPart parent, Integer frameIndex) {
		super(parent, frameIndex);
	}

	protected BrowserPage(SingleWindow window, String pathToFrame) {
		super(window, pathToFrame);
	}

	protected BrowserPage(FunctionalPart parent, String pathToFrame) {
		super(parent, pathToFrame);
	}

	protected BrowserPage(SingleWindow window, WebElement frameElement) {
		super(window, frameElement);
	}

	protected BrowserPage(FunctionalPart parent, WebElement frameElement) {
		super(parent, frameElement);
	}
	
	/**
	 *  Closes browser window and destroys all page objects that are placed on it
	 */
	public void close() throws UnclosedWindowException, NoSuchWindowException,
			UnhandledAlertException, UnreachableBrowserException {
		try {
			((SingleWindow) handle).close();
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
	public Point getPosition() {
		return ((SingleWindow) handle).getPosition();
	}

	@Override
	public Dimension getSize() {
		return ((SingleWindow) handle).getSize();
	}

	@Override
	public void maximize() {
		((SingleWindow) handle).maximize();
	}

	@Override
	public void setPosition(Point point) {
		((SingleWindow) handle).setPosition(point);		
	}

	@Override
	public void setSize(Dimension size) {
		((SingleWindow) handle).setSize(size);		
	}

	@Override
	public void back() {
		((SingleWindow) handle).back();		
	}

	@Override
	public void forward() {
		((SingleWindow) handle).forward();		
	}

	@Override
	public void refresh() {
		((SingleWindow) handle).refresh();		
	}

	@Override
	public void to(String link) {
		((SingleWindow) handle).to(link);
		
	}

	@Override
	public void to(URL url) {
		((SingleWindow) handle).to(url);		
	}

}
