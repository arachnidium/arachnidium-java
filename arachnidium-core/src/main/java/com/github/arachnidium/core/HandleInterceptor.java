package com.github.arachnidium.core;

import java.lang.reflect.Method;

import org.openqa.selenium.By;

import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.interfaces.IDestroyable;
import com.github.arachnidium.core.interfaces.ISwitchesToItself;
import com.github.arachnidium.util.proxy.DefaultInterceptor;

class HandleInterceptor<U extends IHowToGetHandle> extends DefaultInterceptor {
	private Handle handle;
	private final Manager<U, ?> manager;
	private final U howToGetHandle;
	private final long timeOut;
	private final By by;
	private final HowToGetByFrames howToGetByFramesStrategy;
	
	public HandleInterceptor(Manager<U, ?> manager, U howToGetHandle, long timeOut,
			By by, HowToGetByFrames howToGetByFramesStrategy) {
		this.howToGetHandle = howToGetHandle;
		this.timeOut = timeOut;
		this.manager = manager;
		this.by = by;
		this.howToGetByFramesStrategy = howToGetByFramesStrategy;
	}
	
	private void instantiateHandle(){
		if (handle == null && manager.isAlive){
			handle = manager.getRealHandle(timeOut, howToGetHandle, 
					by, howToGetByFramesStrategy);
			return;
		}
		if (!handle.exists() && manager.isAlive()){
			handle = manager.getRealHandle(timeOut, howToGetHandle, 
					by, howToGetByFramesStrategy);
			return;
		}
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		instantiateHandle();
		Class<?> declaringClass = method.getDeclaringClass();
		if (handle != null && !declaringClass.equals(Object.class)){
			if (!declaringClass.equals(IDestroyable.class) && 
					!declaringClass.equals(ISwitchesToItself.class)){
				handle.switchToMe();
			}
			if (!declaringClass.equals(Object.class))
				return method.invoke(handle, args);
		}
		return super.intercept(obj, method, args, proxy);
	}	

}
