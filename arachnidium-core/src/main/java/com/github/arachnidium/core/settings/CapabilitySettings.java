package com.github.arachnidium.core.settings;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.settings.supported.ExtendedCapabilityType;
import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

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
@Group(settingGroup = "DesiredCapabilities")
public class CapabilitySettings extends AbstractConfigurationAccessHelper
implements HasCapabilities, Capabilities {
	private final DesiredCapabilities builtCapabilities = new DesiredCapabilities();
	private final String appCapability = ExtendedCapabilityType.APP;
	private final String proxyCapability = ExtendedCapabilityType.PROXY;

	protected CapabilitySettings(Configuration configuration, String group) {
		super(configuration, group);
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
		List<Field> capabilities = Arrays.asList(ExtendedCapabilityType.class.getFields());
		ExtendedCapabilityType ecp = new ExtendedCapabilityType() {
		};
		
		capabilities.forEach((capability) -> {
			String capName = null;
			try {
				capName = capability.get(ecp).toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			Object value = getSettingValue(capName);
			if (value != null){
				builtCapabilities.setCapability(capName, value);
			}
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
		// if other actions need to be implemented code will be below
	}
}
