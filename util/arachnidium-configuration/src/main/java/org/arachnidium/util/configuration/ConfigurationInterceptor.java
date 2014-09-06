package org.arachnidium.util.configuration;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * It makes requests to default configuration when there is
 *         no data in given configuration
 */
class ConfigurationInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object result = proxy.invokeSuper(obj, args);
		if (result == null & obj != Configuration.byDefault)
			result = proxy.invokeSuper(Configuration.byDefault, args);
		return result;
	}

}
