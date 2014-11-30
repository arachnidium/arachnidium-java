package com.github.arachnidium.core;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;
import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.util.proxy.DefaultInterceptor;

class HandleInterceptor<U extends IHowToGetHandle> extends DefaultInterceptor {
	private Handle handle;
	private final Manager<U, ?> manager;
	private final U howToGetHandle;
	private final long timeOut;
	
	public HandleInterceptor(Manager<U, ?> manager, U howToGetHandle, long timeOut) {
		this.howToGetHandle = howToGetHandle;
		this.timeOut = timeOut;
		this.manager = manager;
	}
	
	private void instantiateHandle(){
		if (handle == null && manager.isAlive){
			handle = manager.getRealHandle(timeOut, howToGetHandle);
			return;
		}
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		instantiateHandle();
		if (handle != null )
			return method.invoke(handle, args);
		
		return super.intercept(obj, method, args, proxy);
	}	

}
