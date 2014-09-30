package org.arachnidium.core.fluenthandle;

import io.appium.java_client.android.AndroidDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class FluentScreenWaiting implements IFluentHandleWaiting {
	
	private static String getContextWhichMatchesToContextExpression(
			String contextRegExp, String currentContext) {
		Pattern p = Pattern.compile(contextRegExp);
		Matcher m = p.matcher(currentContext);
		if (m.find()) {
			return currentContext;
		} else
			return null;
	}

	private static String getContextWhichMatchesToActivities(String context,
			List<String> activitiesRegExps, String currentActivity) {
		for (String activity : activitiesRegExps) {
			Pattern p = Pattern.compile(activity);
			Matcher m = p.matcher(currentActivity);
	
			if (m.find()) {
				return context;
			}
		}
		return null;
	}	
	
	private String getContextByIndex(final WebDriver from, int contextIndex) {
		Set<String> handles = ((ContextAware) from).getContextHandles();
		if (handles.size() - 1 >= contextIndex) {
			((ContextAware) from).context(handles.toArray()[contextIndex].toString());
			return new ArrayList<String>(handles).get(contextIndex);
		} else
			return null;
	}	
	
	private String getContextByIndexAndContextExpression(final WebDriver from,
			int contextIndex, String contextRegExp) {
		String resultHandle = getContextByIndex(from, contextIndex);
		if (resultHandle == null) {
			return null;
		}
		resultHandle =getContextWhichMatchesToContextExpression(contextRegExp,
				resultHandle);
		return resultHandle;
	}		
	
	private String getContextByExpression(final WebDriver from,
			String contextRegExp) {
		String resultHandle = null;
		ContextAware contextAware = ((ContextAware) from);
		Set<String> handles = contextAware.getContextHandles();
		for (String handle : handles) {
			resultHandle = getContextWhichMatchesToContextExpression(contextRegExp, 
					handle);
			if (resultHandle == null) {
				continue;
			}
			return resultHandle;
		}
		return resultHandle;
	}
	
	private String getContextByAcivities(final WebDriver from,
			List<String> activitiesRegExps) {
		String resultHandle = null;
		ContextAware contextAware = ((ContextAware) from);
		Set<String> handles = contextAware.getContextHandles();
		for (String handle : handles) {
			String currentActivity = ((AndroidDriver) contextAware.context(handle)).currentActivity();

			resultHandle = getContextWhichMatchesToActivities(handle, activitiesRegExps,
					currentActivity);
			if (resultHandle == null) {
				continue;
			}
			return resultHandle;
		}
		return resultHandle;
	}	
	
	private String getContextByIndexAndActivities(final WebDriver from,
			int contextIndex, List<String> activitiesRegExps) {
		String resultHandle = getContextByIndex(from, contextIndex);
		if (resultHandle == null) {
			return null;
		}
		ContextAware contextAware = ((ContextAware) from);
		String currentActivity = ((AndroidDriver) contextAware.context(resultHandle)).currentActivity();
		
		return getContextWhichMatchesToActivities(resultHandle, activitiesRegExps, currentActivity);
	}	

	private String getContextByContextExpressionAndActivities(final WebDriver from,
			List<String> activitiesRegExps, String contextRegExp) {

		String resultHandle = getContextByExpression(from, contextRegExp);
		if (resultHandle == null) {
			return null;
		}

		ContextAware contextAware = ((ContextAware) from);
		String currentActivity = ((AndroidDriver) contextAware.context(resultHandle)).currentActivity();

		return getContextWhichMatchesToActivities(resultHandle, activitiesRegExps,
				currentActivity);
	}	
	
	private String getContextByAllConditions(final WebDriver from,
			int contextIndex, List<String> activitiesRegExps, String contextRegExp) {
		String resultHandle = getContextByIndex(from, contextIndex);
		if (resultHandle == null) {
			return null;
		}

		ContextAware contextAware = ((ContextAware) from);
		String currentActivity = ((AndroidDriver) contextAware.context(resultHandle)).currentActivity();

		resultHandle = getContextWhichMatchesToContextExpression(contextRegExp,
				resultHandle);
		if (resultHandle == null) {
			return null;
		}
		return getContextWhichMatchesToActivities(resultHandle, activitiesRegExps,
				currentActivity);
	}	
	
	/**
	 * returns context that we have been waiting for
	 * specified time. The context is defined by index
	 * 
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int)
	 */
	@Override
	public ExpectedCondition<String> getHandle(int index) {
		return from -> getContextByIndex(from, index);
	}

	
	/**
     * returns context that we have been waiting for
	 * specified time.
	 * 
	 * The context should have defined name. We can specify part of a
	 * context name as a regular expression
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.lang.String)
	 */	
	@Override
	public ExpectedCondition<String> getHandle(String contextRegExp) {
		return from -> getContextByExpression(from, contextRegExp);
	}

	/**
	 * returns context that we have been waiting for
	 * specified time. The context is defined by index
	 * 
	 * The context should have defined name. We can specify part of a
	 * context name as a regular expression
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.lang.String)
	 */
	@Override
	public ExpectedCondition<String> getHandle(int index, String contextRegExp) {
		return from -> getContextByIndexAndContextExpression(from, index, contextRegExp);
	}

	/**
	 returns context that we have been waiting for
	 * specified time.
	 * 
     * There should be activity 
     * from the given list. Activities can be defined partially as regular expressions
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.util.List)
	 */	
	@Override
	public ExpectedCondition<String> getHandle(List<String> activitiesRegExps) {
		return from -> getContextByAcivities(from, activitiesRegExps);
	}

	/**
	 * returns context that we have been waiting for
	 * specified time. The context is defined by index.
	 * 
     * There should be activity 
     * from the given list. Activities can be defined partially as regular expressions
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.util.List)
	 */
	@Override
	public ExpectedCondition<String> getHandle(int index,
			List<String> activitiesRegExps) {
		return from -> getContextByIndexAndActivities(from, index, activitiesRegExps);
	}

	/**
	 * returns context that we have been waiting for
	 * specified time.
	 * 
	 * The context should have defined name. We can specify part of a
	 * context name as a regular expression
	 * 
     * There should be activity 
     * from the given list. Activities can be defined partially as regular expressions
	 *
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public ExpectedCondition<String> getHandle(String contextRegExp,
			List<String> activitiesRegExps) {
		return from -> getContextByContextExpressionAndActivities(from, activitiesRegExps, contextRegExp);
	}

	/**
     * returns context that we have been waiting for
	 * specified time. The context is defined by index.
	 * 
	 * The context should have defined name. We can specify part of a
	 * context name as a regular expression.
	 *    
	 * There should be activity 
     * from the given list. Activities can be defined partially as regular expressions
	 * 
	 * @see org.arachnidium.core.fluenthandle.IFluentHandleWaiting#getHandle(int,
	 *      java.lang.String, java.util.List)
	 */	
	@Override
	public ExpectedCondition<String> getHandle(int index, String contextRegExp,
			List<String> activitiesRegExps) {
		return from -> getContextByAllConditions(from, index, activitiesRegExps, contextRegExp);
	}

}
