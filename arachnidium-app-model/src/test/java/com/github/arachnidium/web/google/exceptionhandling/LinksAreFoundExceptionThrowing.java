package com.github.arachnidium.web.google.exceptionhandling;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.web.google.LinksAreFound;

public class LinksAreFoundExceptionThrowing<T extends Handle> extends
		LinksAreFound<T> {
	boolean isMethodInvokedTwice = false;

	protected LinksAreFoundExceptionThrowing(T handle) {
		super(handle);
	}

	protected void onInvokeTwice() {
		if (!isMethodInvokedTwice) {
			isMethodInvokedTwice = true;
			throw new TestException();
		}
		isMethodInvokedTwice = false;
	}

	@InteractiveMethod
	public void openLinkByIndex(int index) {
		onInvokeTwice();
		super.openLinkByIndex(index);
	}

	@InteractiveMethod
	public int getLinkCount() {
		onInvokeTwice();
		return super.getLinkCount();
	}

	@InteractiveMethod
	public void clickOnLinkByIndex(int index) {
		onInvokeTwice();
		super.clickOnLinkByIndex(index);
	}

}
