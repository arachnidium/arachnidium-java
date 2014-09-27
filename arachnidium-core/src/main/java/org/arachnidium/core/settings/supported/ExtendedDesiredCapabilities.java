package org.arachnidium.core.settings.supported;

import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This class little bit extends {@link DesiredCapabilities}
 * It is for internal usage
 */
class ExtendedDesiredCapabilities extends DesiredCapabilities {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @return a {@link DesiredCapabilities} instance which contains:
	 * <br/>
	 * {<br/>
	 *&nbsp;&nbsp;"platformName":{ <br/>
	 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"Android"<br/>    
	 *&nbsp;&nbsp;}, <br/>    
	 *&nbsp;&nbsp;"browserName":{ <br/>
	 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"Chrome" <br/>           
	 *&nbsp;&nbsp;} <br/>
	 *&nbsp;&nbsp;"javascriptEnabled":{ <br/>
	 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"true" <br/>     
	 *&nbsp;&nbsp;}<br/>
	 *}<br/>
	 *<br/> 
	 * This is not full capability set. Other required capabilities should be set 
	 * properly. E.g. "deviceName" and so on.
	 * 
	 * @see CapabilityType
	 * @see MobileCapabilityType
	 */
	static DesiredCapabilities androidChrome(){
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		dc.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
		dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		return dc;
	}
	
	/**
	 * @return a {@link DesiredCapabilities} instance which contains:
	 * <br/>
	 * {<br/>
	 *&nbsp;&nbsp;"platformName":{ <br/>
	 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"iOS"<br/>    
	 *&nbsp;&nbsp;}, <br/>    
	 *&nbsp;&nbsp;"browserName":{ <br/>
	 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"safari" <br/>           
	 *&nbsp;&nbsp;} <br/>
	 *&nbsp;&nbsp;"javascriptEnabled":{ <br/>
	 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"true" <br/>     
	 *&nbsp;&nbsp;}<br/>
	 *}<br/>
	 *<br/> 
	 * This is not full capability set. Other required capabilities should be set 
	 * properly. E.g. "deviceName", "platformVersion" and so on.
	 * 
	 * @see CapabilityType
	 * @see MobileCapabilityType
	 */	
	static DesiredCapabilities iosSafari(){
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
		dc.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
		dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		return dc;		
	}

}
