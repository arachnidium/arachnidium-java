package com.github.arachnidium.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.interfaces.ICalculatesBy;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.core.interfaces.IHasSearchContext;
import com.github.arachnidium.core.interfaces.ISwitchesToItself;
import com.github.arachnidium.util.proxy.DefaultInterceptor;

class HandleInterceptor<U extends IHowToGetHandle> extends DefaultInterceptor {
	private Handle handle;
	private final Manager<U, ?> manager;
	private final U howToGetHandle;
	private final long timeOut;
	private final By by;
	private final HowToGetByFrames howToGetByFramesStrategy;
	private final static List<Class<?>> classesThatDontRequireFocusOnTheHandle = new ArrayList<Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(Object.class);
			add(IHasSearchContext.class);
			add(ICalculatesBy.class);
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
		Class<?> declaringClass = method.getDeclaringClass();
		if (!classesThatDontRequireFocusOnTheHandle.
				contains(declaringClass)){
			instantiateHandle();			
		}
		
		if (handle != null
				&& !classesThatDontRequireFocusOnTheHandle.
				contains(declaringClass) && !declaringClass.
				equals(ISwitchesToItself.class) && !declaringClass.
				equals(IDestroyable.class))
			handle.switchToMe();
		
		if (!declaringClass.equals(Object.class) && handle != null)
			return method.invoke(handle, args);

		return super.intercept(obj, method, args, proxy);
	}

}
