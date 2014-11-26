package com.github.arachnidium.web.google.exceptionhandling;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.web.google.SearchBar;

public class SearchBarExceptionThrowing<T extends Handle> extends SearchBar<T>{	
	
	boolean isMethodInvokedTwice = false;
	
	protected void onInvokeTwice() {
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
