package com.github.arachnidium.model.mobile;

import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.model.common.ApplicationInterceptor;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;

/**
 * see {@link ApplicationInterceptor}
 *
 */
class MobileApplicationInterceptor extends ApplicationInterceptor<IfMobileDefaultContextIndex, 
    IfMobileAndroidActivity, 
	IfMobileContext, 
	HowToGetMobileScreen> {

}
