package com.github.arachnidium.core.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.arachnidium.core.settings.supported.ExtendedCapabilityType;
import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * There are specified {@link WebDriver} {@link Capabilities}
 * 
 * 
 * Specification:
 * 
 * <p><br/>
 * ...<br/>
 * "DesiredCapabilities":<br/>
 *{<br/>
 * &nbsp;&nbsp;"browserName":{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some browser name"   //firefox, chrome etc.<br/> 
 * &nbsp;&nbsp;},<br/>      
 * &nbsp;&nbsp;"version": {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some version"<br/> 
 * &nbsp;&nbsp;},<br/>
 * &nbsp;&nbsp;"platform": {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"STRING",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some platform"<br/>        
 * &nbsp;&nbsp;},<br/>
 * &nbsp;&nbsp;"javascriptEnabled":{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"type":"BOOL",<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"value":"some flag"<br/>           
 * &nbsp;&nbsp;}<br/>
 * &nbsp;&nbsp;... and so on<br/>
 * }<br/>
 * ...<br/>
 * </p>
 * @see Configuration
 * @see Platform 
 * @see Capabilities
 */
public class CapabilitySettings extends AbstractConfigurationAccessHelper
implements HasCapabilities, Capabilities {

	// specified settings for capabilities
	private final String capabilityGroup = "DesiredCapabilities";
	private final DesiredCapabilities builtCapabilities = new DesiredCapabilities();
	private final String appCapability = ExtendedCapabilityType.APP;
	private final String proxyCapability = ExtendedCapabilityType.PROXY;
	private final String initialURL = ExtendedCapabilityType.BROWSER_INITIAL_URL;

	public CapabilitySettings(Configuration configuration) {
		super(configuration);
		buildCapabilities();
	}

	/**
	 * @see org.openqa.selenium.Capabilities#asMap()
	 */
	@Override
	public Map<String, ?> asMap() {
		return builtCapabilities.asMap();
	}

	private void buildCapabilities() {
		HashMap<String, Object> capabilities = getGroup(capabilityGroup);
		if (capabilities == null)
			return;

		List<String> capabilityStrings = new ArrayList<String>(
				capabilities.keySet());
		capabilityStrings.forEach((capabilityStr) -> {
			if (capabilities.get(capabilityStr) != null)
				builtCapabilities.setCapability(capabilityStr,
						capabilities.get(capabilityStr));
		});
		transformCapabilities();
	}

	/**
	 * @see org.openqa.selenium.Capabilities#getBrowserName()
	 */
	@Override
	public String getBrowserName() {
		return builtCapabilities.getBrowserName();
	}

	/**
	 * @see org.openqa.selenium.HasCapabilities#getCapabilities()
	 */
	@Override
	public Capabilities getCapabilities() {
		return builtCapabilities;
	}

	/**
	 * @see org.openqa.selenium.Capabilities#getCapability(java.lang.String)
	 */
	@Override
	public Object getCapability(String capabilityName) {
		return builtCapabilities.getCapability(capabilityName);
	}

	/**
	 * @see org.openqa.selenium.Capabilities#getPlatform()
	 */
	@Override
	public Platform getPlatform() {
		return builtCapabilities.getPlatform();
	}

	/**
	 * @see com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper#getSetting(java.lang.String)
	 */
	@Override
	public <T extends Object> T getSetting(String name) {
		return getSettingValue(capabilityGroup, name);
	}

	/**
	 * @see org.openqa.selenium.Capabilities#getVersion()
	 */
	@Override
	public String getVersion() {
		return builtCapabilities.getVersion();
	}

	/**
	 * @see org.openqa.selenium.Capabilities#is(java.lang.String)
	 */
	@Override
	public boolean is(String capabilityName) {
		return builtCapabilities.is(capabilityName);
	}

	/**
	 * @see org.openqa.selenium.Capabilities#isJavascriptEnabled()
	 */
	@Override
	public boolean isJavascriptEnabled() {
		return builtCapabilities.isJavascriptEnabled();
	}

	// transforms capabilities values if they need to be changed
	//I think it is not final implementation 
	private void transformCapabilities() {
		// transforms relative path to application into absolute
		Object pathToApp = getCapability(appCapability);
		if (pathToApp != null) {
			File app = new File(String.valueOf(pathToApp));
			builtCapabilities.setCapability(appCapability,
					app.getAbsolutePath());
		}
		//sets proxy
		Proxy proxy = Proxy.extractFrom(builtCapabilities);
		if (proxy!=null){
			builtCapabilities.setCapability(proxyCapability, proxy);
		}
		
		//sets initial URL if browser
		String startUrl = (String) builtCapabilities.getCapability(initialURL);
		if (startUrl == null){
			startUrl = getSettingValue(capabilityGroup, initialURL);
		}
		if (startUrl!=null){
			builtCapabilities.setCapability(initialURL, startUrl);
		}		
		// if other actions need to be implemented code will be below
	}
}
