package org.arachnidium.model.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.arachnidium.model.abstractions.ModelObjectInterceptor;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.annotations.classdeclaration.Frame;

/**
 * 
 *<p>This an iterceptor of {@link FunctionalPart} methods.
 *<p>It invokes methods. If some exception is thrown
 *<p>it attempts to handle it implicitly 
 *<p>
 *<p>Also it performs the substitution of methods specified 
 *<p>by {@link IDecomposable}. This substitution depends on 
 *<p>presence of {@link Frame} annotations in the class declareation
 *
 *<p>{@link Frame} annotations describe the default frame path to required 
 *<p>UI. It is actual for browser and hybrid mobile apps.
 */
public class InteractiveInterceptor extends ModelObjectInterceptor {

	public InteractiveInterceptor() {
		super();
	}

	@Override
	public synchronized Object intercept(Object funcPart, Method method,
			Object[] args, MethodProxy methodProxy) throws Throwable {
		try {
			if (method
					.isAnnotationPresent(FunctionalPart.InteractiveMethod.class))
				// if there are actions with a page
				((FunctionalPart<?>) funcPart).switchToMe();

			if (method.getName().equals(GET_PART)) {
				List<Class<?>> paramClasses = Arrays.asList(method
						.getParameterTypes());

				// if .getPart(SomeClass), //SomeClass can be annotated by
				// @Frame
				// so we attempt to invoke .getPart(SomeClass, HowToGetByFrames)
				if (!paramClasses.contains(HowToGetByFrames.class)) {
					HowToGetByFrames howTo = ifClassIsAnnotatedByFrames((Class<?>) args[0]);
							//the first parameter is a class which instance we want

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
		}
	}
}
