package org.arachnidium.model.mobile;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;
import org.arachnidium.model.abstractions.ModelObjectInterceptor;

class MobileApplicationInterceptor extends ModelObjectInterceptor {

	@Override
	public Object intercept(Object modelObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {		
		return super.intercept(modelObj, method, args, proxy);
	}

}
