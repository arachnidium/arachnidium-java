package org.arachnidium.testng;

import org.testng.ITestResult;

//it contains the threadlocal instance with test results 
class TestResultThreadLocal {
	private static final ThreadLocal<ITestResult> results = new ThreadLocal<ITestResult>();
	
	public static void set(ITestResult result)
	{
		results.set(result);
	}
	
	public static ITestResult get()
	{
		return results.get();
	}
}
