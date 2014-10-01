package com.github.arachnidium.model.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;

import com.github.arachnidium.model.abstractions.ModelObjectInterceptor;
import com.github.arachnidium.model.common.FunctionalPart.InteractiveMethod;
import com.github.arachnidium.model.common.FunctionalPart.WithImplicitlyWait;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;

/**
 * 
 * <p>
 * This an iterceptor of {@link FunctionalPart} methods.
 * <p>
 * It invokes methods. If some exception is thrown
 * <p>
 * it attempts to handle it implicitly
 * <p>
 * <p>
 * Also it performs the substitution of methods specified
 * <p>
 * by {@link IDecomposable}. This substitution depends on
 * <p>
 * presence of {@link Frame} annotation in the class declaration
 *
 * <p>
 * {@link Frame} annotations describe the default frame path to desired
 * <p>
 * UI. It is actual for browser and hybrid mobile apps.
 */
public class InteractiveInterceptor extends ModelObjectInterceptor {
	
	public InteractiveInterceptor() {
		super();
	}

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
		}

		try {
			if (method.getName().equals(GET_PART)) {
				List<Class<?>> paramClasses = Arrays.asList(method
						.getParameterTypes());

				// if .getPart(SomeClass),
				// SomeClass can be annotated by
				// @Frame
				// so we attempt to invoke .getPart(SomeClass, HowToGetByFrames)
				if (!paramClasses.contains(HowToGetByFrames.class)) {
					HowToGetByFrames howTo = ifClassIsAnnotatedByFrames((Class<?>) args[0]);
					// the first parameter is a class which instance we want

					if (howTo != null) {
						args = ArrayUtils.add(args, howTo);
						method = ModelSupportUtil.getSuitableMethod(
								funcPart.getClass(), GET_PART, args);
						methodProxy = ModelSupportUtil.getMethodProxy(
								funcPart.getClass(), method);
					}
				}
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
