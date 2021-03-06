package com.github.arachnidium.core.components.common;

import java.util.concurrent.TimeUnit;

import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.interfaces.IConfigurable;
import com.github.arachnidium.util.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriverException;

import com.github.arachnidium.core.components.WebdriverComponent;
import com.github.arachnidium.core.settings.WebDriverTimeOuts;

/**
 * {@link Timeouts} implementor
 * New possibilities: It can pass out given time outs values.
 */
public abstract class TimeOut extends WebdriverComponent implements Timeouts,
		IConfigurable {

	private final long defaultTimeOut = 20000; // 20 seconds

	private final TimeUnit defaultTimeUnit = TimeUnit.MILLISECONDS;
	private long implicitlyWaitTimeOut = defaultTimeOut;

	private TimeUnit implicitlyWaitTimeUnit = defaultTimeUnit;
	private long pageLoadTimeOut = defaultTimeOut;

	private TimeUnit pageLoadTimeUnit = defaultTimeUnit;
	private long scriptTimeOut = defaultTimeOut;

	private TimeUnit scriptTimeUnit = defaultTimeUnit;

	public TimeOut(WebDriver driver) {
		super(driver);
		delegate = this;
	}

	public long getImplicitlyWaitTimeOut() {
		return implicitlyWaitTimeOut;
	}

	public TimeUnit getImplicitlyWaitTimeUnit() {
		return implicitlyWaitTimeUnit;
	}

	public long getPageLoadTimeOut() {
		return pageLoadTimeOut;
	}

	public TimeUnit getPageLoadTimeUnit() {
		return pageLoadTimeUnit;
	}

	public long getScriptTimeOut() {
		return scriptTimeOut;
	}

	public TimeUnit getScriptTimeUnit() {
		return scriptTimeUnit;
	}

	private Long getTimeOutValue(Long longObjParam) {
		if (longObjParam == null)
			longObjParam = defaultTimeOut;
		return longObjParam;
	}

	@Override
	public Timeouts implicitlyWait(long timeOut, TimeUnit timeUnit) {
		boolean timeOutsAreSetWell = true;
		try {
			return driver.manage().timeouts().implicitlyWait(timeOut, timeUnit);

		} catch (WebDriverException e) {
			Log.debug("Setting of an implicitly wait timeout is not supported.");
			timeOutsAreSetWell = false;
			return null;
		} finally {
			if (timeOutsAreSetWell) {
				implicitlyWaitTimeOut = timeOut;
				implicitlyWaitTimeUnit = timeUnit;
			}
		}
	}

	@Override
	public Timeouts pageLoadTimeout(long timeOut, TimeUnit timeUnit) {
		boolean timeOutsAreSetWell = true;
		try {
			return driver.manage().timeouts()
					.pageLoadTimeout(timeOut, timeUnit);
		} catch (WebDriverException e) {
			Log.debug("Setting of a page load timeout is not supported.");
			timeOutsAreSetWell = false;
			return null;
		} finally {
			if (timeOutsAreSetWell) {
				pageLoadTimeOut = timeOut;
				pageLoadTimeUnit = timeUnit;
			}
		}
	}

	// set values of time outs according to configuration
	@Override
	public synchronized void resetAccordingTo(Configuration config) {
		TimeUnit settingTimeUnit = config.getSection(WebDriverTimeOuts.class)
				.getTimeUnit();
		if (settingTimeUnit == null)
			settingTimeUnit = defaultTimeUnit;

		Long timeOut = getTimeOutValue(config.getSection(
				WebDriverTimeOuts.class).getImplicitlyWaitTimeOut());
		implicitlyWait(timeOut, settingTimeUnit);

		timeOut = getTimeOutValue(config.getSection(WebDriverTimeOuts.class)
				.getScriptTimeOut());
		setScriptTimeout(timeOut, settingTimeUnit);

		timeOut = getTimeOutValue(config.getSection(WebDriverTimeOuts.class)
				.getLoadTimeout());
		pageLoadTimeout(timeOut, settingTimeUnit);
	}

	@Override
	public Timeouts setScriptTimeout(long timeOut, TimeUnit timeUnit) {
		boolean timeOutsAreSetWell = true;
		try {
			return driver.manage().timeouts()
					.setScriptTimeout(timeOut, timeUnit);
		} catch (WebDriverException e) {
			Log.debug("Setting of a script execution timeout is not supported.");
			timeOutsAreSetWell = false;
			return null;
		} finally {
			if (timeOutsAreSetWell) {
				scriptTimeOut = timeOut;
				scriptTimeUnit = timeUnit;
			}
		}
	}

}
