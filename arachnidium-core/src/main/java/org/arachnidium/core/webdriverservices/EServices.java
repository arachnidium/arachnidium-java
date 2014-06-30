package org.arachnidium.core.webdriverservices;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.arachnidium.core.webdriversettings.ChromeDriverServerBin;
import org.arachnidium.core.webdriversettings.IEDriverServerBin;
import org.arachnidium.core.webdriversettings.PhantomJSDriverBin;
import org.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IHasPathToFile;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

public enum EServices {
	CHROMESERVICE(ChromeDriverServerBin.class,
			ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
			new HashMap<Platform, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(Platform.WINDOWS, "chromedriver.exe");
					put(Platform.MAC, "chromedriver");
					put(Platform.LINUX, "chromedriver");
				}

			}), IEXPLORERSERVICE(IEDriverServerBin.class,
			InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY,
			new HashMap<Platform, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(Platform.WINDOWS, "IEDriverServer.exe");
				}

			}), PHANTOMJSSERVICE(PhantomJSDriverBin.class,
			PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
			new HashMap<Platform, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(Platform.WINDOWS, "phantomjs.exe");
					put(Platform.MAC, "phantomjs");
					put(Platform.LINUX, "phantomjs");
				}

			});

	private static final String defaultFolder = "";
	private final String propertyName;
	private final Class<? extends AbstractConfigurationAccessHelper> clazzOfSettings;
	private final HashMap<Platform, String> defaultServerFileAccordance;

	// system properties should be set
	public void setSystemProperty(Configuration configInstance) {
		if (System.getProperty(propertyName) != null) {
			return; // if property is already set up
		}

		IHasPathToFile pathConfig = (IHasPathToFile) configInstance
				.getSection(clazzOfSettings);
		String folder = String.valueOf(pathConfig.getFolder());
		if (!new File(folder).exists()) {
			folder = defaultFolder;
		}

		String fileName = String.valueOf(pathConfig.getFile());
		File serverFile = new File(folder + fileName);
		if (serverFile.exists()) { // there is nothing to do is file exists
			System.setProperty(propertyName, serverFile.getAbsolutePath());
			return;
		}

		// Attempt to get the file by its default name according current
		// operating system
		Set<Entry<Platform, String>> accordances = defaultServerFileAccordance
				.entrySet();
		// checks by current operating system
		Platform currentOS = Platform.getCurrent();
		for (Entry<Platform, String> accordance : accordances) {
			if (accordance.getKey().is(currentOS)) {
				fileName = accordance.getValue();

				serverFile = new File(folder + fileName);
				if (serverFile.exists()) {
					System.setProperty(propertyName,
							serverFile.getAbsolutePath());
					return;
				}
				throw new RuntimeException("Can't set value for '"
						+ this.propertyName
						+ "' system property. Proxy server "
						+ " file wasn't found.");
			}
		}

		throw new RuntimeException(
				"Can't set value for '"
						+ this.propertyName
						+ "' system property. Proxy server "
						+ " file wasn't found. Also it is not defined for current platform "
						+ currentOS.toString());
	}

	private EServices(
			Class<? extends AbstractConfigurationAccessHelper> classOfSetting,
			String propertyName,
			HashMap<Platform, String> defaultServerFileAccordance) {
		this.clazzOfSettings = classOfSetting;
		this.defaultServerFileAccordance = defaultServerFileAccordance;
		this.propertyName = propertyName;
	}

}
