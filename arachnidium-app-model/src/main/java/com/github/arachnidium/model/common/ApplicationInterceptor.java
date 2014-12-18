package com.github.arachnidium.model.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.openqa.selenium.By;

import com.github.arachnidium.core.fluenthandle.IHowToGetHandle;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.abstractions.ModelObjectInterceptor;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.interfaces.IDecomposableByHandles;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.util.proxy.DefaultInterceptor;

/**
 *This an iterceptor of {@link Application} methods.
 *It invokes methods. If some exception is thrown
 *it attempts to handle it implicitly 
 *
 *Also it performs the substitution of methods specified 
 *by {@link IDecomposable} and {@link IDecomposableByHandles}. 
 */
class ApplicationInterceptor extends ModelObjectInterceptor{
	/**
	 *Invokes methods and performs
	 *the substitution of methods specified 
     *by {@link IDecomposable} and {@link IDecomposableByHandles}. 
     *
     *@see MethodInterceptor#intercept(Object, Method, Object[], MethodProxy) 
     *
     *@see ModelObjectInterceptor#intercept(Object, Method, Object[], MethodProxy)
     *
     *@see DefaultInterceptor#intercept(Object, Method, Object[], MethodProxy)
     *
	 */
	@Override
	public Object intercept(Object application, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		try {
			if (!method.getName().equals(DecompositionUtil.GET_PART)) {
				return super.intercept(application, method, args, methodProxy);
			}

			List<Class<?>> paramClasses = Arrays.asList(method
					.getParameterTypes());			
			// the first parameter is a class which instance we
			Class<?> desiredClass = DecompositionUtil.extractTargetFromGetPart(method, args);// want
			Application<?, ?> app = (Application<?, ?>) application;

			// There is nothing to do if all parameters apparently defined
			if (!paramClasses.contains(IHowToGetHandle.class)
					|| !paramClasses.contains(HowToGetByFrames.class)
					|| !paramClasses.contains(long.class)|| !paramClasses.contains(By.class)) {

				ESupportedDrivers supportedDriver = app.getWebDriverEncapsulation().
						getInstantiatedSupportedDriver();
				Object[] newArgs = DecompositionUtil.getRelevantArgs2(supportedDriver, method, args, desiredClass);
				args = newArgs;
				method = MethodReadingUtil.getSuitableMethod(
						application.getClass(), DecompositionUtil.GET_PART, args);
				methodProxy = MethodReadingUtil.getMethodProxy(
						application.getClass(), method);

			}
			return super.intercept(application, method, args, methodProxy);
		} catch (Exception e) {
			throw e;
		}
	}

}
