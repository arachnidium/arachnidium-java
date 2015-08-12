package com.github.arachnidium.core.bean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

/**
 *@link{IWindowListener} general implementor
 * Listens to browser window events
 */
@Aspect
class AspectWindow extends AbstractAspect {

	
	public AspectWindow(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}

    @Before("execution(* com.github.arachnidium.core.interfaces.ISwitchesToItself.switchToMe(..))")
	public void beforeIsSwitchedOn(JoinPoint joinPoint) {
		//TODO
	}

    @Before("execution(* com.github.arachnidium.core.interfaces.IExtendedWindow.close(..))")
	public void beforeWindowIsClosed(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeWindowIsMaximized(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeWindowIsMoved(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeWindowIsRefreshed(JoinPoint joinPoint) {
		//TODO
	}

	public void beforeWindowIsResized(JoinPoint joinPoint) {
		//TODO
	}
	
	@After("execution(* com.github.arachnidium.core.interfaces.ISwitchesToItself.switchToMe(..))")
	public void whenIsSwitchedOn(JoinPoint joinPoint) {
		//TODO
	}


	public void whenNewHandleIsAppeared(JoinPoint joinPoint) {
		//TODO
	}


	@After("execution(* com.github.arachnidium.core.interfaces.IExtendedWindow.close(..))")
	public void whenWindowIsClosed(JoinPoint joinPoint) {
		//TODO
	}

	public void whenWindowIsMaximized(JoinPoint joinPoint) {
		//TODO
	}
	
	public void whenWindowIsMoved(JoinPoint joinPoint) {
		//TODO
	}


	public void whenWindowIsRefreshed(JoinPoint joinPoint) {
		//TODO
	}

	public void whenWindowIsResized(JoinPoint joinPoint) {
		//TODO
	}

	/**
	 * @see com.github.arachnidium.core.bean.AbstractAspect#doAround(org.aspectj.lang.ProceedingJoinPoint)
	 */
	@Override
	@Around("execution(* com.github.arachnidium.core.interfaces.IExtendedWindow.*(..)) || "
			+ "execution(* com.github.arachnidium.core.interfaces.IHasHandle.*(..)) || "
			+ "execution(* com.github.arachnidium.core.interfaces.ISwitchesToItself.*(..)) || " +
			"execution(* org.openqa.selenium.WebDriver.Window.*(..))")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		Object result = null;
		try {
			result = point.proceed();
		} catch (Exception e) {
			throw getRootCause(e);
		}
		return result;
	}

}
