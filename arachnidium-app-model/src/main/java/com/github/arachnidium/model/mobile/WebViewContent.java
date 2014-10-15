package com.github.arachnidium.model.mobile;

import java.net.URL;

import org.openqa.selenium.WebDriver.Navigation;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.core.components.mobile.PageTouchActions;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;

/**
 *Can be used to describe a single mobile app web view or its fragment
 */
@IfMobileContext(regExp = "WEBVIEW")
public abstract class WebViewContent extends FunctionalPart<MobileScreen> implements Navigation{

	protected final PageTouchActions touchActions;
	/**
	 * @see {@link FunctionalPart#FunctionalPart(FunctionalPart)}
	 */
	protected WebViewContent(WebViewContent parent) {
		this(parent, new HowToGetByFrames());
	}
	
	/**
	 * @see {@link FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)}
	 */
	protected WebViewContent(WebViewContent parent, HowToGetByFrames howToGetByFrames) {
		super(parent, howToGetByFrames);
		touchActions =   getComponent(PageTouchActions.class);
	}	
	
	/**
	 * @see {@link FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle)
	 */	
	protected WebViewContent(MobileScreen context){
		this(context, new HowToGetByFrames());
	}
	
	/**
	 * @see {@link FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle, HowToGetByFrames))
	 */
	protected WebViewContent(MobileScreen context, HowToGetByFrames howToGetByFrames){
		super(context, howToGetByFrames);
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
