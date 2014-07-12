package org.arachnidium.core.interfaces;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver.Timeouts;

/**
 * An interface for implementors of {@link Timeouts}
 *
 */
public interface ITimeOutsGetter {
	public long getImplicitlyWaitTimeOut();

	public TimeUnit getImplicitlyWaitTimeUnit();

	public long getPageLoadTimeOut();

	public TimeUnit getPageLoadTimeUnit();

	public long getScriptTimeOut();

	public TimeUnit getScriptTimeUnit();

}
