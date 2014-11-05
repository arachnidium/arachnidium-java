package com.github.arachnidium.core.settings.supported;

import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.remote.CapabilityType;

/**
 * It combines Selenium and Appium capabilities
 * and contains some more options
 */
public interface ExtendedCapabilityType extends CapabilityType, MobileCapabilityType{

	/**
	 * Browser will navigate to this URL when it is started.
	 * "initialUrl"
	 */
	String BROWSER_INITIAL_URL = "initialUrl";
	
	//proxy parameters
	/**
	 * "proxyType"
	 * Sets the proxy type, useful for forcing direct connection on Linux.
	 * 
	 * @see ProxyType
	 */
	String PROXY_TYPE = "proxyType";
	/**
	 * "ftpProxy"
	 *  It is the proxy host. Expected format is <code>hostname.com:1234</code>
	 */
	String FTP_PROXY = "ftpProxy";
	/**
	 * "httpProxy"
	 * It the proxy host, expected format is <code>hostname:1234</code>
	 */
	String HTTP_PROXY = "httpProxy";
	/**
	 * "httpProxy"
	 * It is the proxy bypass (noproxy) addresses
	 */
	String NO_PROXY = "noProxy";
	/**
	 * "sslProxy"
	 * It is the proxy host, expected format is <code>hostname.com:1234</code>
	 */
	String SSL_PROXY = "sslProxy";
	/**
	 * "socksProxy"
	 * It is the proxy host, expected format is <code>hostname.com:1234</code>
	 */
	String SOCKS_PROXY = "socksProxy";
	/**
	 * "socksUsername"
	 * It is the SOCKS proxy's username
	 */
	String SOCKS_USER_NAME = "socksUsername";
	/**
	 * "socksPassword"
	 * It is the SOCKS proxy's password
	 */
	String SOCKS_PASSWORD = "socksPassword";
	/**
	 * "proxyAutoconfigUrl"
	 * It is the URL for proxy auto-configuration. Expected format is
     * <code>http://hostname.com:1234/pacfile</code>.  This is required if proxy type is
     * set to {@link ProxyType#PAC}, ignored otherwise.
	 */
	String PROXY_AUTOCONFIG_URL = "proxyAutoconfigUrl";
	/**
	 * "autodetect"
	 * Specifies whether to autodetect proxy settings.
	 * Boolean
	 */
	String AUTODETECT = "autodetect";
	
}
