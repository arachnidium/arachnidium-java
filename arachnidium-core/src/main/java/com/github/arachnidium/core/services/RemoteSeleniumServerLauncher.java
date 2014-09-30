package com.github.arachnidium.core.services;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.github.arachnidium.core.services.interfaces.ILocalServerLauncher;

/**
 *
 * Launches {@link SeleniumServer} server locally
 *
 */
public class RemoteSeleniumServerLauncher implements ILocalServerLauncher {

	private SeleniumServer server;
	private final RemoteControlConfiguration rcc;
	final boolean slowResources = false;
	final String defaultLocalHost = "http://localhost:4444/wd/hub";
	final int defaulPort = 4444;

	public RemoteSeleniumServerLauncher() {
		rcc = new RemoteControlConfiguration();
		rcc.setPort(RemoteControlConfiguration.DEFAULT_PORT);
	}

	@Override
	/**
	 * @return Local URL of the locally started {@link SeleniumServer} 
	 * @see com.github.arachnidium.core.services.interfaces.ILocalServerLauncher#getLocalHost()
	 */
	public URL getLocalHost() {
		URL localHost = null;
		try {
			localHost = new URL(defaultLocalHost.replace(
					Integer.toString(defaulPort),
					Integer.toString(rcc.getPort())));
		} catch (MalformedURLException ignored) {
		}
		return localHost;
	}

	/**
	 * @return Port number of the locally started {@link SeleniumServer} 
	 * @see com.github.arachnidium.core.services.interfaces.ILocalServerLauncher#getPort()
	 */
	@Override
	public int getPort() {
		return rcc.getPort();
	}

    /**
     * @return flag of the local {@link SeleniumServer} state
     * @see com.github.arachnidium.core.services.interfaces.ILocalServerLauncher#isLaunched()
     */
	@Override
	public boolean isLaunched() {
		if (server == null)
			return false;
		return server.getServer().isStarted();
	}

	/**
	 * Attempts to launch {@link SeleniumServer} locally
	 * @see com.github.arachnidium.core.services.interfaces.ILocalServerLauncher#launch()
	 */
	@Override
	public void launch() throws Exception {
		try {
			server = new SeleniumServer(slowResources, rcc);
			server.start();
		} catch (Exception e) {
			throw new WebDriverException("Cann't start server on localhost!", e);
		}
	}

	/**
	 * @param A new port number of the local {@link SeleniumServer}
	 * before it is launched
	 * @see com.github.arachnidium.core.services.interfaces.ILocalServerLauncher#setPort(int)
	 */
	@Override
	public void setPort(int port) {
		rcc.setPort(port);
	}

	/**
	 * Terminates local {@link SeleniumServer}
	 * @see com.github.arachnidium.core.services.interfaces.ILocalServerLauncher#stop()
	 */
	@Override
	public void stop() {
		if (server == null)
			return;
		server.stop();
	}

}
