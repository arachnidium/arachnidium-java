package org.arachnidium.core.components.common;

import java.util.concurrent.TimeUnit;

import org.arachnidium.core.components.WebdriverComponent;
import org.arachnidium.core.interfaces.ITimeOutsGetter;
import org.arachnidium.core.settings.WebDriverTimeOuts;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IConfigurable;
import org.arachnidium.util.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriverException;

public abstract class TimeOut extends WebdriverComponent implements Timeouts,
		ITimeOutsGetter, IConfigurable {

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

	@Override
	public long getImplicitlyWaitTimeOut() {
		return implicitlyWaitTimeOut;
	}

	@Override
	public TimeUnit getImplicitlyWaitTimeUnit() {
		return implicitlyWaitTimeUnit;
	}

	@Override
	public long getPageLoadTimeOut() {
		return pageLoadTimeOut;
	}

	@Override
	public TimeUnit getPageLoadTimeUnit() {
		return pageLoadTimeUnit;
	}

	@Override
	public long getScriptTimeOut() {
		return scriptTimeOut;
	}

	@Override
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
