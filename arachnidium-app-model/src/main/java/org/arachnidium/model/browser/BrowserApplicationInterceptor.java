package org.arachnidium.model.browser;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.core.HowToGetBrowserWindow;
import org.arachnidium.model.common.ApplicationInterceptor;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;

class BrowserApplicationInterceptor extends ApplicationInterceptor<IfBrowserDefaultPageIndex, IfBrowserURL, 
	IfBrowserPageTitle, HowToGetBrowserWindow> {

	@Override
	public Object intercept(Object modelObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {		
		return super.intercept(modelObj, method, args, proxy);
	}
}
