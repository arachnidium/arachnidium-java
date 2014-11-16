package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

import org.openqa.selenium.ie.InternetExplorerDriverService;

/**
 * Settings of IEDriverServer.exe
 * <p>
 * {@link https://code.google.com/p/selenium/wiki/InternetExplorerDriver}</p>
 * 
 * <p>
 * Specification:</p>
 * 
 * <p><br/>
 *"IEDriver":<br/>
 *{<br/>
 *&nbsp;&nbsp;"folder":{<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"path to IEDriverServer.exe"<br/>           
 *&nbsp;&nbsp;},<br/>  
 *&nbsp;&nbsp;"file":{  //It is not a necessary parameter<br/> 
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"IEDriverServer.exe"<br/>           
 *&nbsp;&nbsp;}<br/>       
 *},<br/>
 *</p>
 *
 *@see LocalWebDriverServiceSettings
 *@see InternetExplorerDriverService
 *@see Configuration
 */
@Group(settingGroup = "IEDriver")
public class IEDriverServerBin extends LocalWebDriverServiceSettings {

	protected IEDriverServerBin(Configuration configuration, String group) {
		super(configuration, group);
	}

}
