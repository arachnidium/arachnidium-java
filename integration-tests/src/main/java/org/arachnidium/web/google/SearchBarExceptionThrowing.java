package org.arachnidium.web.google;

import org.arachnidium.core.Handle;

public class SearchBarExceptionThrowing<T extends Handle> extends SearchBar<T>{	
	
	private boolean isMethodInvokedTwice = false;
	
	private void onInvokeTwice() {
		if (!isMethodInvokedTwice) {
			isMethodInvokedTwice = true;
			throw new TestException();
		}
		isMethodInvokedTwice = false;
	}
	
	protected SearchBarExceptionThrowing(T handle) {
		super(handle);
	}

	@InteractiveMethod
	public void performSearch(String searchString) {
		onInvokeTwice();
		super.performSearch(searchString);
	}
}
