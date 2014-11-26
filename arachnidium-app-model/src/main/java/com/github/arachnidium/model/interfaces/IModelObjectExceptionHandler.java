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
	
	/**
	 * This method should add subclass of {@link Throwable} which should be 
	 * handled 
     *
	 * @param tClass is the subclass of {@link Throwable} which should be 
	 * handled 
	 */
	public void addThrowableClass(Class<? extends Throwable> tClass);
}
