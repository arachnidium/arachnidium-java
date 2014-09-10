package org.arachnidium.core.fluenthandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Fluent waiting for browser window handle
 * 
 * @see IFluentHandleWaiting
 */
public class FluentWindowWaiting implements IFluentHandleWaiting {

	private String getWindowHandleByIndex(final WebDriver from, int windowIndex) {
		Set<String> handles = from.getWindowHandles();
		if (handles.size() - 1 >= windowIndex) {
			from.switchTo().window(handles.toArray()[windowIndex].toString());
			return new ArrayList<String>(handles).get(windowIndex);
		} else
			return null;
	}

	private static String getHandleWhichMatchesToTitles(String handle,
			String titleRegExp, String winTitle) {
		Pattern p = Pattern.compile(titleRegExp);
		Matcher m = p.matcher(winTitle);
		if (m.find()) {
			return handle;
		} else
			return null;
	}

	private static String getHandleWhichMatchesToURLs(String handle,
			List<String> urlsRegExps, String currentUrl) {
		for (String url : urlsRegExps) {
			Pattern p = Pattern.compile(url);
			Matcher m = p.matcher(currentUrl);

			if (m.find()) {
				return handle;
			}
		}
		return null;
	}

	private String getWindowHandleByTitle(final WebDriver from,
			String titleRegExp) {
		String resultHandle = null;
		Set<String> handles = from.getWindowHandles();
		for (String handle : handles) {
			String winTitle = null;
			try {
				from.switchTo().window(handle);
				winTitle = from.getTitle();
			} catch (TimeoutException e) {
				return null;
			}
			resultHandle = getHandleWhichMatchesToTitles(handle, titleRegExp,
					winTitle);
			if (resultHandle == null) {
				continue;
			}
			return resultHandle;
		}
		return resultHandle;
	}

	private String getWindowHandleByURLs(final WebDriver from,
			List<String> urlsRegExps) {
		String resultHandle = null;
		Set<String> handles = from.getWindowHandles();
		for (String handle : handles) {
			String currentUrl = null;
			try {
				from.switchTo().window(handle);
				currentUrl = from.getCurrentUrl();
			} catch (TimeoutException e) {
				return null;
			}

			resultHandle = getHandleWhichMatchesToURLs(handle, urlsRegExps,
					currentUrl);
			if (resultHandle == null) {
				continue;
			}
			return resultHandle;
		}
		return resultHandle;
	}

	private String getWindowHandleByTitleAndURLs(final WebDriver from,
			List<String> urlsRegExps, String titleRegExp) {

		String resultHandle = getWindowHandleByTitle(from, titleRegExp);
		if (resultHandle == null) {
			return null;
		}

		String currentUrl = null;
		try {
			from.switchTo().window(resultHandle);
			currentUrl = from.getCurrentUrl();
		} catch (TimeoutException e) {
			return null;
		}

		return getHandleWhichMatchesToURLs(resultHandle, urlsRegExps,
				currentUrl);
	}

	private String getWindowHandleByIndexAndTitle(final WebDriver from,
			int windowIndex, String titleRegExp) {
		String resultHandle = getWindowHandleByIndex(from, windowIndex);
		if (resultHandle == null) {
			return null;
		}

		String winTitle = null;
		try {
			from.switchTo().window(resultHandle);
			winTitle = from.getTitle();
		} catch (TimeoutException e) {
			return null;
		}

		return getHandleWhichMatchesToTitles(resultHandle, titleRegExp,
				winTitle);
	}

	private String getWindowHandleByAllConditions(final WebDriver from,
			int windowIndex, List<String> urlsRegExps, String titleRegExp) {
		String resultHandle = getWindowHandleByIndex(from, windowIndex);
		if (resultHandle == null) {
			return null;
		}

		String winTitle = null;
		String currentUrl = null;
		try {
			from.switchTo().window(resultHandle);
			winTitle = from.getTitle();
			currentUrl = from.getCurrentUrl();
		} catch (TimeoutException e) {
			return null;
		}

		resultHandle = getHandleWhichMatchesToTitles(resultHandle, titleRegExp,
				winTitle);
		if (resultHandle == null) {
			return null;
		}
		return getHandleWhichMatchesToURLs(resultHandle, urlsRegExps,
				currentUrl);
	}

	private String getWindowHandleByIndexAndURLs(final WebDriver from,
			int windowIndex, List<String> urlsRegExps) {
		String resultHandle = getWindowHandleByIndex(from, windowIndex);
		if (resultHandle == null) {
			return null;
		}

		String currentUrl = null;
		try {
			from.switchTo().window(resultHandle);
			currentUrl = from.getCurrentUrl();
		} catch (TimeoutException e) {
			return null;
		}
		return getHandleWhichMatchesToURLs(resultHandle, urlsRegExps,
				currentUrl);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. The window is defined by index
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int)
	 */
	@Override
	public ExpectedCondition<String> getHandle(int index) {
		return from -> getWindowHandleByIndex(from, index);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. 
	 * 
	 * The browser window should have defined title. We can
	 * specify part of a title as a regular expression
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.lang.String)
	 */
	@Override
	public ExpectedCondition<String> getHandle(String titleRegExp) {
		return from -> getWindowHandleByTitle(from, titleRegExp);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. 
	 * 
	 * Browser window should have page that is loaded at
	 * specified URLs URLs can be defined partially as regular expressions
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.util.List)
	 */
	@Override
	public ExpectedCondition<String> getHandle(List<String> urlsRegExps) {
		return from -> getWindowHandleByURLs(from, urlsRegExps);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. 
	 * 
	 * The browser window should have defined title. We can
	 * specify part of a title as a regular expression
	 * 
	 * Browser window should have page that is loaded at
	 * specified URLs URLs can be defined partially as regular expressions
	 *
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public ExpectedCondition<String> getHandle(String titleRegExp,
			List<String> urlsRegExps) {
		return from -> getWindowHandleByTitleAndURLs(from, urlsRegExps,
				titleRegExp);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. The window is defined by index
	 * 
	 * The browser window should have defined title. We can
	 * specify part of a title as a regular expression
	 * 
	 * Browser window should have page that is loaded at
	 * specified URLs URLs can be defined partially as regular expressions
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	public ExpectedCondition<String> getHandle(int index, String titleRegExp,
			List<String> urlsRegExps) {
		return from -> getWindowHandleByAllConditions(from, index, urlsRegExps,
				titleRegExp);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. The window is defined by index
	 * 
	 * The browser window should have defined title. We can specify part of a
	 * title as a regular expression
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.lang.String)
	 */
	@Override
	public ExpectedCondition<String> getHandle(int index, String titleRegExp) {
		return from -> getWindowHandleByIndexAndTitle(from, index, titleRegExp);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. The window is defined by index
	 * 
	 * Browser window should have page that is loaded at
	 * specified URLs URLs can be defined partially as regular expressions
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.util.List)
	 */
	@Override
	public ExpectedCondition<String> getHandle(int index,
			List<String> urlsRegExps) {
		return from -> getWindowHandleByIndexAndURLs(from, index, urlsRegExps);
	}

}
