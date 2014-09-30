package com.github.arachnidium.util.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * The default {@link MethodInterceptor} implementor
 *
 * @see MethodInterceptor
 * @see MethodProxy
 */
public class DefaultInterceptor implements MethodInterceptor {

	/**
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[],
	 *      net.sf.cglib.proxy.MethodProxy)
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return proxy.invokeSuper(obj, args);
	}

}
