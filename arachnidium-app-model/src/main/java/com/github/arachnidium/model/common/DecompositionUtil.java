package com.github.arachnidium.model.common;

import java.lang.reflect.Constructor;

import net.sf.cglib.proxy.MethodInterceptor;

import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.util.proxy.EnhancedProxyFactory;

abstract class DecompositionUtil {

	/**
	 * Creation of any decomposable part of application
	 */
	protected static <T extends IDecomposable> T get(Class<T> partClass, 
			Class<? extends InteractiveInterceptor<?>> requiredInterceptor,
			Object[] paramValues) {
		try{
			Constructor<?> constrauctor = requiredInterceptor.getConstructors()[0];
			constrauctor.setAccessible(true);
			T decomposable = EnhancedProxyFactory.getProxy(partClass,
					ModelSupportUtil.getParameterClasses(paramValues, partClass), paramValues,
					(MethodInterceptor) constrauctor.newInstance());
			return decomposable;
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}		
	}
}
