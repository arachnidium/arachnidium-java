package org.arachnidium.core.bean;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.arachnidium.core.interfaces.IContext;
import org.arachnidium.core.interfaces.IDestroyable;
import org.arachnidium.core.interfaces.IExtendedWindow;
import org.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;


/**
 * This is {@link AnnotationConfigApplicationContext}
 * 
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MainBeanConfiguration {
	private IConfigurationWrapper wrapper;
	private WebDriver driver;
	private AbstractApplicationContext context;
	private IDestroyable destroyable;
	
	public final static String COMPONENT_BEAN = "component";
	public final static String WEBDRIVER_BEAN = "webdriver";
	public final static String WINDOW_BEAN    = "window";
	public final static String MOBILE_CONTEXT_BEAN    = "mobile_context";
	
	/**
	 * Creates {@link WebDriver} instance and makes it listenable.
	 * 
	 * @param context instantiated {@link AbstractApplicationContext} 
	 * which is used by {@link AspectWebDriverEventListener}
	 * @param configurationWrapper something that wraps {@link Configuration}
	 * {@link AspectWebDriverEventListener} needs it
	 * @param destroyable Something that implements {@link IDestroyable}
	 * @param required Class of {@link WebDriver} implementor
	 * @param paramClasses  Are constructor parameters
	 * @param paramValues Are constructor parameter values
	 * 
	 * @return A listenable {@link WebDriver} instance
	 */
	@SuppressWarnings("unchecked")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = WEBDRIVER_BEAN)
	public <T extends WebDriver> T getWebdriver(AbstractApplicationContext context, 
			IConfigurationWrapper configurationWrapper,
			IDestroyable destroyable,
			Class<T> required,
			Class<?>[] paramClasses, Object[] paramValues) {
		try {
			Constructor<?> c = required.getConstructor(paramClasses);
			T result = (T) c.newInstance(paramValues);
			return (T) populate(context, configurationWrapper, destroyable, result);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	private WebDriver populate(AbstractApplicationContext context, 
			IConfigurationWrapper configurationWrapper,
			IDestroyable destroyable,
			WebDriver driver){
		this.driver = driver;
		wrapper = configurationWrapper;
		this.destroyable = destroyable;
		this.context = context;
		return driver;
	}
	
	/**
	 * It makes instantiated {@link WebDriver} listenable
	 * 
	 * @param context nstantiated {@link AbstractApplicationContext} 
	 * which is used by {@link AspectWebDriverEventListener}
	 * 
	 * @param configurationWrapper something that wraps {@link Configuration}
	 * {@link AspectWebDriverEventListener} needs it
	 * @param destroyable Something that implements {@link IDestroyable}
	 * @param driver An instance of {@link WebDriver}
	 * 
	 * @return A listenable {@link WebDriver} instance
	 * 
	 * @see Bean
	 */
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = WEBDRIVER_BEAN)
	public WebDriver getWebdriver(AbstractApplicationContext context, 
			IConfigurationWrapper configurationWrapper,
			IDestroyable destroyable,
			WebDriver driver) {
		return populate(context, configurationWrapper, destroyable, driver);
	}
	
	/**
	 * Makes an instance of {@link IExtendedWindow} listenable
	 * @param window An original instance of {@link IExtendedWindow}
	 * @return The listenable instance of {@link IExtendedWindow}
	 * 
	 * @see Bean
	 */
	@SuppressWarnings("unchecked")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = WINDOW_BEAN)
	public <T extends IExtendedWindow> T getWindow(IExtendedWindow window) {
		return (T) window;
	}	
	
	/**
	 * Makes an instance of {@link IContext} listenable
	 * @param window An original instance of {@link IContext}
	 * @return The listenable instance of {@link IContext}
	 * 
	 * @see Bean
	 */	
	@SuppressWarnings("unchecked")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = MOBILE_CONTEXT_BEAN)
	public <T extends IContext> T getContext(IContext context) {
		return (T) context;
	}		
	
	/**
	 * It return listenable {@link WebDriver} components
	 * 
	 * @see WebDriver
	 * @see WebElement
	 * @see Navigation
	 * @see Options
	 * @see TargetLocator
	 * @see JavascriptExecutor
	 * @see ContextAware
	 * @see Alert
	 * @see MobileElement
	 * @see AppiumDriver
	 *  
	 * @param component It is an object of types above
	 * @return The listenable object of types above
	 * 
	 * @see Bean
	 */
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = COMPONENT_BEAN)
	Object  getComponent(Object component) {
		return component;
	}	
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = "webdriverAspect")
	AspectWebDriverEventListener getWebdriverAspect(){
		return new AspectWebDriverEventListener(driver, wrapper, destroyable, context);
	}
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = "windowAspect")
	AspectWindowListener getWindowAspect(){
		return new AspectWindowListener(wrapper);
	}	
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = "contextAspect")
	AspectContextListener getContextAspect(){
		return new AspectContextListener(wrapper);
	}		

}
