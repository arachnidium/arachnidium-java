package com.github.arachnidium.core.bean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.github.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

/**
 * @link{IContextListener general implementor. It listens to mobile context
 *                        events
 */
@Aspect
class AspectContext extends AbstractAspect {

	public AspectContext(IConfigurationWrapper configurationWrapper) {
		super(configurationWrapper);
	}

	public void beforeIsSwitchedOn(JoinPoint joinPoint) {
		//TODO
	}

	public void whenIsSwitchedOn(JoinPoint joinPoint) {
		//TODO
	}

	public void whenNewHandleIsAppeared(JoinPoint joinPoint) {
		//TODO
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
	
	public void beforeIsRotated(JoinPoint joinPoint) {
		//TODO
	}

	public void whenIsRotated(JoinPoint joinPoint) {
		//TODO
	}
}
