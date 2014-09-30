package com.github.arachnidium.util.logging;

import com.github.arachnidium.util.logging.Log.LogRecWithAttach;

/**
 * It is for something that converts log messages 
 * to some report or messages of 3rd party logging lib.
 */
public interface ILogConverter {
	public void convert(LogRecWithAttach record);
}
