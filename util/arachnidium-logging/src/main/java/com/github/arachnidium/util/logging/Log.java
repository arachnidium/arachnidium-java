/*
 +Copyright 2014-2015 Arachnidium contributors
 +Copyright 2014-2015 Software Freedom Conservancy
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

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {
	
	private static void addConverter(Handler handler, 
			ILogConverter converter){
		handler.setFormatter(new DefaultFormatter(converter));
		getAnonymousLogger().addHandler(handler);		
	}
	
	private static void addConverter(Class<? extends Handler> classOfHandler, 
			ILogConverter converter){
		Handler h = null;
		try {
			Constructor<?> c = classOfHandler.getConstructor(new Class[]{});
			c.setAccessible(true);
			h = (Handler) c.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		addConverter(h, converter);	
	}
	
	/**
	 *  add new converter of log messages 
	 *  Is useful for integration with reporting or another 
	 *  logging frameworks. It will convert messages which are
	 *  printed at console by default.
	 *  
     * @param converter An instance of {@link ILogConverter} 
     * implementor
     */
	public static void addConverter(ILogConverter converter) {
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(getLevel());
		addConverter(consoleHandler, converter);
	}
	
	/**
	 *  add new converter of log messages 
	 *  Is useful for integration with reporting or another 
	 *  logging frameworks. Here it is possible to use any desired
	 *  {@link Handler} subclass. Note!!! The desire {@link Handler} subclass
	 *  should have accessible default constructor
	 * 
	 * @param converter An instance of {@link ILogConverter} 
     * implementor
     * 
	 * @param classOfHandle is the desired class that extends {@link Handler}
	 */
	public static void addConverter(ILogConverter converter, 
			Class<? extends Handler> classOfHandle) {
		addConverter(classOfHandle, converter);
	}	
	
	/**
	 *  add new converter of log messages 
	 *  Is useful for integration with reporting or another 
	 *  logging frameworks. Here it is possible to use any desired
	 *  {@link Handler} subclass instance.
	 * 
	 * @param converter converter An instance of {@link ILogConverter} 
     * implementor
     * 
	 * @param handler is the instance of a class that extends {@link Handler}
	 */
	public static void addConverter(ILogConverter converter, 
			Handler handler) {
		addConverter(handler, converter);
	}	
	

	private static void applyLogRec(LogRecWithAttach rec) {
		getAnonymousLogger().log(rec);
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
		return getAnonymousLogger().getLevel();
	}

	// new log record is formed here
	private static LogRecWithAttach getRecordForLog(eAvailableLevels level,
			String msg) {
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		StackTraceElement element = stack[levelUp];
		LogRecWithAttach rec = new LogRecWithAttach(level.getLevel(), msg);
		rec.setSourceClassName(element.getClassName());
		rec.setSourceMethodName(element.getMethodName());
		rec.setMillis(Calendar.getInstance().getTimeInMillis());
		rec.setThreadID((int) Thread.currentThread().getId());
		rec.setLevel(level.getLevel());
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
	public static void resetLogLevel(Level level) {
		if (level == null)
			getAnonymousLogger().setLevel(level);
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

	private final static int levelUp = 3;
	
	private final static Level commonLevel = Level.INFO;	
	private static final  LogManager LOG_MANAGER = LogManager.getLogManager();
	
	private static Logger getAnonymousLogger(){
		return LOG_MANAGER.getLogger("");
	}
	
	static {
		if (LOG_MANAGER.getProperty(".level") == null)
			getAnonymousLogger().setLevel(commonLevel);
	}
}
