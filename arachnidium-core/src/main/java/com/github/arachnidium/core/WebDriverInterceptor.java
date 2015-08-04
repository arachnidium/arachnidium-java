package com.github.arachnidium.core;

import java.lang.reflect.Method;

import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import net.sf.cglib.proxy.MethodProxy;
import com.github.arachnidium.util.inheritance.MethodInheritanceUtil;
import com.github.arachnidium.util.proxy.DefaultInterceptor;

class WebDriverInterceptor extends DefaultInterceptor {

	private final Handle handle;

	WebDriverInterceptor(Handle handle) {
		this.handle = handle;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {

		if (MethodInheritanceUtil.isOverriddenFrom(method, Object.class))
			return proxy.invokeSuper(obj, args);

		WebDriver d = handle.driverEncapsulation.getWrappedDriver();
		if (MethodInheritanceUtil.isOverriddenFrom(method,
				HasCapabilities.class))
			return method.invoke(d, args);

		handle.switchToMe();
		return method.invoke(d, args);
	}

}
