package com.github.arachnidium.util.logging;

import java.util.logging.LogRecord;

/**
 * It is for something that converts log messages 
 * to some report or messages of 3rd party logging lib.
 */
public interface ILogConverter {
	public String convert(LogRecord record);
}
