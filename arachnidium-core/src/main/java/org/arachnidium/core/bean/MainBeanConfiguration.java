package org.arachnidium.core.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.arachnidium.core.interfaces.IContext;
import org.arachnidium.core.interfaces.IExtendedWindow;
import org.arachnidium.util.configuration.interfaces.IConfigurationWrapper;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * creates beans of {@link WebDriver} and related objects
 */

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MainBeanConfiguration {
	private IConfigurationWrapper wrapper;
	private WebDriver driver;
	private AbstractApplicationContext context;
	
	public final static String COMPONENT_BEAN = "component";
	public final static String WEBDRIVER_BEAN = "webdriver";
	public final static String WINDOW_BEAN    = "window";
	public final static String MOBILE_CONTEXT_BEAN    = "mobile_context";
	
	@SuppressWarnings("unchecked")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = WEBDRIVER_BEAN)
	public <T extends WebDriver> T getWebdriver(AbstractApplicationContext context, 
			IConfigurationWrapper configurationWrapper,
			Class<T> required,
			Class<?>[] paramClasses, Object[] paramValues) {
		try {
			Constructor<?> c = required.getConstructor(paramClasses);
			T result = (T) c.newInstance(paramValues);
			driver = result;
			wrapper = configurationWrapper;
			this.context = context;
			return result;
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = WEBDRIVER_BEAN)
	public WebDriver getWebdriver(AbstractApplicationContext context, 
			IConfigurationWrapper configurationWrapper,
			WebDriver driver) {
		this.driver = driver;
		wrapper = configurationWrapper;
		this.context = context;
		return driver;
	}
	
	@SuppressWarnings("unchecked")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = WINDOW_BEAN)
	public <T extends IExtendedWindow> T getWindow(IExtendedWindow window) {
		return (T) window;
	}	
	
	@SuppressWarnings("unchecked")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = MOBILE_CONTEXT_BEAN)
	public <T extends IContext> T getContext(IContext context) {
		return (T) context;
	}		
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = COMPONENT_BEAN)
	Object  getComponent(Object component) {
		return component;
	}	
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = "webdriverAspect")
	AspectWebDriverEventListener getWebdriverAspect(){
		return new AspectWebDriverEventListener(driver, wrapper, context);
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
