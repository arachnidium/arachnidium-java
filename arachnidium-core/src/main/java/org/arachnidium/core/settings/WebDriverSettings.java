package org.arachnidium.core.settings;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;

import org.arachnidium.core.settings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Parameters of a {@link WebDriver} that have to be instantiated
 * @see Configuration
 * 
 * Specification:
 * 
 * ..
 * "webdriver":
  {
      "driverName":{
          "type":"STRING",
          "value":"some {@link WebDriver} designation"
      },    
      Allowed designation:
        - {@link FirefoxDriver} - FIREFOX/firefox
        - {@link ChromeDriver} - CHROME/chrome
        - {@link InternetExplorerDriver} - INTERNETEXPLORER/internetexplorer
        - {@link SafariDriver} - SAFARI/safari
        - {@link PhantomJSDriver} - PHANTOMJS/phantomjs
        - {@link RemoteWebDriver} - REMOTE/remote
        - {@link AppiumDriver} - MOBILE/mobile        
      "remoteAdress":{
          "type":"STRING",
          "value":"URL of the remote server like http://127.0.0.1:4444/wd/hub"
      } //this parameter is applied to {@link RemoteWebDriver} and {@link AppiumDriver}
      //by other is ignored
  }
  ...
 * 
 */
public class WebDriverSettings extends AbstractConfigurationAccessHelper {

	private final String remoteAddress = "remoteAdress";
	private final String webDriverName = "driverName";
	private final String webDriverGroup = "webdriver";

	public WebDriverSettings(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @return URL of the remote {@link WebDriver} server
	 */
	public URL getRemoteAddress() {
		String remoteURLValue = getSetting(remoteAddress);
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
	 * @see org.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(webDriverGroup, name);
	}

	/**
	 * @return Supported {@link WebDriver}. @see ESupportedDrivers
	 */
	public ESupportedDrivers getSupoortedWebDriver() {
		String name = getSetting(webDriverName);
		if (name != null)
			return ESupportedDrivers.parse(name);
		else
			return null;
	}

}
