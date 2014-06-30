package org.arachnidium.model.abstractions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.model.interfaces.IModelObjectExceptionHandler;

/**
 * @author s.tihomirov this is an abstract class. It's inheritors should handle
 *         exceptions of defined types
 */
public abstract class ModelObjectExceptionHandler implements
		IModelObjectExceptionHandler {
	private List<Class<? extends Throwable>> throwableList = new ArrayList<Class<? extends Throwable>>();

	public boolean isThrowableInList(Class<? extends Throwable> tClass) {
		return throwableList.contains(tClass);
	}

	public ModelObjectExceptionHandler(Class<? extends Throwable> tClass) {
		throwableList.add(tClass);
	}

	public ModelObjectExceptionHandler(
			List<Class<? extends Throwable>> tClassList) {
		throwableList.addAll(tClassList);
	}

	@Override
	public abstract Object handleException(Object object,
			Method originalMethod, MethodProxy methodProxy, Object[] args,
			Throwable t) throws Throwable;

}
