package org.arachnidium.util.logging;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.arachnidium.util.configuration.Configuration;

public class Log {
	/**
	 * @author s.tihomirov java.util.logging.LogRecord with attached files
	 */
	public static class LogRecWithAttach extends LogRecord {

		private File attached;

		private static final long serialVersionUID = 1L;

		public LogRecWithAttach(Level level, String msg) {
			super(level, msg);
		}

		public File getAttachedFile() {
			return attached;
		}

		private void setAttachment(File attached) {
			this.attached = attached;
		}

	}

	// add new sender to any other logging system or framework
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

	public static void debug(String msg) {
		applyLogRec(getRecordForLog(eAvailableLevels.FINE, msg));
	}

	public static void debug(String msg, File attached) {
		applyLogRec(getRecordForLog(eAvailableLevels.FINE, msg), attached);
	}

	public static void debug(String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.FINE, msg), t));
	}

	public static void debug(String msg, Throwable t, File attached) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.FINE, msg), t),
				attached);
	}

	public static void error(String msg) {
		applyLogRec(getRecordForLog(eAvailableLevels.SEVERE, msg));
	}

	public static void error(String msg, File attached) {
		applyLogRec(getRecordForLog(eAvailableLevels.SEVERE, msg), attached);
	}

	public static void error(String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.SEVERE, msg), t));
	}

	public static void error(String msg, Throwable t, File attached) {
		applyLogRec(
				setThrown(getRecordForLog(eAvailableLevels.SEVERE, msg), t),
				attached);
	}

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

	public static void log(eAvailableLevels level, String msg) {
		applyLogRec(getRecordForLog(level, msg));
	}

	public static void log(eAvailableLevels level, String msg, File attached) {
		applyLogRec(getRecordForLog(level, msg), attached);
	}

	public static void log(eAvailableLevels level, String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(level, msg), t));
	}

	public static void log(eAvailableLevels level, String msg, Throwable t,
			File attached) {
		applyLogRec(setThrown(getRecordForLog(level, msg), t), attached);
	}

	public static void message(String msg) {
		applyLogRec(getRecordForLog(eAvailableLevels.INFO, msg));
	}

	public static void message(String msg, File attached) {
		applyLogRec(getRecordForLog(eAvailableLevels.INFO, msg), attached);
	}

	public static void message(String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.INFO, msg), t));
	}

	public static void message(String msg, Throwable t, File attached) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.INFO, msg), t),
				attached);
	}

	public static void removeConverter(ILogConverter converter) {
		converters.remove(converter);
	}

	// reset log level parameters
	public static Level resetLogLevel(Level level) {
		if (level == null)
			commonLevel = info;
		else
			commonLevel = level;
		log.setLevel(commonLevel);
		return commonLevel;
	}

	private static LogRecWithAttach setThrown(LogRecWithAttach rec, Throwable t) {
		rec.setThrown(t);
		return rec;
	}

	public static void warning(String msg) {
		applyLogRec(getRecordForLog(eAvailableLevels.WARN, msg));
	}

	public static void warning(String msg, File attached) {
		applyLogRec(getRecordForLog(eAvailableLevels.WARN, msg), attached);
	}

	public static void warning(String msg, Throwable t) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.WARN, msg), t));
	}

	public static void warning(String msg, Throwable t, File attached) {
		applyLogRec(setThrown(getRecordForLog(eAvailableLevels.WARN, msg), t),
				attached);
	}

	private final static int levelUp = 2;

	private final static Level info = Level.INFO;

	private static Logger log = Logger.getAnonymousLogger();

	private static Level commonLevel = resetLogLevel(Configuration.byDefault
			.getSection(LoggingHelper.class).getLevel());

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
