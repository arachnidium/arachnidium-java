package com.github.arachnidium.util.logging;

import java.util.logging.Level;

/**
 * Stores the list of available {@link Level} 
 */
public enum eAvailableLevels {
	/**
	 * {@link Level#FINE}
	 */
	FINE(Level.FINE), 
	/**
	 * {@link Level#INFO}
	 */
	INFO(Level.INFO), 
	/**
	 * {@link Level#SEVERE}
	 */
	SEVERE(Level.SEVERE), 
	/**
	 * {@link Level#WARNING}
	 */
	WARN(Level.WARNING);

	private final Level level;

	private eAvailableLevels(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

}
