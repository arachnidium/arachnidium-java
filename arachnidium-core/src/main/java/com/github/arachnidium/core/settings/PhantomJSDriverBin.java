package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

/**
 * Settings of phantomjs*
 * 
 * <p>
 * Specification:</p>
 * 
 * <p><br/>
 *"PhantomJSDriver":<br/>
 *{<br/>
 *&nbsp;&nbsp;"folder":{<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"path to phantomjs binary"<br/>           
 *&nbsp;&nbsp;},<br/>  
 *&nbsp;&nbsp;"file":{ //It is not a necessary parameter<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"phantomjs.exe" (or chromedriver on Mac OS X or Linux)<br/>            
 *&nbsp;&nbsp;}<br/>       
 *}<br/>  
 *</p>
 *
 * @see PhantomJSDriverService
 * @see LocalWebDriverServiceSettings
 * @see Configuration
 */
public class PhantomJSDriverBin extends LocalWebDriverServiceSettings {
	private static final String phantomJSDriverGroup = "PhantomJSDriver";

	public PhantomJSDriverBin(Configuration configuration) {
		super(configuration, phantomJSDriverGroup);
	}

}
