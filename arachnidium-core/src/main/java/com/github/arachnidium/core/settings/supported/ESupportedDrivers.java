package com.github.arachnidium.core.settings.supported;

import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.server.SeleniumServer;

import com.github.arachnidium.core.services.EServices;
import com.github.arachnidium.core.services.RemoteSeleniumServerLauncher;
import com.github.arachnidium.core.services.interfaces.ILocalServerLauncher;
import com.github.arachnidium.util.configuration.Configuration;

/**
 * There is information about supported {@link WebDriver} implementors <br/>
 * - {@link FirefoxDriver}<br/>
 * - {@link ChromeDriver}<br/>
 * - {@link InternetExplorerDriver}<br/>
 * - {@link SafariDriver}<br/>
 * - {@link PhantomJSDriver}<br/>
 * - {@link RemoteWebDriver}<br/>
 * - {@link AndroidDriver}<br/>
 * - {@link IOSDriver}<br/>
 * <br/>
 * - is browser only<br/>
 * - is for mobile applications only<br/>
 * Additional info:<br/>
 * - default {@link Capabilities}<br/>
 * - information about {@link DriverService}<br/>
 * - can {@link WebDriver} be started remotely?<br/>
 * - is {@link WebDriver} require URL of remotely or locally started<br/>
 * server. ({@link SeleniumServer} or Appium Node.js server (see
 * http://appium.io/slate/en/master/?ruby#appium-design)<br/>
 */
public enum ESupportedDrivers {
	/**
	 * <b>Required {@link WebDriver} implementor</b>: {@link FirefoxDriver} <br/>
	 * <br/>
	 * <b>Default {@link Capabilities}</b>: {@link DesiredCapabilities#firefox()}<br/>
	 * <br/>
	 * <b>{@link DriverService}</b>: none<br/>
	 * <br/>
	 * <b>Starts</b>: locally<br/>
	 * browser only
	 */
	FIREFOX(DesiredCapabilities.firefox(), FirefoxDriver.class, true, false, null, null,
			false, false),
	/**
	 * <b>Required {@link WebDriver} implementor</b>: {@link ChromeDriver} <br/>
	 * <br/>
	 * <b>Default {@link Capabilities}</b>: {@link DesiredCapabilities#chrome()}<br/>
	 * <br/>
	 * <b>{@link DriverService}</b>: {@link ChromeDriverService}<br/>
	 * <br/>
	 * <b>Starts</b>: locally<br/>
	 * browser only
	 */
	CHROME(DesiredCapabilities.chrome(), ChromeDriver.class, true, false,
			EServices.CHROMESERVICE, null, false, false),
	/**
	 * <b>Required {@link WebDriver} implementor</b>: {@link InternetExplorerDriver} <br/>
	 * <br/>
	 * <b>Default {@link Capabilities}</b>:
	 * {@link DesiredCapabilities#internetExplorer()}<br/>
	 * <br/>
	 * <b>{@link DriverService}</b>: {@link InternetExplorerDriverService}<br/>
	 * <br/>
	 * <b>Starts</b>: locally<br/>
	 * browser only<br/>
	 * <br/>
	 * <b>Additionally:</b> Windows only<br/>
	 */
	INTERNETEXPLORER(DesiredCapabilities.internetExplorer(),
			InternetExplorerDriver.class, true, false,  
			EServices.IEXPLORERSERVICE, null,
			false, false),
	/**
	 * <b>Required {@link WebDriver} implementor</b>: {@link SafariDriver} <br/>
	 * <br/>
	 * <b>Default {@link Capabilities}</b>: {@link DesiredCapabilities#safari()}<br/>
	 * <br/>
	 * <b>{@link DriverService}</b>: none<br/>
	 * <br/>
	 * <b>Starts</b>: locally<br/>
	 * browser only
	 */
	SAFARI(DesiredCapabilities.safari(), SafariDriver.class,
			true, false, 
			null, null, false,
			false),
	/**
	* <b>Required {@link WebDriver} implementor</b>: {@link PhantomJSDriver} <br/>
	* <br/>
	* <b>Default {@link Capabilities}</b>: {@link DesiredCapabilities#phantomjs()}<br/>
	* <br/>
	* <b>{@link DriverService}</b>: {@link PhantomJSDriverService}<br/>
	* <br/>
	* <b>Starts</b>: locally<br/>
	* browser only
	 */
	PHANTOMJS(DesiredCapabilities.phantomjs(), PhantomJSDriver.class,
			true, false, 
			EServices.PHANTOMJSSERVICE, null, false, false),
	/**
	 * <b>Required {@link WebDriver} implementor</b>: {@link AndroidDriver}<br/>
	 * <br/>
	 * <b>Default {@link Capabilities}</b>: {@link ExtendedDesiredCapabilities#androidChrome()}<br/>
	 * <br/>
	 * <b>{@link DriverService}</b>: none<br/>
	 * <br/>
	 * <b>Starts</b>: remotely. It requires {@link URL} of the host where Appium node server is started, e.g.
	 * http://127.0.0.1:4723/wd/hub (local host). Please find information here:<br/>
	 * http://appium.io/getting-started.html<br/>
	 * browser only
	 */
	ANDROID_CHROME(ExtendedDesiredCapabilities.androidChrome(), 
			AndroidDriver.class, true, false, null, null, true, true),
	/**
	 * <b>Required {@link WebDriver} implementor</b>: {@link IOSDriver}<br/>
	 * <br/>
	 * <b>Default {@link Capabilities}</b>: {@link ExtendedDesiredCapabilities#iosSafari()}<br/>
	 * <br/>
	 * <b>{@link DriverService}</b>: none<br/>
	 * <br/>
	 * <b>Starts</b>: remotely. It requires {@link URL} of the host where Appium node server is started, e.g.
	 * http://127.0.0.1:4723/wd/hub (local host). Please find information here:<br/>
	 * http://appium.io/getting-started.html<br/>
	 * browser only
	 */
	IOS_SAFARI(ExtendedDesiredCapabilities.iosSafari(), IOSDriver.class, true, false, null,
			null, true, true),
	
