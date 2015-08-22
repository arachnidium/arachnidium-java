package com.github.arachnidium.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.openqa.selenium.By;
import org.openqa.selenium.internal.WrapsDriver;

import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.interfaces.ICalculatesBy;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.core.interfaces.ISwitchesToItself;
import com.github.arachnidium.util.inheritance.MethodInheritanceUtil;
import com.github.arachnidium.util.proxy.DefaultInterceptor;

class HandleInterceptor<U extends IHowToGetHandle> extends DefaultInterceptor {
	private Handle handle;
	private final Manager<U, ?> manager;
	private final U howToGetHandle;
	private final long timeOut;
	private final By by;
	private final HowToGetByFrames howToGetByFramesStrategy;
	private final static List<Class<?>> classesThatDontRequireFocusOnTheHandle = 
			new ArrayList<Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(Object.class);
			add(ICalculatesBy.class);
			add(WrapsDriver.class);
		}

	};

	public HandleInterceptor(Manager<U, ?> manager, U howToGetHandle,
			long timeOut, By by, HowToGetByFrames howToGetByFramesStrategy) {
		this.howToGetHandle = howToGetHandle;
		this.timeOut = timeOut;
		this.manager = manager;
		this.by = by;
		this.howToGetByFramesStrategy = howToGetByFramesStrategy;
	}

	private void instantiateHandle() {
		if (handle == null && manager.isAlive) {
			handle = manager.getRealHandle(timeOut, howToGetHandle, by,
					howToGetByFramesStrategy);
			return;
		}
		if (!handle.exists() && manager.isAlive()) {
			handle = manager.getRealHandle(timeOut, howToGetHandle, by,
					howToGetByFramesStrategy);
			return;
		}
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {	
		
		if (!MethodInheritanceUtil.isOverriddenFromAny(method, 
				classesThatDontRequireFocusOnTheHandle))
			instantiateHandle();			
		
		if (handle != null
				&& !MethodInheritanceUtil.isOverriddenFromAny(method, 
						classesThatDontRequireFocusOnTheHandle) 
				&& !MethodInheritanceUtil.isOverriddenFrom(method,
						ISwitchesToItself.class) && !MethodInheritanceUtil.isOverriddenFrom(method, 
								IDestroyable.class))
			handle.switchToMe();
		
		if (!MethodInheritanceUtil.isOverriddenFrom(method, Object.class) && 
				handle != null)
			return method.invoke(handle, args);

		return super.intercept(obj, method, args, proxy);
	}

}
