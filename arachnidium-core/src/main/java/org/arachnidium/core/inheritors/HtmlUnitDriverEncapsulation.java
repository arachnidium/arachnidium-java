package org.arachnidium.core.inheritors;


import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.core.webdriversettings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.Configuration;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * @author s.tihomirov
 * 
 */
public final class HtmlUnitDriverEncapsulation extends WebDriverEncapsulation {
	public HtmlUnitDriverEncapsulation(boolean enableJavascript) {
		super();
		createWebDriver(ESupportedDrivers.HTMLUNIT.getUsingWebDriverClass(),
				new Class<?>[] { boolean.class },
				new Object[] { enableJavascript });
	}

	public HtmlUnitDriverEncapsulation(boolean enableJavascript,
			Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.HTMLUNIT.getUsingWebDriverClass(),
				new Class<?>[] { boolean.class },
				new Object[] { enableJavascript });
	}

	public HtmlUnitDriverEncapsulation(BrowserVersion version) {
		super();
		createWebDriver(ESupportedDrivers.HTMLUNIT.getUsingWebDriverClass(),
				new Class<?>[] { BrowserVersion.class },
				new Object[] { version });
	}

	public HtmlUnitDriverEncapsulation(BrowserVersion version,
			Configuration config) {
		super();
		this.configuration = config;
		createWebDriver(ESupportedDrivers.HTMLUNIT.getUsingWebDriverClass(),
				new Class<?>[] { BrowserVersion.class },
				new Object[] { version });
	}
}
