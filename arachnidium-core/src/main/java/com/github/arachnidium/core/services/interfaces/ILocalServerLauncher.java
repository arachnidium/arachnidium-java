package com.github.arachnidium.core.services.interfaces;

import java.net.URL;

public interface ILocalServerLauncher {
	/**
	 * @return Actual URL (localhost) or the server
	 */
	public URL getLocalHost();

	/**
	 * @return Actual port of the local server
	 */
	public int getPort();

	/**
	 * @return flag of the local server state
	 */
	public boolean isLaunched();

	/**
	 * Attempts to launch server locally
	 * @throws Exception if starting is failed
	 */
	public void launch() throws Exception;

	/**
	 * @param A new port number of the local server
	 * before it is launched
	 */
	public void setPort(int port);

	/**
	 * Terminates local server
	 */
	public void stop();
}
