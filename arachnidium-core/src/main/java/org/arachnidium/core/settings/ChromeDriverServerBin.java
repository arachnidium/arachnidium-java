package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.Configuration;

/**
 * Settings of chromedriver*
 * {@link https://code.google.com/p/selenium/wiki/ChromeDriver}
 * @see LocalWebDriverServiceSettings
 * @see Configuration
 * 
 * Specification:
 * 
 *   "ChromeDriver":
  {
       "folder":{
          "type":"STRING",
          "value":"path to chromedriver binary"           
      },  
       "file":{  //It is not a necessary parameter 
          "type":"STRING",
          "value":"chromedriver.exe" (or chromedriver on Mac OS X or Linux)         
      }      
  },
 * 
 */
public class ChromeDriverServerBin extends LocalWebDriverServiceSettings {
	private static final String chromeDriverGroup = "ChromeDriver";

	public ChromeDriverServerBin(Configuration configuration) {
		super(configuration, chromeDriverGroup);
	}

}
