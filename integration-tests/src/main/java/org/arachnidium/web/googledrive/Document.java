package org.arachnidium.web.googledrive;

import org.arachnidium.core.Handle;

/**
 * Uses annotations of superclass
 */
public class Document<T extends Handle> extends AnyDocument<T> {

	protected Document(T handle) {
		super(handle);
	}

}
