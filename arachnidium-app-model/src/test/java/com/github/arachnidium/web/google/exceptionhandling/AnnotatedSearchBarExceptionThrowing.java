package com.github.arachnidium.web.google.exceptionhandling;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.abstractions.UsedImplicitExceptionHandlers;

@UsedImplicitExceptionHandlers(areUsed = {AnnotataedTestExceptionHandler.class})
public class AnnotatedSearchBarExceptionThrowing<T extends Handle> extends SearchBarExceptionThrowing<T>{	
	
	protected AnnotatedSearchBarExceptionThrowing(T handle) {
		super(handle);
	}
	
	protected void onInvokeTwice() {
		if (!isMethodInvokedTwice) {
			isMethodInvokedTwice = true;
			throw new TestException2();
		}
		isMethodInvokedTwice = false;
	}	
}
