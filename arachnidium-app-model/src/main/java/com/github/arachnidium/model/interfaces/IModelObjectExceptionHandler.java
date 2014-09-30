package com.github.arachnidium.model.interfaces;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * This interface should provide the model of 
 * implicit and automated handling of caught exceptions
 * 
 * @see MethodProxy
 * @see MethodInterceptor
 */
public interface IModelObjectExceptionHandler {
	public Object handleException(Object object, Method originalMethod,
			MethodProxy methodProxy, Object[] args, Throwable t)
			throws Throwable;
}
