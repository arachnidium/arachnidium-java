package org.arachnidium.core;

import java.util.ArrayList;
import java.util.List;

import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.util.configuration.interfaces.IConfigurable;

/**
 * @author s.tihomirov Container for all configurable objects that are created
 *         with @Link{WebDriverEncapsulation}
 */
class ConfigurableElements implements IConfigurable {

	private final List<IConfigurable> configurableList = new ArrayList<IConfigurable>();

	void addConfigurable(IConfigurable configurable) {
		configurableList.add(configurable);
	}

	@Override
	public void resetAccordingTo(Configuration config) {
		configurableList.forEach((configurable) -> configurable
				.resetAccordingTo(config));
	}
}
