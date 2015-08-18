package com.github.arachnidium.core.bean;

import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.ProceedingJoinPoint;

import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

/**
 * This aspect is used by listeners
 */
public abstract class AbstractAspect {

	final IConfigurationWrapper configurationWrapper;

	
	public AbstractAspect(IConfigurationWrapper configurationWrapper){
		this.configurationWrapper = configurationWrapper;
	}
	
	/**
	 * This abstract method will implement logic of the listening. 
	 * 
	 * @param point is the {@link ProceedingJoinPoint} instance
	 * @return Object that has been returned by target method
	 * @throws Throwable
	 * 
	 * @see {@link ProceedingJoinPoint}
	 */
	public abstract Object doAround(ProceedingJoinPoint point)  throws Throwable;
	
	/**
	 * If some exception is thrown its cause will be got and thrown instead of 
	 * it
	 * @param thrown Is a {@link Throwable} instance which is thrown 
	 * @return The root cause of the thrown exception
	 */
	protected static Throwable getRootCause(Throwable thrown){
		Class<? extends Throwable> throwableClass = thrown.getClass();
		if (!InvocationTargetException.class.equals(throwableClass) && !RuntimeException.class.equals(throwableClass)){
			return thrown;
		}
		if (thrown.getCause() != null){
			return getRootCause(thrown.getCause());
		}
		return thrown;
	}

}
