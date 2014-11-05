package com.github.arachnidium.model.common;

import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.util.proxy.EnhancedProxyFactory;

abstract class DecompositionUtil {

	/**
	 * Creation of any decomposable part of application
	 */
	protected static <T extends IDecomposable> T get(Class<T> partClass,
			Object[] paramValues) {
		T decomposable = EnhancedProxyFactory.getProxy(partClass,
				ModelSupportUtil.getParameterClasses(paramValues, partClass), paramValues,
				new InteractiveInterceptor());
		return decomposable;
	}

}
