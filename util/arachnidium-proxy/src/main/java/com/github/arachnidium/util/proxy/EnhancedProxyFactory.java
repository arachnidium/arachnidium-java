/*
 +Copyright 2014 Arachnidium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package com.github.arachnidium.util.proxy;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * The simple factory that makes usage of some CGLIB tools easily
 * 
 * Creates proxy objects by the list of {@link MethodInterceptor} implementations
 * and {@link Enhancer}
 *
 */
public abstract class EnhancedProxyFactory {
	
	/**
	 * @param clazz Instance of defined class has to be got from
	 * @param paramClasses An array of classes which matches to required constructor 
	 * parameter list
	 * @param paramValues An array of values which matches to required constructor 
	 * parameter list
	 * @param interceptors A list of {@link MethodInterceptor} instances
	 * @return A proxy instance of the defined class
	 */
	public static <T extends Object> T getProxy(Class<T> clazz,
			Class<?>[] paramClasses, Object[] paramValues,
			List<MethodInterceptor> interceptors) {
		Enhancer enhancer = new Enhancer();
		return getProxy(enhancer, clazz, paramClasses, paramValues, interceptors);
	}
	
	/**
	 * 
	 * @param clazz Instance of defined class has to be got from
	 * @param paramClasses  An array of classes which matches to required constructor 
	 * parameter list
	 * @param paramValues An array of values which matches to required constructor 
	 * parameter list
	 * @param interceptor Some {@link MethodInterceptor} implementor instance 
	 * @return A proxy instance of the defined class
	 */
	public static <T extends Object> T getProxy(Class<T> clazz,
			Class<?>[] paramClasses, Object[] paramValues,
			final MethodInterceptor interceptor) {
		return getProxy(new Enhancer(), clazz, paramClasses, paramValues,
				interceptor);
	}
	
	/**
	 * 
	 * @param enhancer is an instance of {@link Enhancer} superclass
	 * @param clazz Instance of defined class has to be got from
	 * @param paramClasses An array of classes which matches to required constructor 
	 * parameter list
	 * @param paramValues An array of values which matches to required constructor 
	 * parameter list
	 * @param interceptors A list of {@link MethodInterceptor} instances
	 * @return A proxy instance of the defined class
	 */
	public static <T extends Object> T getProxy(Enhancer enhancer, Class<T> clazz,
			Class<?>[] paramClasses, Object[] paramValues,
			List<MethodInterceptor> interceptors) {
		enhancer.setCallbacks(interceptors.toArray(new MethodInterceptor[] {}));
		enhancer.setSuperclass(clazz);
		@SuppressWarnings("unchecked")
		T proxy = (T) enhancer.create(paramClasses, paramValues);
		return proxy;
		
	}
	
	public static <T extends Object> T getProxy(Enhancer enhancer, Class<T> clazz,
			Class<?>[] paramClasses, Object[] paramValues,
			final MethodInterceptor interceptor) {
		return getProxy(enhancer, clazz, paramClasses, paramValues,
				new ArrayList<MethodInterceptor>() {
					private static final long serialVersionUID = 1L;
					{
						add(interceptor);
					}
				});
		
	}	

	private EnhancedProxyFactory() {
		super();
	}

}
