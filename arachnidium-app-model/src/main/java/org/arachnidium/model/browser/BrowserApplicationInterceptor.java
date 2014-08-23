package org.arachnidium.model.browser;

import org.arachnidium.core.HowToGetBrowserWindow;
import org.arachnidium.model.common.ApplicationInterceptor;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;

class BrowserApplicationInterceptor extends ApplicationInterceptor<IfBrowserDefaultPageIndex, IfBrowserURL, 
	IfBrowserPageTitle, HowToGetBrowserWindow> {
}
