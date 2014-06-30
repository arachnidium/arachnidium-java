package org.arachnidium.util.configuration.commonhelpers;

import org.arachnidium.util.configuration.Configuration;


/**
 * @author s.tihomirov
 * settings of IEDriver.exe
 */
public class IEDriverServerBin extends LocalWebDriverServiceSettings {
	private static final String ieDriverGroup = "IEDriver";

	public IEDriverServerBin(Configuration configuration) {
		super(configuration, ieDriverGroup);
	}

}
