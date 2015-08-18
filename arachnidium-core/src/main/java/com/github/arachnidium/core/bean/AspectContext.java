package com.github.arachnidium.core.bean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.ScreenOrientation;

import com.github.arachnidium.core.interfaces.IHasHandle;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

/**
 * @link{IContextListener general implementor. It listens to mobile context
 *                        events
 */
@Aspect
class AspectContext extends AbstractAspect {

	private final DefaultContextListener defaultContextListener;
	
	public AspectContext(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
		defaultContextListener = new DefaultContextListener(configurationWrapper);
	}

	@Before("execution(* com.github.arachnidium.core.interfaces.ISwitchesToItself.switchToMe(..))")
	public void beforeIsSwitchedOn(JoinPoint joinPoint) throws Throwable{
		try {
			IHasHandle handle = (IHasHandle) joinPoint.getTarget();
			defaultContextListener.beforeIsSwitchedOn(handle);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}

	@After("execution(* com.github.arachnidium.core.interfaces.ISwitchesToItself.switchToMe(..))")
	public void whenIsSwitchedOn(JoinPoint joinPoint) throws Throwable{
		try {
			IHasHandle handle = (IHasHandle) joinPoint.getTarget();
			defaultContextListener.whenIsSwitchedOn(handle);
		}catch(Throwable t){
			throw getRootCause(t);
		}
			
	}

	@After("execution(* com.github.arachnidium.core.interfaces.IHasHandle.whenIsCreated(..))")
	public void whenNewHandleIsAppeared(JoinPoint joinPoint) throws Throwable{
		try {
			IHasHandle handle = (IHasHandle) joinPoint.getTarget();
			defaultContextListener.whenNewHandleIsAppeared(handle);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}
	
	@Override
	@Around("execution(* com.github.arachnidium.core.interfaces.IHasHandle.*(..)) || "
			+ "execution(* com.github.arachnidium.core.interfaces.ISwitchesToItself.*(..)) || "
			+ "execution(* org.openqa.selenium.Rotatable.*(..))")
	public Object doAround(ProceedingJoinPoint point) throws Throwable {
		Object result = null;
		try {
			result = point.proceed();
		} catch (Exception e) {
			throw getRootCause(e);
		}
		return result;
	}
	
	@Before("execution(* org.openqa.selenium.Rotatable.rotate(..))")
	public void beforeIsRotated(JoinPoint joinPoint) throws Throwable{
		try {
			IHasHandle handle = (IHasHandle) joinPoint.getTarget();
			ScreenOrientation orientation = (ScreenOrientation) joinPoint.getArgs()[0];
			defaultContextListener.beforeIsRotated(handle, orientation);
		}catch(Throwable t){
			throw getRootCause(t);
		}
		
	}

	@After("execution(* org.openqa.selenium.Rotatable.rotate(..))")
	public void whenIsRotated(JoinPoint joinPoint) throws Throwable{
		try {
			IHasHandle handle = (IHasHandle) joinPoint.getTarget();
			ScreenOrientation orientation = (ScreenOrientation) joinPoint.getArgs()[0];
			defaultContextListener.whenIsRotated(handle, orientation);
		}catch(Throwable t){
			throw getRootCause(t);
		}
	}
}
