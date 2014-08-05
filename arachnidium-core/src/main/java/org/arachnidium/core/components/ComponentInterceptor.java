package org.arachnidium.core.components;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.util.proxy.DefaultInterceptor;

class ComponentInterceptor extends DefaultInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object realObject = ((WebdriverComponent) obj).delegate;
		Method realMethod = realObject.getClass().getMethod(method.getName(),
				method.getParameterTypes());
		if (obj.getClass().equals(realObject.getClass())) {
			return super.intercept(realObject, method, args, proxy);
		} else {
			return realMethod.invoke(realObject, args);
		}
	}

}
