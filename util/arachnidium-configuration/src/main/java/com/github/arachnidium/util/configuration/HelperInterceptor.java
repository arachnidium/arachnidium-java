package com.github.arachnidium.util.configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper.Setting;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class HelperInterceptor implements MethodInterceptor {
	private final List<Method> CURRENT_STACK = new ArrayList<Method>();
	private static final Class<?>[] EMPTY_PARAMS = new Class<?>[] {};
	private static final Method GET_SETTING = findGetSettingMethod();
	
	private static Method findGetSettingMethod(){
		try {
			return AbstractConfigurationAccessHelper.class.getDeclaredMethod(
					AbstractConfigurationAccessHelper.GET_SETTING_VALUE_METHOD,
					EMPTY_PARAMS);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	private String getSettingAnnotationVlue(int upperIndexToStart){
		for (int i = upperIndexToStart; i >= 0; i--){
			Method m = CURRENT_STACK.get(i);
			if (!m.isAnnotationPresent(Setting.class)){
				continue;
			}
			
			Setting settingAnnotation = m.getAnnotation(Setting.class);
			return settingAnnotation.setting();
		}
		return null;
	}

	/**
	 * This is an algorithm of {@link AbstractConfigurationAccessHelper}
	 * subclasses method interception.
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {

		CURRENT_STACK.add(method);
		try{
			if (!method.equals(GET_SETTING)){
				return proxy.invokeSuper(obj, args);
			}
			String setting = getSettingAnnotationVlue(CURRENT_STACK.size() - 1);
			if (setting == null){
				return proxy.invokeSuper(obj, args);
			}
			return ((AbstractConfigurationAccessHelper) obj).getSettingValue(setting);			
		}
		finally{
			CURRENT_STACK.remove(CURRENT_STACK.size() - 1);
		}
	}

}
