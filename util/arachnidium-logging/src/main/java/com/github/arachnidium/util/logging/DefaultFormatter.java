package com.github.arachnidium.util.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class DefaultFormatter extends Formatter {
	private final ILogConverter converter;
	
	public DefaultFormatter(ILogConverter converter) {
		this.converter = converter;
	}
	
	@Override
	public String format(LogRecord record) {
		return converter.convert(record);
	}

}
