package org.arachnidium.util.logging;

import org.arachnidium.util.logging.Log.LogRecWithAttach;

public interface ILogConverter {
	public void convert(LogRecWithAttach record);
}
