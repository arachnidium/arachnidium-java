package com.github.arachnidium.model.abstractions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.model.interfaces.IModelObjectExceptionHandler;
import com.github.arachnidium.model.support.annotations.IDefaultAnnotationReader;

/**
 * It is the abstraction which describes the process of 
 * implicit and automated exception handling
 * 
 * @see IModelObjectExceptionHandler
 * @see MethodProxy
 * @see MethodInterceptor
 */
public abstract class ModelObjectExceptionHandler implements
IModelObjectExceptionHandler {
	private List<Class<? extends Throwable>> throwableList = new ArrayList<Class<? extends Throwable>>();

	/**
	 * @param tClass is the class of exception which should be caught and
	 * handled 
	 */
	public ModelObjectExceptionHandler(Class<? extends Throwable> tClass) {
		addThrowableClass(tClass);
	}

	public ModelObjectExceptionHandler() {
		super();
		IDefaultAnnotationReader reader = new IDefaultAnnotationReader() {
		};
		ExpectectedThrowables[] expectectedThrowables = reader.getAnnotations(ExpectectedThrowables.class, 
				this.getClass());		
		if (expectectedThrowables.length != 0){
			ExpectectedThrowables et = expectectedThrowables[0];
			throwableList.addAll(Arrays.asList(et.expectedThrowables()));
		}
	}
	
	@Override
	public abstract Object handleException(Object object,
			Method originalMethod, MethodProxy methodProxy, Object[] args,
			Throwable t) throws Throwable;

	/**
	 * 
	 * @param tClass is the class of exception which would be handled
	 * @return flag 
	 *    <code>true</code> if exception can be handled by this {@link ModelObjectExceptionHandler}
	 */
	public boolean isThrowableInList(Class<? extends Throwable> tClass) {
		return throwableList.contains(tClass);
	}
	
	@Override
	public void addThrowableClass(Class<? extends Throwable> tClass){
		throwableList.add(tClass);
	}

}
