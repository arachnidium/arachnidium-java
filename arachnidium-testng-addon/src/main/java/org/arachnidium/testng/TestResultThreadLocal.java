package org.arachnidium.testng;

import org.testng.ITestResult;

//it contains the threadlocal instance with test results
class TestResultThreadLocal {
	public static ITestResult get() {
		return results.get();
	}

	public static void set(ITestResult result) {
		results.set(result);
	}

	private static final ThreadLocal<ITestResult> results = new ThreadLocal<ITestResult>();
}
