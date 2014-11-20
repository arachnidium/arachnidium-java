package com.github.arachnidium.model.common;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;

import com.github.arachnidium.model.abstractions.ModelObjectInterceptor;
import com.github.arachnidium.model.common.FunctionalPart.InteractiveMethod;
import com.github.arachnidium.model.common.FunctionalPart.WithImplicitlyWait;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.IRootElementReader;

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
public abstract class InteractiveInterceptor<RootElementReader extends IRootElementReader> extends ModelObjectInterceptor {
	
	private static void resetTimeOut(FunctionalPart<?> funcPart,
			long timeOutValue, TimeUnit timeUnit) {
		funcPart.getTimeOut().implicitlyWait(timeOutValue, timeUnit);
		funcPart.getDefaultFieldDecorator().resetImplicitlyWaitTimeOut(timeOutValue,
				timeUnit);
	}
	
	@SuppressWarnings("unchecked")
	private Object[] getSubstitutedArgs(FunctionalPart<?> funcPart, Method method, Object[] args) throws Throwable{
		Type generic = this.getClass().getGenericSuperclass();
		ParameterizedType parameters = (ParameterizedType) generic;
				
		// the first parameter is a class which instance we
		Class<?> desiredClass = (Class<?>) args[0];// want
		// if .getPart(SomeClass),
		// SomeClass can be annotated by
		// @Frame
		// so we attempt to invoke .getPart(SomeClass, HowToGetByFrames)
		HowToGetByFrames howTo = ModelSupportUtil
				.getDefinedParameter(method, HowToGetByFrames.class,
						args);
		if (howTo == null)
			howTo = ModelSupportUtil
					.getHowToGetByFramesStrategy(desiredClass);
		
		By rootBy = ModelSupportUtil.getDefinedParameter(method, By.class, args);
		if (rootBy == null){
			Class<RootElementReader> rootElementClass = (Class<RootElementReader>) Class
					.forName(parameters.getActualTypeArguments()[0].getTypeName());
			RootElementReader reader = rootElementClass.newInstance();
			rootBy = reader.readClassAndGetBy(desiredClass, funcPart.getWrappedDriver());
		}		
		
		// the first parameter is a class which instance we want
		Object[] newArgs = new Object[] { desiredClass };
		if (howTo != null) {
			newArgs = ArrayUtils.add(newArgs, howTo);
		}
		
		if (rootBy != null){
			newArgs = ArrayUtils.add(newArgs, rootBy);
		}
		
		return newArgs;
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
				args = getSubstitutedArgs(funcPart, method, args);
				method = ModelSupportUtil.getSuitableMethod(
						funcPart.getClass(), GET_PART, args);
				methodProxy = ModelSupportUtil.getMethodProxy(
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
