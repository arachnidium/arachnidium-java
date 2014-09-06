package org.arachnidium.util.configuration.interfaces;

import org.arachnidium.util.configuration.Configuration;

/**
 * It is for all objects which changes it is own 
 * options/parameters by given {@link Configuration}
 */
public interface IConfigurable {
	public void resetAccordingTo(Configuration config);
}
