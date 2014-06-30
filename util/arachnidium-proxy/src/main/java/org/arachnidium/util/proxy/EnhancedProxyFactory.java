package org.arachnidium.util.proxy;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;


/**
 * Creates proxy objects using list of {@link MethodInterceptor} implementations
 * and {@link Enhancer}}
 *
 */
public final class EnhancedProxyFactory {
	private EnhancedProxyFactory(){
		super();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getProxy(Class<T> clazz,
			Class<?>[] paramClasses, Object[] paramValues, List<MethodInterceptor> interceptors) {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallbacks(interceptors.toArray(new MethodInterceptor[]{}));
		enhancer.setSuperclass(clazz);
		T proxy = (T) enhancer.create(paramClasses, paramValues);
		return proxy;
	}
	
	public static <T extends Object> T getProxy(Class<T> clazz,
			Class<?>[] paramClasses, Object[] paramValues, final MethodInterceptor interceptor) {
		return getProxy(clazz, paramClasses, paramValues, new ArrayList<MethodInterceptor>(){
			private static final long serialVersionUID = 1L;
			{
				add(interceptor);
			}
		});
	}

}
