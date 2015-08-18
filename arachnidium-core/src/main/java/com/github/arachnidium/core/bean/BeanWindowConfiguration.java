package com.github.arachnidium.core.bean;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

/**
 * This is {@link AnnotationConfigApplicationContext}
 * 
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BeanWindowConfiguration extends AbstractBeanHandleConfiguration {

	public final static String WINDOW_BEAN    = "window";
	
	/**
	 * Makes an instance of {@link IExtendedWindow} listenable
	 * @param window An original instance of {@link IExtendedWindow}
	 * @return The listenable instance of {@link IExtendedWindow}
	 * 
	 * @see Bean
	 */
	@Override
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = WINDOW_BEAN)
	public <T extends IHasHandle> T getHandle(T handle, IConfigurationWrapper configurationWrapper){
		return super.getHandle(handle, configurationWrapper);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = "windowAspect")
	AspectWindow getHandleAspect() {
		return new AspectWindow(configurationWrapper);
	}

}