	 /**
	* <b>Required {@link WebDriver} implementor</b>: {@link RemoteWebDriver} <br/>
	* <br/>
	* <b>Default {@link Capabilities}</b>:
	* {@link DesiredCapabilities#firefox()()}<br/>
	* <br/>
	* <b>Starts</b>: locally and remotely. When it is started locally {@link SeleniumServer} is started it the same time.<br/>
	* browser only<br/>
	* If it needs to be launched remotely it requires {@link URL} of the host where Appium node server (if {@link ExtendedDesiredCapabilities#androidChrome()} or 
	* {@link ExtendedDesiredCapabilities#iosSafari()} are used) or 
	* {@link SeleniumServer} are started, e.g. http://127.0.0.1:4723/wd/hub (local host). 
	* Information about Appium node server please find here:<br/>
	* http://appium.io/getting-started.html<br/>
	* Information about {@link SeleniumServer}: https://code.google.com/p/selenium/wiki/Grid2<br/> 
	* <br/>
	* <b>{@link DriverService}</b>: optionally. If it is run locally with {@link DesiredCapabilities#chrome()}, {@link DesiredCapabilities#internetExplorer()}
	* or {@link DesiredCapabilities#phantomjs()} then 
	* {@link ChromeDriverService}, {@link InternetExplorerDriverService} or {@link PhantomJSDriverService} should be set up respectively<br/>
	*/
	REMOTE(DesiredCapabilities.firefox(), RemoteWebDriver.class, true, false, null,
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
	/**
	 * <b>Required {@link WebDriver} implementor</b>: {@link AndroidDriver} <br/>
	 * <br/>
	 * <b>Default {@link Capabilities}</b>: empty. They should be defined explicitly. Here is an example: <br/>	    
	 * &nbsp;&nbsp;"deviceName":{<br/>	
	 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"desired device name e.g. Android Emulator, NEXUS and so on"<br/>	    
	 * &nbsp;&nbsp;},<br/> 
	 * &nbsp;&nbsp;"app":{<br/>	
	 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"absolute path to desired *.apk file"<br/>	    
	 * &nbsp;&nbsp;}<br/>	       
	 * <br/>
	 * It is enough for the successful starting. Also see {@link MobileCapabilityType}<br/>
	 * <b>{@link DriverService}</b>: none<br/>
	 * <br/>
	 * <b>Starts</b>: remotely. It requires {@link URL} of the host where Appium node server is started, e.g.
	 * http://127.0.0.1:4723/wd/hub (local host). Please find information here:<br/>
	 * http://appium.io/getting-started.html<br/>
	 * mobile apps only
	 */
	ANDROID_APP(new DesiredCapabilities(), AndroidDriver.class, false, true, null,
			null, true, true),
			
	/**
	 * <b>Required {@link WebDriver} implementor</b>: {@link IOSDriver} <br/>
	 * <br/>
	 * <b>Default {@link Capabilities}</b>: empty. They should be defined explicitly. Here is an example: <br/>	    
	 * &nbsp;&nbsp;"platformVersion":{<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"7.1"<br/>    
	 * &nbsp;&nbsp;},<br/>
	 * &nbsp;&nbsp;"deviceName":{<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"desired device name e.g. iPhone Simulator"<br/>    
	 * &nbsp;&nbsp;},<br/> 
	 * &nbsp;&nbsp;"app":{<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"absolute path to desired *.app"<br/>    
	 * &nbsp;&nbsp;}<br/>      
     * <br/>
	 * It is enough for the successful starting. Also see {@link MobileCapabilityType}<br/>
	 * <b>{@link DriverService}</b>: none<br/>
	 * <br/>
	 * <b>Starts</b>: remotely. It requires {@link URL} of the host where Appium node server is started, e.g.
	 * http://127.0.0.1:4723/wd/hub (local host). Please find information here:<br/>
	 * http://appium.io/getting-started.html<br/>
	 * mobile apps only	
	 */
	IOS_APP(new DesiredCapabilities(), IOSDriver.class, false, true, null,
			null, true, true);

	public static ESupportedDrivers parse(String original) {
		String parcingStr = original.toUpperCase().trim();

		ESupportedDrivers[] values = ESupportedDrivers.values();
		for (ESupportedDrivers enumElem : values)
			if (parcingStr.equals(enumElem.toString()))
				return enumElem;
		throw new IllegalArgumentException("Webdriver with specified name "
				+ original + " is not supported");
	}

	private DesiredCapabilities capabilities;
	private Class<? extends WebDriver> driverClazz;
	private EServices service;
	final ILocalServerLauncher serverLauncher;
	private final boolean startsRemotely;

	private final boolean requiresRemoteURL;
	private final boolean isForBrowser;
	private final boolean isForMobileApp;

	private ESupportedDrivers(DesiredCapabilities capabilities,
			Class<? extends WebDriver> driverClazz, 
			boolean isForBrowser, boolean isForMobileApp,
			EServices sevice,
			ILocalServerLauncher serverLauncher, boolean startsRemotely,
			boolean requiresRemoteURL) {
		this.capabilities = capabilities;
		this.driverClazz = driverClazz;
		this.service = sevice;
		this.serverLauncher = serverLauncher;
		this.startsRemotely = startsRemotely;
		this.requiresRemoteURL = requiresRemoteURL;
		this.isForBrowser = isForBrowser;
		this.isForMobileApp = isForMobileApp;
	}

	/**
	 * @return {@link Capabilities} Default capabilities of given
	 *         {@link WebDriver} implementor
	 */
	public DesiredCapabilities getDefaultCapabilities() {
		return capabilities;
	}

	/**
	 * @return Class of supported {@link WebDriver} implementor
	 */
	public Class<? extends WebDriver> getUsingWebDriverClass() {
		return driverClazz;
	}

	/**
	 * Starts remote server locally It is possible to launch
	 * {@link SeleniumServer} locally for now
	 */
	public synchronized void launchRemoteServerLocallyIfWasDefined() {
		if (serverLauncher == null)
			return;
		if (serverLauncher.isLaunched())
			return;
		try {
			serverLauncher.launch();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return flag of necessity of URL (remote server)
	 */
	public boolean requiresRemoteURL() {
		return requiresRemoteURL;
	}

	/**
	 * 
	 * @param configInstance
	 *            . An instance of {@link Configuration} where path to
	 *            chromedriver*, IEDriverServer.exe or phantomjs* are defined
	 */
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

	/**
	 * @return flag of of possibility to start {@link WebDriver} implementor
	 *         remotely
	 */
	public boolean startsRemotely() {
		return startsRemotely;
	}
	
	public boolean isForBrowser(){
		return isForBrowser;
	}
	
	public boolean isForMobileApp(){
		return isForMobileApp;
	}
}
