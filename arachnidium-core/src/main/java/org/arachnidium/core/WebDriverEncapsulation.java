package org.arachnidium.core;

import java.net.URL;

import org.arachnidium.core.bean.MainBeanConfiguration;
import org.arachnidium.core.components.ComponentFactory;
import org.arachnidium.core.components.WebdriverComponent;
import org.arachnidium.core.components.common.TimeOut;
import org.arachnidium.core.interfaces.IDestroyable;
import org.arachnidium.core.settings.CapabilitySettings;
import org.arachnidium.core.settings.WebDriverSettings;
import org.arachnidium.core.settings.supported.ESupportedDrivers;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IConfigurable;
import org.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import org.arachnidium.util.logging.Log;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class WebDriverEncapsulation implements IDestroyable, IConfigurable,
WrapsDriver, IConfigurationWrapper{
	protected static void prelaunch(ESupportedDrivers supporteddriver,
			Configuration config, Capabilities capabilities) {
		supporteddriver.launchRemoteServerLocallyIfWasDefined(config);
		supporteddriver.setSystemProperty(config, capabilities);
	}

	// get tests started with FireFoxDriver by default.
	private static ESupportedDrivers defaultSupportedDriver = ESupportedDrivers.FIREFOX;
	private RemoteWebDriver enclosedDriver;

	private Configuration configuration = Configuration.byDefault;
	private TimeOut timeout;
	final AbstractApplicationContext context = 
			new AnnotationConfigApplicationContext(MainBeanConfiguration.class);
	private final DestroyableObjects destroyableObjects = new DestroyableObjects();
	
	/**
	 * creates instance by specified driver and remote address using specified
	 * configuration
	 */
	public WebDriverEncapsulation(Configuration configuration) {
		this.configuration = configuration;
		ESupportedDrivers supportedDriver = this.configuration.getSection(
				WebDriverSettings.class).getSupoortedWebDriver();
		if (supportedDriver == null)
			supportedDriver = defaultSupportedDriver;

		Capabilities capabilities = this.configuration
				.getSection(CapabilitySettings.class);
		if (capabilities == null)
			capabilities = supportedDriver.getDefaultCapabilities();

		if (capabilities.asMap().size() == 0)
			capabilities = supportedDriver.getDefaultCapabilities();

		String remoteAdress = this.configuration.getSection(
				WebDriverSettings.class).getRemoteAddress();
		if (remoteAdress == null) {// local starting
			prelaunch(supportedDriver, this.configuration, capabilities);
			constructorBody(supportedDriver, capabilities, (URL) null);
			return;
		}

		try {
			URL remoteUrl = new URL(remoteAdress);
			constructorBody(supportedDriver, capabilities, remoteUrl);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/** creates instance by specified driver */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver) {
		this(supporteddriver, supporteddriver.getDefaultCapabilities());
	}

	// constructors are below:
	/** creates instance by specified driver and capabilities */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver,
			Capabilities capabilities) {
		prelaunch(supporteddriver, this.configuration, capabilities);
		constructorBody(supporteddriver, capabilities, (URL) null);
	}

	/** creates instance by specified driver, capabilities and remote address */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver,
			Capabilities capabilities, URL remoteAddress) {
		constructorBody(supporteddriver, capabilities, remoteAddress);
	}

	/**
	 * creates instance by specified driver and remote address using default
	 * capabilities
	 */
	public WebDriverEncapsulation(ESupportedDrivers supporteddriver,
			URL remoteAddress) {
		this(supporteddriver, supporteddriver.getDefaultCapabilities(),
				remoteAddress);
	}

	/** creates instance using externally initiated webdriver **/
	public WebDriverEncapsulation(RemoteWebDriver externallyInitiatedWebDriver) {
		this(externallyInitiatedWebDriver, Configuration.byDefault);
	}

	/** creates instance using externally initiated webdriver **/
	public WebDriverEncapsulation(RemoteWebDriver externallyInitiatedWebDriver,
			Configuration configuration) {
		this.configuration = configuration;		
		enclosedDriver = (RemoteWebDriver) context.getBean(MainBeanConfiguration.WEBDRIVER_BEAN, context,
				this, destroyableObjects, externallyInitiatedWebDriver);
		actoinsAfterWebDriverCreation(externallyInitiatedWebDriver.getClass());
	}

	private void actoinsAfterWebDriverCreation(Class<? extends WebDriver> driverClass) {
		Log.message("Getting started with "
				+ driverClass.getSimpleName());
		timeout = getComponent(TimeOut.class);
		resetAccordingTo(configuration);
	}

	// if attempt to create a new web driver instance has been failed
	protected void actoinsOnConstructFailure(RuntimeException e) {
		Log.error(
				"Attempt to create a new web driver instance has been failed! "
						+ e.getMessage(), e);
		destroy();
		throw e;

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
			enclosedDriver = (RemoteWebDriver) context.getBean(MainBeanConfiguration.WEBDRIVER_BEAN, context,
					this, destroyableObjects, driverClass, paramClasses, values);
			actoinsAfterWebDriverCreation(driverClass);
		} catch (Exception e) {
			if (enclosedDriver != null)
				enclosedDriver.quit();
			actoinsOnConstructFailure(new RuntimeException(e));
		}
	}

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
	 * adds an object which related to {@link Webdriver} 
	 * and has to destroyed after quit
	 */
	public void addDestroyable(IDestroyable destroyable){
		destroyableObjects.add(destroyable);
	}
	
	public TimeOut getTimeOut() {
		return timeout;
	}
	
	public <T extends WebdriverComponent> T getComponent(Class<T> required){
		return ComponentFactory.getComponent(required, enclosedDriver);
	}
	
	public <T extends WebdriverComponent> T getComponent(Class<T> required, Class<?>[] params, Object[] values){
		return ComponentFactory.getComponent(required, enclosedDriver, params, values);
	}	

	// it goes to another URL
	public void getTo(String url) {
		enclosedDriver.get(url);
	}

	@Override
	public WebDriver getWrappedDriver() {
		return enclosedDriver;
	}


	@Override
	public synchronized void resetAccordingTo(Configuration config) {
		configuration = config;
		timeout.resetAccordingTo(config);
	}

	@Override
	public Configuration getWrappedConfiguration() {
		return configuration;
	}
}