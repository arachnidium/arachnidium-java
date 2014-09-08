package org.arachnidium.core.services;

import java.net.MalformedURLException;
import java.net.URL;

import org.arachnidium.core.services.interfaces.ILocalServerLauncher;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

/**
 *
 * Launches {@link RemoteWebDriver} server locally
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

	@Override
	public int getPort() {
		return rcc.getPort();
	}

	@Override
	public boolean isLaunched() {
		if (server == null)
			return false;
		return server.getServer().isStarted();
	}

	@Override
	public void launch() throws Exception {
		try {
			server = new SeleniumServer(slowResources, rcc);
			server.start();
		} catch (Exception e) {
			throw new WebDriverException("Cann't start server on localhost!", e);
		}
	}

	@Override
	public void setPort(int port) {
		rcc.setPort(port);
	}

	@Override
	public void stop() {
		if (server == null)
			return;
		server.stop();
	}

}
