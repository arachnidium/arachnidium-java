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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.github.arachnidium.core.bean.WebDriverBeanConfiguration;
import com.github.arachnidium.core.components.ComponentFactory;
import com.github.arachnidium.core.components.WebdriverComponent;
import com.github.arachnidium.core.components.common.TimeOut;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.interfaces.IConfigurable;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import com.github.arachnidium.util.logging.Log;

/**
 * This class creates an instance of required {@link WebDriver} implementor,
 * wraps it and creates related components ({@link WebdriverComponent})
 *
 */
public class WebDriverEncapsulation implements IDestroyable, IConfigurable,
		WrapsDriver, IConfigurationWrapper {

	private final RemoteWebDriver enclosedDriver;

	private Configuration configuration = Configuration.byDefault;
	private final DestroyableObjects destroyableObjects = new DestroyableObjects();
	private final TimeOut timeOut;
	private final ESupportedDrivers instantiatedESupportedDriver;

	/**
	 * Allows to instantiate the selected {@link WebDriver} by given parameters.
	 * These parameters should correspond existing {@link WebDriver} constructors
	 * 
	 * @param supporteddriver the selected {@link WebDriver} representation
	 * @param values they are used to launch {@link WebDriver}
	 */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver,
			Object... values) {
		try {
			Class<? extends WebDriver> driverClass = supporteddriver.getUsingWebDriverClass();
			
			AbstractApplicationContext context = new AnnotationConfigApplicationContext(
					WebDriverBeanConfiguration.class);
			enclosedDriver = (RemoteWebDriver) context.getBean(
					WebDriverBeanConfiguration.WEBDRIVER_BEAN, context, this,
					destroyableObjects, driverClass, values);
			Log.message("Getting started with " + driverClass.getSimpleName());
			timeOut = getComponent(TimeOut.class);
			resetAccordingTo(configuration);
			this.instantiatedESupportedDriver = supporteddriver;
			
		} catch (Exception e) {
			Log.error(
					"Attempt to create a new web driver instance has been failed! "
							+ e.getMessage(), e);
			destroy();
			throw new RuntimeException(e);
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
	
	public ESupportedDrivers getInstantiatedSupportedDriver(){
		return instantiatedESupportedDriver;
	}
}