package com.github.arachnidium.web.google.exceptionhandling;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.abstractions.exceptionhandlers.UsedImplicitExceptionHandlers;

public class LinksAreFoundExceptionThrowingAnnotatedMethods<T extends Handle> extends
	LinksAreFoundExceptionThrowing<T> {
	boolean isMethodInvokedTwice = false;

	protected LinksAreFoundExceptionThrowingAnnotatedMethods(T handle) {
		super(handle);
	}

	@Override
	protected void onInvokeTwice() {
		if (!isMethodInvokedTwice) {
			isMethodInvokedTwice = true;
			throw new TestException2();
		}
		isMethodInvokedTwice = false;
	}

	@InteractiveMethod
	@UsedImplicitExceptionHandlers(areUsed = AnnotataedTestExceptionHandler.class)
	public void openLinkByIndex(int index) {
		super.openLinkByIndex(index);
	}

	@InteractiveMethod
	@UsedImplicitExceptionHandlers(areUsed = AnnotataedTestExceptionHandler.class)
	public int getLinkCount() {
		return super.getLinkCount();
	}

	@InteractiveMethod
	@UsedImplicitExceptionHandlers(areUsed = AnnotataedTestExceptionHandler.class)
	public void clickOnLinkByIndex(int index) {
		super.clickOnLinkByIndex(index);
	}

}
