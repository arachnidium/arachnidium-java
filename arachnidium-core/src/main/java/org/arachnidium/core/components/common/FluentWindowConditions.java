package org.arachnidium.core.components.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * @author s.tihomirov Fluent waiting for some window conditions
 */
public abstract class FluentWindowConditions extends WebdriverComponent {

	public FluentWindowConditions(WebDriver driver) {
		super(driver);
		delegate = this;
	}

	/***
	 * is here new browser window? returns handle of a new browser window that
	 * we have been waiting for specified time
	 */
	private String getNewHandle(final WebDriver from,
			final Set<String> oldHandles) {
		String newHandle = null;
		Set<String> newHandles = from.getWindowHandles();
		if (newHandles.size() > oldHandles.size()) {
			newHandles.removeAll(oldHandles);
			newHandle = (String) newHandles.toArray()[0];
			from.switchTo().window(newHandle);
			return newHandle;
		}
		return newHandle;

	}

	/***
	 * is here new browser window? returns handle of a new browser window that
	 * we have been waiting for specified time new browser window should have
	 * page that is loaded by specified URLs URLs can be defined partially as
	 * regular expressions
	 */
	private String getNewHandle(final WebDriver from,
			final Set<String> oldHandles, List<String> urls) {
		String newHandle = null;
		Set<String> newHandles = from.getWindowHandles();
		if (newHandles.size() > oldHandles.size()) {
			newHandles.removeAll(oldHandles);
			for (String handle : newHandles) {
				String currentUrl = null;
				try {
					from.switchTo().window(handle);
					currentUrl = from.getCurrentUrl();
				} catch (TimeoutException e) {
					continue;
				}

				for (String url : urls) {
					Pattern p = Pattern.compile("^[" + url + "]?(\\?.*)?");
					Matcher m = p.matcher(currentUrl);

					if (m.find()) {
						newHandle = handle;
						return newHandle;
					}
				}
			}
		}
		return newHandle;
	}

	/***
	 * is here new browser window? returns handle of a new browser window that
	 * we have been waiting for specified time new browser window should have
	 * defined title. We can specify part of a title as a regular expression
	 * **/
	private String getNewHandle(final WebDriver from,
			final Set<String> oldHandles, String title) {
		String newHandle = null;
		Set<String> newHandles = from.getWindowHandles();
		if (newHandles.size() > oldHandles.size()) {
			newHandles.removeAll(oldHandles);

			for (String handle : newHandles) {
				String winTitle = null;
				try {
					from.switchTo().window(handle);
					winTitle = from.getTitle();
				} catch (TimeoutException e) {
					continue;
				}

				Pattern p = Pattern.compile("^[" + title + "]");
				Matcher m = p.matcher(winTitle);
				if (m.find()) {
					newHandle = handle;
					return newHandle;
				}
			}
		}
		return newHandle;
	}

	// is there such window count? It it is true this method returns handle of
	// window
	private String getWindowHandleByIndex(final WebDriver from, int windowIndex) {
		Set<String> handles = from.getWindowHandles();
		if (handles.size() - 1 >= windowIndex) {
			from.switchTo().window(handles.toArray()[windowIndex].toString());
			return new ArrayList<String>(handles).get(windowIndex);
		} else
			return null;
	}

	// fluent waiting for the result. See above
	public ExpectedCondition<Boolean> isClosed(final String closingHandle) {
		return from -> isClosed(from, closingHandle);
	}

	// is browser window closed?
	private Boolean isClosed(final WebDriver from, String handle) {
		Set<String> handles;
		try {
			handles = from.getWindowHandles();
		} catch (WebDriverException e) { // if all windows are closed
			return true;
		}

		if (!handles.contains(handle))
			return true;
		else
			return null;
	}

	// fluent waiting for the result. See above
	public ExpectedCondition<String> newWindowIsAppeared() {
		return new ExpectedCondition<String>() {
			Set<String> oldHandles = driver.getWindowHandles();

			@Override
			public String apply(final WebDriver from) {
				return getNewHandle(from, oldHandles);
			}
		};
	}

	// fluent waiting of the result. See above
	public ExpectedCondition<String> newWindowIsAppeared(final List<String> urls) {
		return new ExpectedCondition<String>() {
			Set<String> oldHandles = driver.getWindowHandles();

			@Override
			public String apply(final WebDriver from) {
				return getNewHandle(from, oldHandles, urls);
			}
		};
	}

	// fluent waiting for the result. See above
	public ExpectedCondition<String> newWindowIsAppeared(final String title) {
		return new ExpectedCondition<String>() {
			Set<String> oldHandles = driver.getWindowHandles();

			@Override
			public String apply(final WebDriver from) {
				return getNewHandle(from, oldHandles, title);
			}
		};
	}

	// fluent waiting for the result. See above
	public ExpectedCondition<String> suchWindowWithIndexIsPresent(
			final int windowIndex) {
		return from -> getWindowHandleByIndex(from, windowIndex);
	}
}
