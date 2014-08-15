package org.arachnidium.core.services.interfaces;

import java.net.URL;

import org.arachnidium.util.configuration.interfaces.IConfigurable;

/**
 * Launches remote server locally
 *
 */
public interface ILocalServerLauncher extends IConfigurable {
	public URL getLocalHost();

	public int getPort();

	public boolean isLaunched();

	public void launch() throws Exception;

	public void setPort(int port);

	public void stop();
}
