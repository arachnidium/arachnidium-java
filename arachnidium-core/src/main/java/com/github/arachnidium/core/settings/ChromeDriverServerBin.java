package com.github.arachnidium.core.settings;

import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

import org.openqa.selenium.chrome.ChromeDriverService;

/**
 * Settings of chromedriver*
 * <p>
 * {@link https://code.google.com/p/selenium/wiki/ChromeDriver}</p>
 * 
 * <p>
 * Specification:</p>
 * 
 * <p><br/>
 *"ChromeDriver":<br/>
 *{<br/>
 *&nbsp;&nbsp;"folder":{<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"path to chromedriver binary"<br/>           
 *&nbsp;&nbsp;},<br/>  
 *&nbsp;&nbsp;"file":{  //It is not a necessary parameter<br/> 
 *&nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 *&nbsp;&nbsp;&nbsp;&nbsp;"value":"chromedriver.exe" (or chromedriver on Mac OS X or Linux)<br/>         
 *&nbsp;&nbsp;}<br/>      
 *},<br/>
 *</p>
 * 
 * @see LocalWebDriverServiceSettings
 * @see ChromeDriverService
 * @see Configuration
 */
@Group(settingGroup = "ChromeDriver")
public class ChromeDriverServerBin extends LocalWebDriverServiceSettings {
	protected ChromeDriverServerBin(Configuration configuration, String group) {
		super(configuration, group);
	}

}
