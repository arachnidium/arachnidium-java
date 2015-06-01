package com.github.arachnidium.model.mobile;

import java.net.URL;

import org.openqa.selenium.WebDriver.Navigation;

import com.github.arachnidium.core.MobileContextNamePatterns;
import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.core.components.mobile.PageTouchActions;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.ExpectedContext;

/**
 *Can be used to describe a single mobile app web view or its fragment
 */
@ExpectedContext(regExp = MobileContextNamePatterns.WEBVIEW)
public abstract class WebViewContent extends FunctionalPart<MobileScreen> implements Navigation{

	protected final PageTouchActions touchActions;
		
	/**
	 * @see {@link FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle, HowToGetByFrames, By))
	 */
	protected WebViewContent(MobileScreen context){
		super(context);
		touchActions =   getComponent(PageTouchActions.class);
	}	

	@InteractiveMethod
	@Override
	public void back() {
		getWrappedDriver().navigate().back();		
	}

	@InteractiveMethod
	@Override
	public void forward() {
		getWrappedDriver().navigate().forward();		
	}

	@InteractiveMethod
	@Override
	public void to(String url) {
		getWrappedDriver().navigate().to(url);		
	}

	@InteractiveMethod
	@Override
	public void to(URL url) {
		getWrappedDriver().navigate().to(url);
		
	}

	@InteractiveMethod
	@Override
	public void refresh() {
		getWrappedDriver().navigate().refresh();		
	}
	
	@InteractiveMethod
	public String getCurrentUrl(){
		return getWrappedDriver().getCurrentUrl();
	}

}
