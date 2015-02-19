package com.github.arachnidium.util.logging;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * java.util.logging.LogRecord with attached files
 */
public class LogRecWithAttach extends LogRecord{
	private File attached;

	private static final long serialVersionUID = 1L;

	public LogRecWithAttach(Level level, String msg) {
		super(level, msg);
	}

	/**
	 * Gets attached file
	 * @return {@link File} which was attached to message
	 */
	public File getAttachedFile() {
		return attached;
	}

	/**
	 * attaches file
	 * @param attached File which has to be attached
	 */
	void setAttachment(File attached) {
		this.attached = attached;
	}

}
