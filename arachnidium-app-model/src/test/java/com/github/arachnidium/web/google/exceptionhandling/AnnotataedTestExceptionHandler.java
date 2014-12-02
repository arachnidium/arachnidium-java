package com.github.arachnidium.web.google.exceptionhandling;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.model.abstractions.ExpectectedThrowables;
import com.github.arachnidium.model.abstractions.ModelObjectExceptionHandler;

@ExpectectedThrowables(expectedThrowables = {TestException2.class})
public class AnnotataedTestExceptionHandler extends ModelObjectExceptionHandler {

	public static boolean isExceptionCatched_Static = false;
	
	public boolean isExceptionCatched = false;

	@Override
	public Object handleException(Object object, Method originalMethod,
			MethodProxy methodProxy, Object[] args, Throwable t)
			throws Throwable {
		t.printStackTrace();
		try{
			return methodProxy.invoke(object, args);
		}
		catch (Exception e){
			throw e;
		}
		finally{
			isExceptionCatched = true;
			isExceptionCatched_Static = true;
		}
	}

}
