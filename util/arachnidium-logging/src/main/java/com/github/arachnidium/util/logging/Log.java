/*
 +Copyright 2014 Arachnidium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

package com.github.arachnidium.util.logging;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.github.arachnidium.util.configuration.Configuration;

public class Log {
	/**
	 * java.util.logging.LogRecord with attached files
	 */
	public static class LogRecWithAttach extends LogRecord {

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
		private void setAttachment(File attached) {
			this.attached = attached;
		}

	}

    /**
	 *  add new converter of log messages 
	 *  Is useful for integration with reporting or another 
	 *  logging frameworks
	 *  
     * @param converter An instance of {@link ILogConverter} 
     * implementor
     */
	public static void addConverter(ILogConverter converter) {
		converters.add(converter);
	}

	private static void applyLogRec(LogRecWithAttach rec) {
		if (commonLevel.intValue() <= rec.getLevel().intValue()) {
			log.log(rec);
			converting.convert(rec);
		}
	}

	private static void applyLogRec(LogRecWithAttach rec, File attached) {
		rec.setAttachment(attached);
		applyLogRec(rec);
	}

	/**
	 * Creates a log message with FINE {@link Level}
	 * 
	 * @param msg Message text
	 */
	public static void debug(String msg) {
		applyLogRec(getRecordForLog(eAvailableLevels.FINE, msg));
	}

    /**
     * Creates a log message with FINE {@link Level} and
     * attached file
     * 
     * @param msg Message text
     * @param attached attached {@link File}
     */
	public static void debug(String msg, File attached) {
		applyLogRec(getRecordForLog(eAvailableLevels.FINE, msg), attached);
	}

    /**
     * Creates a log message with FINE {@link Level} and
     * throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     */	
	public static void debug(String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.FINE, msg), t));
	}

    /**
     * Creates a log message with FINE {@link Level}, 
     * attached file and throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     * @param attached attached {@link File}
     */
	public static void debug(String msg, Throwable t, File attached) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.FINE, msg), t),
				attached);
	}

	/**
	 * Creates a log message with SEVERE {@link Level}
	 * 
	 * @param msg Message text
	 */
	public static void error(String msg) {
		applyLogRec(getRecordForLog(eAvailableLevels.SEVERE, msg));
	}

    /**
     * Creates a log message with SEVERE {@link Level} and
     * attached file
     * 
     * @param msg Message text
     * @param attached attached file {@link File}
     */	
	public static void error(String msg, File attached) {
		applyLogRec(getRecordForLog(eAvailableLevels.SEVERE, msg), attached);
	}

    /**
     * Creates a log message with SEVERE {@link Level} and
     * throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     */		
	public static void error(String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.SEVERE, msg), t));
	}

    /**
     * Creates a log message with SEVERE {@link Level}, 
     * attached file and throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     * @param attached attached {@link File}
     */	
	public static void error(String msg, Throwable t, File attached) {
		applyLogRec(
				setThrown(getRecordForLog(eAvailableLevels.SEVERE, msg), t),
				attached);
	}

	/**
	 * Gets current {@link Level}
	 * 
	 * @return {@link Level}
	 */
	public static Level getLevel() {
		return commonLevel;
	}

	// new log record is formed here
	private static LogRecWithAttach getRecordForLog(eAvailableLevels level,
			String msg) {
		StackTraceElement stack[] = new Throwable().getStackTrace();
		StackTraceElement element = stack[levelUp];
		LogRecWithAttach rec = new LogRecWithAttach(level.getLevel(), msg);
		rec.setSourceClassName(element.getClassName());
		rec.setSourceMethodName(element.getMethodName());
		rec.setMillis(Calendar.getInstance().getTimeInMillis());
		rec.setThreadID((int) Thread.currentThread().getId());
		rec.setLoggerName(log.getName());
		rec.setLevel(level.getLevel());
		sequence = sequence + 1;
		rec.setSequenceNumber(sequence);
		return rec;
	}

	/**
	 * Creates a log message with available {@link Level}
	 * 
	 * @param msg Message text
	 */
	public static void log(eAvailableLevels level, String msg) {
		applyLogRec(getRecordForLog(level, msg));
	}

    /**
     * Creates a log message with available {@link Level} and
     * attached file
     * 
     * @param msg Message text
     * @param attached attached file {@link File}
     */	
	public static void log(eAvailableLevels level, String msg, File attached) {
		applyLogRec(getRecordForLog(level, msg), attached);
	}

    /**
     * Creates a log message with available {@link Level} and
     * throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     */		
	public static void log(eAvailableLevels level, String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(level, msg), t));
	}

    /**
     * Creates a log message with available {@link Level}, 
     * attached file and throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     * @param attached attached {@link File}
     */		
	public static void log(eAvailableLevels level, String msg, Throwable t,
			File attached) {
		applyLogRec(setThrown(getRecordForLog(level, msg), t), attached);
	}

	/**
	 * Creates a log message with INFO {@link Level}
	 * 
	 * @param msg Message text
	 */	
	public static void message(String msg) {
		applyLogRec(getRecordForLog(eAvailableLevels.INFO, msg));
	}

    /**
     * Creates a log message with INFO {@link Level} and
     * attached file
     * 
     * @param msg Message text
     * @param attached attached file {@link File}
     */		
	public static void message(String msg, File attached) {
		applyLogRec(getRecordForLog(eAvailableLevels.INFO, msg), attached);
	}

    /**
     * Creates a log message with INFO {@link Level} and
     * throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     */	
	public static void message(String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.INFO, msg), t));
	}

    /**
     * Creates a log message with INFO {@link Level}, 
     * attached file and throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     * @param attached attached {@link File}
     */		
	public static void message(String msg, Throwable t, File attached) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.INFO, msg), t),
				attached);
	}

	/**
	 * changes current log {@link Level}
	 * @param level
	 * @return new current {@link Level}
	 */
	public static Level resetLogLevel(Level level) {
		if (level == null)
			commonLevel = info;
		else
			commonLevel = level;
		
		String config = "\n" + 
			       "handlers = java.util.logging.ConsoleHandler" + "\n" + 
			       ".level = " + commonLevel.getLocalizedName() +"\n"+
			       "java.util.logging.ConsoleHandler.level = " + commonLevel.getLocalizedName() + "\n" +
			       "";
		InputStream ins = new ByteArrayInputStream(config.getBytes());
	    try {
	       LogManager.getLogManager().readConfiguration(ins);
	    } catch (IOException e) {
	       throw new RuntimeException(e);
	    }
		return commonLevel;
	}

	private static LogRecWithAttach setThrown(LogRecWithAttach rec, Throwable t) {
		rec.setThrown(t);
		return rec;
	}

	/**
	 * Creates a log message with WARNING {@link Level}
	 * 
	 * @param msg Message text
	 */	
	public static void warning(String msg) {
		applyLogRec(getRecordForLog(eAvailableLevels.WARN, msg));
	}

    /**
     * Creates a log message with WARNING {@link Level} and
     * attached file
     * 
     * @param msg Message text
     * @param attached attached file {@link File}
     */		
	public static void warning(String msg, File attached) {
		applyLogRec(getRecordForLog(eAvailableLevels.WARN, msg), attached);
	}

    /**
     * Creates a log message with WARNING {@link Level} and
     * throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     */		
	public static void warning(String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.WARN, msg), t));
	}

    /**
     * Creates a log message with WARNING {@link Level}, 
     * attached file and throwable instance
     * 
     * @param msg Message text
     * @param t Some {@link Throwable}
     * @param attached attached {@link File}
     */		
	public static void warning(String msg, Throwable t, File attached) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.WARN, msg), t),
				attached);
	}

	private final static int levelUp = 2;

	private final static Level info = Level.INFO;

	private static Level commonLevel = resetLogLevel(Configuration.byDefault
			.getSection(LoggingHelper.class).getLevel());

	private static Logger log = Logger.getAnonymousLogger();
	
	private static long sequence = 0;

	private static final List<ILogConverter> converters = Collections
			.synchronizedList(new LinkedList<ILogConverter>());

	private static final ILogConverter converting = (ILogConverter) Proxy
			.newProxyInstance(ILogConverter.class.getClassLoader(),
					new Class[] { ILogConverter.class },
					(proxy, method, args) -> {
						for (ILogConverter sender : converters)
							method.invoke(sender, args);
						return null;
					});
}
