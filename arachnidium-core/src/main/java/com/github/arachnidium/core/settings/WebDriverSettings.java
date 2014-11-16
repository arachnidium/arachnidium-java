package com.github.arachnidium.core.settings;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;

/**
 * Parameters of a {@link WebDriver} that have to be instantiated
 * 
 * Specification:
 * 
 *<p><br/>
 *...<br/>
 * "webdriver":<br/>
 *{<br/>
 *&nbsp;&nbsp;"driverName":{<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"some {@link WebDriver} designation"<br/>
 *&nbsp;&nbsp;},<br/>    
 *     Allowed designation:<br/>
 *       - {@link FirefoxDriver} - FIREFOX/firefox<br/>
 *       - {@link ChromeDriver} - CHROME/chrome<br/>
 *       - {@link InternetExplorerDriver} - INTERNETEXPLORER/internetexplorer<br/>
 *       - {@link SafariDriver} - SAFARI/safari<br/>
 *       - {@link PhantomJSDriver} - PHANTOMJS/phantomjs<br/>
 *       - {@link RemoteWebDriver} - REMOTE/remote<br/>
 *       - {@link AndroidDriver} - ANDROID_CHROME/android_chrome or ANDROID_APP/android_app<br/>  
 *       - {@link IOSDriver} - IOS_SAFARI/ios_safari or IOS_APP/ios_app<br/>      
 * &nbsp;&nbsp;"remoteAdress":{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"URL of the remote server like http://127.0.0.1:4444/wd/hub"<br/>
 * &nbsp;&nbsp;}<code>//this parameter is applied to {@link RemoteWebDriver}, {@link AndroidDriver} 
 * and {@link IOSDriver}</code><br/>
 * &nbsp;&nbsp;<code>//by other is ignored</code><br/>
 * }<br/>
 * ...<br/>
 * </p>
 * 
 *@see Configuration
 *@see ESupportedDrivers
 */
@Group(settingGroup = "webdriver")
public class WebDriverSettings extends AbstractConfigurationAccessHelper {
	/**
	 * {@link FirefoxDriver} is used as default value
	 */
	private final ESupportedDrivers DEFAULT_SUPPORTED_DRIVER = ESupportedDrivers.FIREFOX;

	protected WebDriverSettings(Configuration configuration, String group) {
		super(configuration, group);
	}

	/**
	 * @return URL of the remote {@link WebDriver} server
	 */
	@Setting(setting = "remoteAdress")
	public URL getRemoteAddress() {
		String remoteURLValue = getSetting();
		if (remoteURLValue == null){
			return null; 
		}
		try {
			return new URL(remoteURLValue);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return Supported {@link WebDriver}. 
	 * 
	 * @see ESupportedDrivers
	 */
	@Setting(setting = "driverName")
	public ESupportedDrivers getSupoortedWebDriver() {
		String name = getSetting();
		if (name != null)
			return ESupportedDrivers.parse(name);
		else
			return DEFAULT_SUPPORTED_DRIVER;
	}

}
