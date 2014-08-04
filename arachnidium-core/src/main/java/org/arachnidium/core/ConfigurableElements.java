package org.arachnidium.core;

import java.util.ArrayList;
import java.util.List;

import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IConfigurable;
import org.arachnidium.util.configuration.interfaces.IConfigurationWrapper;

/**
 * @author s.tihomirov Container for all configurable objects that are created
 *         with @Link{WebDriverEncapsulation}
 */
class ConfigurableElements implements IConfigurable, IConfigurationWrapper {

	private final List<IConfigurable> configurableList = new ArrayList<IConfigurable>();
	private Configuration configuration;
	
	ConfigurableElements(Configuration configuration){
		this.configuration = configuration;
	}

	void addConfigurable(IConfigurable configurable) {
		configurable.resetAccordingTo(configuration);
		configurableList.add(configurable);
	}

	@Override
	public void resetAccordingTo(Configuration config) {
		configuration = config;
		configurableList.forEach((configurable) -> configurable
				.resetAccordingTo(configuration));
	}

	@Override
	public Configuration getWrappedConfiguration() {
		return configuration;
	}
}
