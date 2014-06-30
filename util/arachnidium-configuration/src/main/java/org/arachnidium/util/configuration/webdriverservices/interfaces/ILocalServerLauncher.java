package org.arachnidium.util.configuration.webdriverservices.interfaces;

import java.net.URL;
import org.arachnidium.util.configuration.interfaces.IConfigurable;

/**
 * Launches remote server locally
 *
 */
public interface ILocalServerLauncher extends IConfigurable {
	public void launch() throws Exception;
	public boolean isLaunched();
	public int getPort();
	public void setPort(int port);
	public void stop();
	public URL getLocalHost();
}
