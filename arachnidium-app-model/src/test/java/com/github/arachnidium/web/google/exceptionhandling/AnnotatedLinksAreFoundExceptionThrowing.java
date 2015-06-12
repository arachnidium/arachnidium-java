package com.github.arachnidium.web.google.exceptionhandling;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.abstractions.exceptionhandlers.UsedImplicitExceptionHandlers;

@UsedImplicitExceptionHandlers(areUsed = {AnnotataedTestExceptionHandler.class})
public class AnnotatedLinksAreFoundExceptionThrowing<T extends Handle> extends
		LinksAreFoundExceptionThrowing<T> {

	protected AnnotatedLinksAreFoundExceptionThrowing(T handle) {
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
