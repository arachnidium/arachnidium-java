package org.arachnidium.core.components.bydefault;

import java.lang.reflect.Method;

import org.arachnidium.util.proxy.DefaultInterceptor;

import net.sf.cglib.proxy.MethodProxy;


class ComponentInterceptor extends DefaultInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return method.invoke(((WebdriverInterfaceImplementor) obj).delegate, args);
	}

}
