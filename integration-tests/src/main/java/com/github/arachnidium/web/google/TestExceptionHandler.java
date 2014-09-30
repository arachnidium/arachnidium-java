package com.github.arachnidium.web.google;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.model.abstractions.ModelObjectExceptionHandler;

public class TestExceptionHandler extends ModelObjectExceptionHandler {

	public boolean isExceptionCatched = false;
	public boolean isExceptionHandled = false;
	
	public TestExceptionHandler() {
		super(TestException.class);
	}

	@Override
	public Object handleException(Object object, Method originalMethod,
			MethodProxy methodProxy, Object[] args, Throwable t)
			throws Throwable {
		isExceptionCatched = true;
		t.printStackTrace();
		try{
			return methodProxy.invoke(object, args);
		}
		catch (Exception e){
			throw e;
		}
		finally{
			isExceptionHandled = true;
		}
	}

}
