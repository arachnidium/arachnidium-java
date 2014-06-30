package org.arachnidium.util.logging;

import java.util.logging.Level;

public enum eAvailableLevels {
	FINE(Level.FINE),
	INFO(Level.INFO),
	SEVERE(Level.SEVERE),
	WARN(Level.WARNING);
	

	private final Level level;

	private eAvailableLevels(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

}
