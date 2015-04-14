package com.github.arachnidium.web.google.exceptionhandling;

import org.openqa.selenium.By;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.abstractions.exceptionhandlers.UsedImplicitExceptionHandlers;
import com.github.arachnidium.core.HowToGetByFrames;

@UsedImplicitExceptionHandlers(areUsed = {AnnotataedTestExceptionHandler.class})
public class AnnotatedLinksAreFoundExceptionThrowing<T extends Handle> extends
		LinksAreFoundExceptionThrowing<T> {

	protected AnnotatedLinksAreFoundExceptionThrowing(T handle, HowToGetByFrames path, By by) {
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
