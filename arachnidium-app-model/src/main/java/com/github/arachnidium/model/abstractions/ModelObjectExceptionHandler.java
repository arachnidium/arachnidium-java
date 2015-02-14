package com.github.arachnidium.model.abstractions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.github.arachnidium.model.interfaces.IModelObjectExceptionHandler;
import com.github.arachnidium.util.reflect.annotations.AnnotationUtil;

/**
 * It is the abstraction which describes the process of 
 * implicit and automated exception handling
 * 
 * @see IModelObjectExceptionHandler
 * @see MethodProxy
 * @see MethodInterceptor
 */
public abstract class ModelObjectExceptionHandler implements
IModelObjectExceptionHandler {
	private List<Class<? extends Throwable>> throwableList = new ArrayList<Class<? extends Throwable>>();
	private String expectedMessagePattern;

	/**
	 * @param tClass is the class of exception which should be caught and
	 * handled 
	 */
	public ModelObjectExceptionHandler(Class<? extends Throwable> tClass) {
		addThrowableClass(tClass);
	}

	public ModelObjectExceptionHandler() {
		super();
		ExpectectedThrowables[] expectectedThrowables = AnnotationUtil.
				getAnnotations(ExpectectedThrowables.class, 
				this.getClass(), true);		
		if (expectectedThrowables.length != 0){
			ExpectectedThrowables et = expectectedThrowables[0];
			throwableList.addAll(Arrays.asList(et.expectedThrowables()));
		}
	}
	
	@Override
	public abstract Object handleException(Object object,
			Method originalMethod, MethodProxy methodProxy, Object[] args,
			Throwable t) throws Throwable;

	/**
	 * 
	 * @param tClass is the class of exception which would be handled
	 * @return flag 
	 *    <code>true</code> if exception can be handled by this {@link ModelObjectExceptionHandler}
	 */
	public boolean isThrowableInList(Class<? extends Throwable> tClass) {
		return throwableList.contains(tClass);
	}
	
	@Override
	public void addThrowableClass(Class<? extends Throwable> tClass){
		throwableList.add(tClass);
	}
	
	/**
	 * This methods sets/changes the expected exception message pattern.
	 * 
	 * @param expectedMessagePattern  is the regular expression that helps to detect the matching of a message text 
	 * of a caught exception
	 */
	public void setExpectedMessagePattern(String expectedMessagePattern){
		this.expectedMessagePattern = expectedMessagePattern;
	}
	
	String getExpectedMessagePattern(){
		return expectedMessagePattern;
	}
	
	boolean doesExceptionMessageMatch(String theGivenMessage){
		Pattern p = Pattern.compile(expectedMessagePattern);
		Matcher m = p.matcher(theGivenMessage);
		return m.find();
	}
}
