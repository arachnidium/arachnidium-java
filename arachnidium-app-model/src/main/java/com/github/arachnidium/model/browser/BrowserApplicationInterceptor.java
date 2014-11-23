package com.github.arachnidium.model.browser;

import com.github.arachnidium.core.HowToGetBrowserWindow;
import com.github.arachnidium.model.common.ApplicationInterceptor;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserDefaultPageIndex;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;

/**
 * @see ApplicationInterceptor
 */
class BrowserApplicationInterceptor extends ApplicationInterceptor<IfBrowserDefaultPageIndex, 
    IfBrowserURL, 
	IfBrowserPageTitle, 
	HowToGetBrowserWindow> {
}
