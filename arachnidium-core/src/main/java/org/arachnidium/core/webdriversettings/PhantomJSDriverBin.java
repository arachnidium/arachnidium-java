package org.arachnidium.core.webdriversettings;

import org.arachnidium.util.configuration.Configuration;

public class PhantomJSDriverBin extends LocalWebDriverServiceSettings {
	private static final String phantomJSDriverGroup = "PhantomJSDriver";

	public PhantomJSDriverBin(Configuration configuration) {
		super(configuration, phantomJSDriverGroup);
	}

}
