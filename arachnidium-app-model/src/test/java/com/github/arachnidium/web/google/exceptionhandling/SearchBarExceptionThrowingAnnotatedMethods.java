package com.github.arachnidium.web.google.exceptionhandling;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.abstractions.exceptionhandlers.UsedImplicitExceptionHandlers;

public class SearchBarExceptionThrowingAnnotatedMethods<T extends Handle> extends SearchBarExceptionThrowing<T>{	
	
	boolean isMethodInvokedTwice = false;
	
	@Override
	protected void onInvokeTwice() {
		if (!isMethodInvokedTwice) {
			isMethodInvokedTwice = true;
			throw new TestException2();
		}
		isMethodInvokedTwice = false;
	}
	
	protected SearchBarExceptionThrowingAnnotatedMethods(T handle) {
		super(handle);
	}

	@InteractiveMethod
	@UsedImplicitExceptionHandlers(areUsed = AnnotataedTestExceptionHandler.class)
	public void performSearch(String searchString) {
		super.performSearch(searchString);
	}
}
