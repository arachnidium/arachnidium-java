package com.github.arachnidium.core.fluenthandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

/**
 * Fluent waiting for browser window handle
 * 
 * @see IFluentHandleWaiting
 */
public class FluentPageWaiting implements IFluentHandleWaiting {

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

	private Function<WebDriver, String> getWindowHandleByTitleAndURLs(final WebDriver from,
			List<String> urlsRegExps, String titleRegExp) {

		return getHandle(titleRegExp).andThen(input -> 
		{
			if (input == null)
				return null;
			
			String currentUrl = null;
			try {
				from.switchTo().window(input);
				currentUrl = from.getCurrentUrl();
			} catch (TimeoutException e) {
				return null;
			}

			return getHandleWhichMatchesToURLs(input, urlsRegExps,
					currentUrl);
			
		});		
	}

	private Function<WebDriver, String> getWindowHandleByIndexAndTitle(final WebDriver from,
			int windowIndex, String titleRegExp) {
		
		return getHandle(windowIndex).andThen(input -> {
			if (input == null)
				return null;
			
			String winTitle = null;
			try {
				from.switchTo().window(input);
				winTitle = from.getTitle();
			} catch (TimeoutException e) {
				return null;
			}

			return getHandleWhichMatchesToTitles(input, titleRegExp,
					winTitle);			
		});		
	}

	private Function<WebDriver, String> getWindowHandleByAllConditions(final WebDriver from,
			int windowIndex, List<String> urlsRegExps, String titleRegExp) {
		 return getWindowHandleByIndexAndTitle(from,
				windowIndex, titleRegExp).andThen(input -> {
					if (input == null)
						return null;
					
					String currentUrl = null;
					try {
						from.switchTo().window(input);
						currentUrl = from.getCurrentUrl();
					} catch (TimeoutException e) {
						return null;
					}
					return getHandleWhichMatchesToURLs(input, urlsRegExps,
							currentUrl);					
				});

	}

	private Function<WebDriver, String> getWindowHandleByIndexAndURLs(final WebDriver from,
			int windowIndex, List<String> urlsRegExps) {		
		return getHandle(windowIndex).andThen(input -> {
			if (input == null)
				return null;
			
			String currentUrl = null;
			try {
				from.switchTo().window(input);
				currentUrl = from.getCurrentUrl();
			} catch (TimeoutException e) {
				return null;
			}
			return getHandleWhichMatchesToURLs(input, urlsRegExps,
					currentUrl);			
		});
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. The window is defined by index
	 * 
	 * @see com.github.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int)
	 */
	@Override
	public IFunctionalHandleCondition getHandle(final int index) {
		return  from -> {
			Set<String> handles = from.getWindowHandles();
			if (handles.size() - 1 >= index) {
				from.switchTo().window(handles.toArray()[index].toString());
				return new ArrayList<String>(handles).get(index);
			} else
				return null;
		};
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. 
	 * 
	 * The browser window should have defined title. We can
	 * specify part of a title as a regular expression
	 * 
	 * @see com.github.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.lang.String)
	 */
	@Override
	public IFunctionalHandleCondition getHandle(String titleRegExp) {
		return from -> {
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
		};
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. 
	 * 
	 * Browser window should have page that is loaded at
	 * specified URLs. Each URL can be defined partially as regular expression
	 * 
	 * @see com.github.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.util.List)
	 */
	@Override
	public IFunctionalHandleCondition getHandle(List<String> urlsRegExps) {
		return  from -> {
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
		};
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. 
	 * 
	 * The browser window should have defined title. We can
	 * specify part of a title as a regular expression.
	 * 
	 * Browser window should have page that is loaded at
	 * specified URLs. Each URL can be defined partially as regular expression.
	 *
	 * @see com.github.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public IFunctionalHandleCondition getHandle(String titleRegExp,
			List<String> urlsRegExps) {
		return from -> getWindowHandleByTitleAndURLs(from, urlsRegExps,
				titleRegExp).apply(from);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. The window is defined by index.
	 * 
	 * The browser window should have defined title. We can
	 * specify part of a title as a regular expression.
	 * 
	 * Browser window should have page that is loaded at
	 * specified URLs. Each URL can be defined partially as regular expression
	 * 
	 * @see com.github.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	public IFunctionalHandleCondition getHandle(int index, String titleRegExp,
			List<String> urlsRegExps) {
		return from -> getWindowHandleByAllConditions(from, index, urlsRegExps,
				titleRegExp).apply(from);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. The window is defined by index.
	 * 
	 * The browser window should have defined title. We can specify part of a
	 * title as a regular expression.
	 * 
	 * @see com.github.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.lang.String)
	 */
	@Override
	public IFunctionalHandleCondition getHandle(int index, String titleRegExp) {
		return from -> getWindowHandleByIndexAndTitle(from, index, titleRegExp).apply(from);
	}

	/**
	 * returns handle of a browser window that we have been waiting for
	 * specified time. The window is defined by index.
	 * 
	 * Browser window should have page that is loaded at
	 * specified URLs. Each URL can be defined partially as regular expression.
	 * 
	 * @see com.github.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.util.List)
	 */
	@Override
	public IFunctionalHandleCondition getHandle(int index,
			List<String> urlsRegExps) {
		return from -> getWindowHandleByIndexAndURLs(from, index, urlsRegExps).apply(from);
	}

}
