package org.arachnidium.core.components.bydefault;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.util.proxy.DefaultInterceptor;

class ComponentInterceptor extends DefaultInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return method.invoke(((WebdriverInterfaceImplementor) obj).delegate,
				args);
	}

}
