package org.arachnidium.util.proxy;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * The simple factory that makes usage of some CGLIB tools easily
 * 
 * Creates proxy objects using list of {@link MethodInterceptor} implementations
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
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getProxy(Class<T> clazz,
			Class<?>[] paramClasses, Object[] paramValues,
			List<MethodInterceptor> interceptors) {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallbacks(interceptors.toArray(new MethodInterceptor[] {}));
		enhancer.setSuperclass(clazz);
		T proxy = (T) enhancer.create(paramClasses, paramValues);
		return proxy;
	}
	
	/**
	 * 
	 * @param clazz Instance of defined class has to be got from
	 * @param paramClasses  An array of classes which matches to required constructor 
	 * parameter list
	 * @param paramValues An array of values which matches to required constructor 
	 * parameter list
	 * @param interceptor Some {@link MethodInterceptor} implementor instance 
	 * @return
	 */
	public static <T extends Object> T getProxy(Class<T> clazz,
			Class<?>[] paramClasses, Object[] paramValues,
			final MethodInterceptor interceptor) {
		return getProxy(clazz, paramClasses, paramValues,
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
