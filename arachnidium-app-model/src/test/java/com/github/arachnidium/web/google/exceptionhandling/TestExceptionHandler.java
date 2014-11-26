package com.github.arachnidium.web.google.exceptionhandling;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.model.abstractions.ModelObjectExceptionHandler;

public class TestExceptionHandler extends ModelObjectExceptionHandler {

	public boolean isExceptionCatched = false;
	
	public TestExceptionHandler() {
		super(TestException.class);
	}

	@Override
	public Object handleException(Object object, Method originalMethod,
			MethodProxy methodProxy, Object[] args, Throwable t)
			throws Throwable {
		if (!isThrowableInList(t.getClass())){
			throw t;
		}

		t.printStackTrace();
		try{
			return methodProxy.invoke(object, args);
		}
		catch (Exception e){
			throw e;
		}
		finally{
			isExceptionCatched = true;
		}
	}

}
