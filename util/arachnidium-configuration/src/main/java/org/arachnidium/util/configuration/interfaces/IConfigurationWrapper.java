package org.arachnidium.util.configuration.interfaces;

import org.arachnidium.util.configuration.Configuration;

/**
 * It is for all objects which 
 * stores given {@link Configuration}
 */
public interface IConfigurationWrapper {
	Configuration getWrappedConfiguration();
}
