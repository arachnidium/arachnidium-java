package org.arachnidium.model.common;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.arachnidium.model.abstractions.ModelObjectInterceptor;
import org.arachnidium.model.support.HowToGetByFrames;

public class InteractiveInterceptor extends ModelObjectInterceptor {

	public InteractiveInterceptor() {
		super();
	}

	/**
	 * Interceptor that sets focus on pages to interact with.
	 */
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
					HowToGetByFrames howTo = ifClassIsAnnotatedByFrames(paramClasses
							//the first parameter is a class which instance we want
							.get(0));

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
