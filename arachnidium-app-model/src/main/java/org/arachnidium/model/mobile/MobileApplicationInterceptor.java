package org.arachnidium.model.mobile;

import org.arachnidium.core.HowToGetMobileScreen;
import org.arachnidium.model.common.ApplicationInterceptor;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileDefaultContextIndex;

class MobileApplicationInterceptor extends ApplicationInterceptor<IfMobileDefaultContextIndex, IfMobileAndroidActivity, 
	IfMobileContext, HowToGetMobileScreen> {

}
