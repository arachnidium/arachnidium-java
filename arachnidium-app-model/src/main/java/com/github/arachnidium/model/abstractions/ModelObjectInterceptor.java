package com.github.arachnidium.model.abstractions;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.util.proxy.DefaultInterceptor;

/**
 *
 * A default interceptor for any {@link ModelObject}
 * 
 * It invokes methods. If some exception is thrown
 * it attempts to handle it implicitly 
 * 
 * @see MethodInterceptor
 * 
 * @see DefaultInterceptor
 */
public abstract class ModelObjectInterceptor	extends DefaultInterceptor {

	protected static final String GET_PART = "getPart";
		
	/**
	 * @see com.github.arachnidium.util.proxy.DefaultInterceptor#intercept(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[],
	 *      net.sf.cglib.proxy.MethodProxy)
	 *      
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[],
	 *      net.sf.cglib.proxy.MethodProxy)     
	 */
	@Override
	public Object intercept(Object modelObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		try {
			return super.intercept(modelObj, method, args, proxy);
		} catch (Exception e) {
			return ((ModelObject<?>) modelObj).exceptionHandler
					.handleException(modelObj, method, proxy, args, e);
		}
	}

}
