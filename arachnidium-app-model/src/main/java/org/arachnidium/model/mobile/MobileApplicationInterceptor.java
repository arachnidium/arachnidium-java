package org.arachnidium.model.mobile;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.core.HowToGetMobileScreen;
import org.arachnidium.model.common.ApplicationInterceptor;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;

class MobileApplicationInterceptor extends ApplicationInterceptor<IfMobileDefaultContextIndex, IfMobileAndroidActivity, 
	IfMobileContext, HowToGetMobileScreen> {

	@Override
	public Object intercept(Object modelObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {		
		return super.intercept(modelObj, method, args, proxy);
	}

}
