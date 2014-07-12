package org.arachnidium.core.webdriversettings.supported;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.webdriverservices.EServices;
import org.arachnidium.core.webdriverservices.RemoteSeleniumServerLauncher;
import org.arachnidium.core.webdriverservices.interfaces.ILocalServerLauncher;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.opera.core.systems.OperaDriver;

public enum ESupportedDrivers {
	FIREFOX(DesiredCapabilities.firefox(), FirefoxDriver.class, null, null,
			false, false), CHROME(DesiredCapabilities.chrome(),
			ChromeDriver.class, EServices.CHROMESERVICE, null, false, false), INTERNETEXPLORER(
			DesiredCapabilities.internetExplorer(),
			InternetExplorerDriver.class, EServices.IEXPLORERSERVICE, null,
			false, false), SAFARI(DesiredCapabilities.safari(),
			SafariDriver.class, null, null, false, false), OPERA(
			DesiredCapabilities.opera(), OperaDriver.class, null, null, false,
			false), HTMLUNIT(DesiredCapabilities.htmlUnitWithJs(),
			HtmlUnitDriver.class, null, null, false, false), PHANTOMJS(
			DesiredCapabilities.phantomjs(), PhantomJSDriver.class,
			EServices.PHANTOMJSSERVICE, null, false, false), REMOTE(
			DesiredCapabilities.firefox(), RemoteWebDriver.class, null,
			new RemoteSeleniumServerLauncher(), true, false) {
		@Override
		public void setSystemProperty(Configuration configInstance,
				Capabilities capabilities) {
			String brofserName = capabilities.getBrowserName();

			if (DesiredCapabilities.chrome().getBrowserName()
					.equals(brofserName))
				CHROME.setSystemProperty(configInstance);
			if (DesiredCapabilities.internetExplorer().getBrowserName()
					.equals(brofserName))
				INTERNETEXPLORER.setSystemProperty(configInstance);
			if (DesiredCapabilities.phantomjs().getBrowserName()
					.equals(brofserName))
				PHANTOMJS.setSystemProperty(configInstance);
		}
	},
	MOBILE(new DesiredCapabilities(), AppiumDriver.class, null, null, true,
			true);

	public static ESupportedDrivers parse(String original) {
		String parcingStr = original.toUpperCase().trim();

		ESupportedDrivers[] values = ESupportedDrivers.values();
		for (ESupportedDrivers enumElem : values)
			if (parcingStr.equals(enumElem.toString()))
				return enumElem;
		throw new IllegalArgumentException("Webdriver with specified name "
				+ original + " is not supported");
	}

	private Capabilities capabilities;
	private Class<? extends WebDriver> driverClazz;
	private EServices service;
	final ILocalServerLauncher serverLauncher;
	private final boolean startsRemotely;

	private final boolean requiresRemoteURL;

	private ESupportedDrivers(Capabilities capabilities,
			Class<? extends WebDriver> driverClazz, EServices sevice,
			ILocalServerLauncher serverLauncher, boolean startsRemotely,
			boolean requiresRemoteURL) {
		this.capabilities = capabilities;
		this.driverClazz = driverClazz;
		this.service = sevice;
		this.serverLauncher = serverLauncher;
		this.startsRemotely = startsRemotely;
		this.requiresRemoteURL = requiresRemoteURL;
	}

	public Capabilities getDefaultCapabilities() {
		return capabilities;
	}

	public Class<? extends WebDriver> getUsingWebDriverClass() {
		return driverClazz;
	}

	public synchronized void launchRemoteServerLocallyIfWasDefined(
			Configuration configuration) {
		if (serverLauncher == null)
			return;
		if (serverLauncher.isLaunched())
			return;
		try {
			serverLauncher.resetAccordingTo(configuration);
			serverLauncher.launch();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean requiresRemoteURL() {
		return requiresRemoteURL;
	}

	private void setSystemProperty(Configuration configInstance) {
		if (service != null)
			this.service.setSystemProperty(configInstance);
	}

	/**
	 * It is useful for {@link RemoteWebDriver} instantiation. Local services
	 * depend on capabilities
	 */
	public void setSystemProperty(Configuration configInstance,
			Capabilities ignored) {
		setSystemProperty(configInstance);
	}

	public boolean startsRemotely() {
		return startsRemotely;
	}
}
