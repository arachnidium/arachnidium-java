package org.arachnidium.core.beans.webdriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
public class WebDriverBeanConfiguration implements IWebdriverPountCut{
	private IConfigurationWrapper wrapper;
	private WebDriver driver;
	private AbstractApplicationContext context;
	
	public final static String COMPONENT_BEAN = "component";
	public final static String WEBDRIVER_BEAN = "webdriver";
	
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
	@Bean(name = COMPONENT_BEAN)
	public Object  getComponent(Object component) {
		return component;
	}
	
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = "webdriverAspect")
	AspectWebDriverEventListener getAspect(){
		return new AspectWebDriverEventListener(driver, wrapper, context);
	}

}
