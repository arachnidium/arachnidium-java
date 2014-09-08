package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.Configuration;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

/**
 * Settings of phantomjs*
 * @see PhantomJSDriverService
 * @see LocalWebDriverServiceSettings
 * 
 * Specification:
 * 
 * "PhantomJSDriver":
  {
       "folder":{
          "type":"STRING",
          "value":"path to phantomjs binary"           
      },  
       "file":{ //It is not a necessary parameter
          "type":"STRING",
          "value":"phantomjs.exe" (or chromedriver on Mac OS X or Linux)            
      }       
  }  
 */
public class PhantomJSDriverBin extends LocalWebDriverServiceSettings {
	private static final String phantomJSDriverGroup = "PhantomJSDriver";

	public PhantomJSDriverBin(Configuration configuration) {
		super(configuration, phantomJSDriverGroup);
	}

}
