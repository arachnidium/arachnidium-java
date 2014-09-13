package org.arachnidium.model.abstractions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.model.interfaces.IModelObjectExceptionHandler;

/**
 * It is the abstraction which describes the process of 
 * automated exception handling
 */
public abstract class ModelObjectExceptionHandler implements
IModelObjectExceptionHandler {
	private List<Class<? extends Throwable>> throwableList = new ArrayList<Class<? extends Throwable>>();

	/**
	 * @param tClass is the class of exception which should be caught and
	 * handled 
	 */
	public ModelObjectExceptionHandler(Class<? extends Throwable> tClass) {
		throwableList.add(tClass);
	}

	/**
	 * @param tClassList is the class list of exceptions which should be caught and
	 */
	public ModelObjectExceptionHandler(
			List<Class<? extends Throwable>> tClassList) {
		throwableList.addAll(tClassList);
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

}
