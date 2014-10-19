/*
 +Copyright 2014 Arachnidium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package com.github.arachnidium.core;

import java.net.URL;

import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.interfaces.IConfigurable;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import com.github.arachnidium.util.logging.Log;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.github.arachnidium.core.bean.MainBeanConfiguration;
import com.github.arachnidium.core.components.ComponentFactory;
import com.github.arachnidium.core.components.WebdriverComponent;
import com.github.arachnidium.core.components.common.TimeOut;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.core.settings.CapabilitySettings;
import com.github.arachnidium.core.settings.WebDriverSettings;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;

/**
 * This class creates an instance of required {@link WebDriver} implementor,
 * wraps it and creates related components ({@link WebdriverComponent})
 *
 */
public class WebDriverEncapsulation implements IDestroyable, IConfigurable,
		WrapsDriver, IConfigurationWrapper {

	private static void prelaunch(ESupportedDrivers supporteddriver,
			Configuration config, Capabilities capabilities) {
		supporteddriver.launchRemoteServerLocallyIfWasDefined();
		supporteddriver.setSystemProperty(config, capabilities);
	}

	private RemoteWebDriver enclosedDriver;

	private Configuration configuration = Configuration.byDefault;
	final AbstractApplicationContext context = new AnnotationConfigApplicationContext(
			MainBeanConfiguration.class);
	private final DestroyableObjects destroyableObjects = new DestroyableObjects();
	private TimeOut timeOut;
	
	/**
	 * Creates and wraps an instance of {@link RemoteWebDriver} by the given
	 * {@link Configuration}
	 * 
	 * @param {@link Configuration}
	 */
	public WebDriverEncapsulation(Configuration configuration) {
		this.configuration = configuration;
		ESupportedDrivers supportedDriver = this.configuration.getSection(
				WebDriverSettings.class).getSupoortedWebDriver();

		Capabilities capabilities = this.configuration
				.getSection(CapabilitySettings.class);
		boolean definedCapabilitiesAreEmpty = false;
		if (capabilities == null){
			capabilities = supportedDriver.getDefaultCapabilities();
			definedCapabilitiesAreEmpty = true;
		}

		if (capabilities.asMap().size() == 0){
			capabilities = supportedDriver.getDefaultCapabilities();
			definedCapabilitiesAreEmpty = true;
		}
		
		if (!definedCapabilitiesAreEmpty) {
			DesiredCapabilities dc = new DesiredCapabilities();
			capabilities = dc.merge(supportedDriver.getDefaultCapabilities()).merge(
					capabilities);
		}

		URL remoteAdress = this.configuration.getSection(
				WebDriverSettings.class).getRemoteAddress();
		if (remoteAdress == null) {// local starting
			prelaunch(supportedDriver, this.configuration, capabilities);
			constructorBody(supportedDriver, capabilities, (URL) null);
			return;
		}

		try {
			constructorBody(supportedDriver, capabilities, remoteAdress);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Creates and wraps an instance of required {@link RemoteWebDriver}
	 * subclass
	 * 
	 * @param supporteddriver
	 *            Is the one element from {@link ESupportedDrivers} enumeration
	 *            which contains the class of required {@link RemoteWebDriver}
	 *            subclass
	 */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver) {
		this(supporteddriver, supporteddriver.getDefaultCapabilities());
	}

	/**
	 * Creates and wraps an instance of required {@link RemoteWebDriver}
	 * subclass with given {@link Capabilities}
	 * 
	 * @param supporteddriver
	 *            Is the one element from {@link ESupportedDrivers} enumeration
	 *            which contains the class of required {@link RemoteWebDriver}
	 *            subclass
	 * 
	 * @param capabilities
	 *            in an instance of {@link Capabilities}
	 */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver,
			Capabilities capabilities) {
		prelaunch(supporteddriver, this.configuration, capabilities);
		constructorBody(supporteddriver, capabilities, (URL) null);
	}

	/**
	 * Creates and wraps an instance of required {@link RemoteWebDriver}
	 * subclass with given {@link Capabilities}. It should be launched on the
	 * remote host.
	 * 
	 * @param supporteddriver
	 *            Is the one element from {@link ESupportedDrivers} enumeration
	 *            which contains the class of required {@link RemoteWebDriver}
	 *            subclass
	 * 
	 * @param capabilities
	 *            in an instance of {@link Capabilities}
	 * 
	 * @param remoteAddress
	 *            is the URL of the required remote host
	 */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver,
			Capabilities capabilities, URL remoteAddress) {
		constructorBody(supporteddriver, capabilities, remoteAddress);
	}

	/**
	 * Creates and wraps an instance of required {@link RemoteWebDriver}
	 * subclass. It should be launched on the remote host.
	 * 
	 * @param supporteddriver
	 *            Is the one element from {@link ESupportedDrivers} enumeration
	 *            which contains the class of required {@link RemoteWebDriver}
	 *            subclass
	 * 
	 * @param remoteAddress
	 *            is the URL of the required remote host
	 */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver,
			URL remoteAddress) {
		this(supporteddriver, supporteddriver.getDefaultCapabilities(),
				remoteAddress);
	}

	/**
	 * Wraps an instance of required {@link RemoteWebDriver} subclass which is
	 * already instantiated
	 * 
	 * @param explicitlyInitiatedWebDriver
	 *            it is already instantiated {@link RemoteWebDriver}
	 */
	public WebDriverEncapsulation(RemoteWebDriver explicitlyInitiatedWebDriver) {
		this(explicitlyInitiatedWebDriver, Configuration.byDefault);
	}

	/**
	 * Wraps an instance of required {@link RemoteWebDriver} subclass which is
	 * already instantiated and applies given {@link Configuration}
	 * 
	 * @param explicitlyInitiatedWebDriver
	 *            it is already instantiated {@link RemoteWebDriver}
	 * @param {@link Configuration}
	 */
	public WebDriverEncapsulation(RemoteWebDriver explicitlyInitiatedWebDriver,
			Configuration configuration) {
		this.configuration = configuration;
		enclosedDriver = (RemoteWebDriver) context.getBean(
				MainBeanConfiguration.WEBDRIVER_BEAN, context, this,
				destroyableObjects, explicitlyInitiatedWebDriver);
		Log.message("Getting started with already instantiated "
				+ explicitlyInitiatedWebDriver.getClass());
		timeOut = getComponent(TimeOut.class);
		resetAccordingTo(configuration);
	}

	// other methods:
	private void constructorBody(ESupportedDrivers supporteddriver,
			Capabilities capabilities, URL remoteAddress) {
		if (supporteddriver.startsRemotely() & remoteAddress != null)
			createWebDriver(supporteddriver.getUsingWebDriverClass(),
					new Class[] { URL.class, Capabilities.class },
					new Object[] { remoteAddress, capabilities });
		else {
			if (remoteAddress == null & supporteddriver.requiresRemoteURL())
				throw new RuntimeException(
						"Defined driver '"
								+ supporteddriver.toString()
								+ "' requires remote address (URL)! Please, define it in settings.json "
								+ "or use suitable constructor");
			if (remoteAddress != null)
				Log.message("Remote address " + String.valueOf(remoteAddress)
						+ " has been ignored");
			createWebDriver(supporteddriver.getUsingWebDriverClass(),
					new Class[] { Capabilities.class },
					new Object[] { capabilities });
		}
	}

	// it makes objects of any WebDriver and navigates to specified URL
	private void createWebDriver(Class<? extends WebDriver> driverClass,
			Class<?>[] paramClasses, Object[] values) {
		try {
			enclosedDriver = (RemoteWebDriver) context.getBean(
					MainBeanConfiguration.WEBDRIVER_BEAN, context, this,
					destroyableObjects, driverClass, paramClasses, values);
			Log.message("Getting started with " + driverClass.getSimpleName());
			timeOut = getComponent(TimeOut.class);
			resetAccordingTo(configuration);
		} catch (Exception e) {
			Log.error(
					"Attempt to create a new web driver instance has been failed! "
							+ e.getMessage(), e);
			destroy();
			throw e;
		}
	}

	/**
	 * Attempts to shut down {@link RemoteWebDriver} and destroys all related
	 * information
	 */
	@Override
	public void destroy() {
		if (enclosedDriver == null)
			return;
		try {
			enclosedDriver.quit();
		} catch (WebDriverException e) { // it may be already dead
			return;
		}
	}

	/**
	 * adds an object which related to {@link Webdriver} and has to be "destroyed"
	 * after quit
	 */
	public void addDestroyable(IDestroyable destroyable) {
		destroyableObjects.add(destroyable);
	}

	/**
	 * @param required {@link WebdriverComponent} subclass
	 * @return The instance of required {@link WebdriverComponent} subclass
	 */
	public <T extends WebdriverComponent> T getComponent(Class<T> required) {
		return ComponentFactory.getComponent(required, enclosedDriver);
	}

	/**
	 * 
	 * @param required {@link WebdriverComponent} subclass
	 * 
	 * @param params is a Class[] which excludes {@link WebDriver}.class
	 * {@link WebDriver} + given Class[] should match to {@link WebdriverComponent} subclass
	 * constructor parameters
	 *   
	 * @param values is a Object[] which excludes {@link WebDriver} instance
	 * {@link WebDriver} instance + given Object[] should match to {@link WebdriverComponent} subclass
	 * constructor 
	 * 
	 * @return The instance of required {@link WebdriverComponent} subclass
	 */
	public <T extends WebdriverComponent> T getComponent(Class<T> required,
			Class<?>[] params, Object[] values) {
		return ComponentFactory.getComponent(required, enclosedDriver, params,
				values);
	}

	/**
	 * @see org.openqa.selenium.internal.WrapsDriver#getWrappedDriver()
	 */
	@Override
	public WebDriver getWrappedDriver() {
		return enclosedDriver;
	}

	/**
	 * This method replaces previous {@link Configuration}
	 * and applies new given parameters 
	 * 
	 * @see com.github.arachnidium.util.configuration.interfaces.IConfigurable#resetAccordingTo(com.github.arachnidium.util.configuration.Configuration)
	 */
	@Override
	public synchronized void resetAccordingTo(Configuration config) {
		configuration = config;
		timeOut.resetAccordingTo(configuration);
	}

	/**
	 * Returns {@link Configuration}
	 * 
	 * @see com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper#getWrappedConfiguration()
	 */
	@Override
	public Configuration getWrappedConfiguration() {
		return configuration;
	}
	
	public TimeOut getTimeOut(){
		return timeOut;
	}
}