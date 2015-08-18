package com.github.arachnidium.core.bean;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BeanContextConfiguration extends AbstractBeanHandleConfiguration {

	public final static String MOBILE_CONTEXT_BEAN    = "mobile_context";
	
	/**
	 * Makes an instance of {@link IContext} listenable
	 * @param window An original instance of {@link IContext}
	 * @return The listenable instance of {@link IContext}
	 * 
	 * @see Bean
	 */	
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = MOBILE_CONTEXT_BEAN)
	public <T extends IHasHandle> T getHandle(T handle, IConfigurationWrapper configurationWrapper){
		return super.getHandle(handle, configurationWrapper);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = "contextAspect")
	AspectContext getHandleAspect() {
		return new AspectContext(configurationWrapper);
	}

}
