package com.github.arachnidium.model.common;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.MethodProxy;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.abstractions.ModelObjectInterceptor;
import com.github.arachnidium.model.common.FunctionalPart.InteractiveMethod;
import com.github.arachnidium.model.common.FunctionalPart.WithImplicitlyWait;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;

/**
 * 
 * <p>
 * This an iterceptor of {@link FunctionalPart} methods.
 * It invokes methods. If some exception is thrown
 * it attempts to handle it implicitly
 * Also it performs the substitution of methods specified
 * by {@link IDecomposable}. This substitution depends on
 * presence of {@link Frame} annotation in the class declaration
 * 
 * {@link Frame} annotations describe the default frame path to desired
 * 
 * UI. It is actual for browser and hybrid mobile apps.
 */
abstract class InteractiveInterceptor extends ModelObjectInterceptor {
	
	private static void resetTimeOut(FunctionalPart<?> funcPart,
			long timeOutValue, TimeUnit timeUnit) {
		funcPart.getTimeOut().implicitlyWait(timeOutValue, timeUnit);
		funcPart.getDefaultFieldDecorator().resetImplicitlyWaitTimeOut(timeOutValue,
				timeUnit);
	}

	@Override
	public synchronized Object intercept(Object object, Method method,
			Object[] args, MethodProxy methodProxy) throws Throwable {

		FunctionalPart<?> funcPart = (FunctionalPart<?>) object;
		long timeOut = 0;
		TimeUnit timeUnit = null;
		
		boolean timeOutIsChanged = false;		
		if (method.isAnnotationPresent(InteractiveMethod.class)) {
			funcPart.switchToMe();
			
			timeOut = funcPart.getTimeOut().getImplicitlyWaitTimeOut();
			timeUnit = funcPart.getTimeOut().getImplicitlyWaitTimeUnit();
			
			funcPart.getDefaultFieldDecorator().resetImplicitlyWaitTimeOut(timeOut,
					timeUnit);
			
			// if there is customized time out
			if (method.isAnnotationPresent(WithImplicitlyWait.class)) {				
				WithImplicitlyWait withImplicitlyWait = method
						.getAnnotation(WithImplicitlyWait.class);
				long customTimeOut = withImplicitlyWait.timeOut();
				TimeUnit customTimeUnit = withImplicitlyWait.timeUnit();
				resetTimeOut(funcPart, customTimeOut, customTimeUnit);
				timeOutIsChanged = true;
			}
			//TODO implement behavior when method is annotated by @RootElement
			//and @Frame
		}

		try {
			if (method.getName().equals(DecompositionUtil.GET_PART)) {
				Class<?> target = DecompositionUtil.extractTargetFromGetPart(method, args);
				ESupportedDrivers supportedDriver = 
						funcPart.getWebDriverEncapsulation().getInstantiatedSupportedDriver();
				Object[] newArgs = DecompositionUtil.
						getRelevantArgs(supportedDriver, method, args, target);
				args = newArgs;
				method = MethodReadingUtil.getSuitableMethod(
						funcPart.getClass(), DecompositionUtil.GET_PART, args);
				methodProxy = MethodReadingUtil.getMethodProxy(
						funcPart.getClass(), method);
			}
			return super.intercept(funcPart, method, args, methodProxy);
		} catch (Exception e) {
			throw e;
		} finally {
			if (timeOutIsChanged)
				resetTimeOut(funcPart, timeOut, timeUnit);
		}
	}
}
