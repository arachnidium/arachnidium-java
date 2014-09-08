package org.arachnidium.core.settings;

import org.arachnidium.util.configuration.Configuration;

/**
 * Settings of IEDriverServer.exe
 * {@link https://code.google.com/p/selenium/wiki/InternetExplorerDriver}
 * @see LocalWebDriverServiceSettings
 * @see Configuration
 * 
 * Specification:
 * 
 *"IEDriver":
  {
       "folder":{
          "type":"STRING",
          "value":"path to IEDriverServer.exe"           
      },  
       "file":{  //It is not a necessary parameter 
          "type":"STRING",
          "value":"IEDriverServer.exe"           
      }       
  },
 */
public class IEDriverServerBin extends LocalWebDriverServiceSettings {
	private static final String ieDriverGroup = "IEDriver";

	public IEDriverServerBin(Configuration configuration) {
		super(configuration, ieDriverGroup);
	}

}
