package com.github.arachnidium.web.google.exceptionhandling;

import org.openqa.selenium.By;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.abstractions.exceptionhandlers.UsedImplicitExceptionHandlers;
import com.github.arachnidium.core.HowToGetByFrames;

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
	
	protected SearchBarExceptionThrowingAnnotatedMethods(T handle, HowToGetByFrames path, By by) {
		super(handle, path, by);
	}

	@InteractiveMethod
	@UsedImplicitExceptionHandlers(areUsed = AnnotataedTestExceptionHandler.class)
	public void performSearch(String searchString) {
		super.performSearch(searchString);
	}
}
