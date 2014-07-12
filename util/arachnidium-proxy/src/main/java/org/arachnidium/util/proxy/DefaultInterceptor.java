package org.arachnidium.util.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * A default interceptor for all proxy objects
 *
 */
public class DefaultInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return proxy.invokeSuper(obj, args);
	}

}
