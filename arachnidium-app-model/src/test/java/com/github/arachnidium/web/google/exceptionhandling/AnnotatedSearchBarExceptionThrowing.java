package com.github.arachnidium.web.google.exceptionhandling;

import org.openqa.selenium.By;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.abstractions.UsedImplicitExceptionHandlers;
import com.github.arachnidium.model.support.HowToGetByFrames;

@UsedImplicitExceptionHandlers(areUsed = {AnnotataedTestExceptionHandler.class})
public class AnnotatedSearchBarExceptionThrowing<T extends Handle> extends SearchBarExceptionThrowing<T>{	
	
	protected AnnotatedSearchBarExceptionThrowing(T handle, HowToGetByFrames path, By by) {
		super(handle, path, by);
	}
	
	protected void onInvokeTwice() {
		if (!isMethodInvokedTwice) {
			isMethodInvokedTwice = true;
			throw new TestException2();
		}
		isMethodInvokedTwice = false;
	}	
}
