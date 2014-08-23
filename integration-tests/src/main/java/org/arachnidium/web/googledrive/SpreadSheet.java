package org.arachnidium.web.googledrive;

import org.arachnidium.core.Handle;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;

/**
 * Overrides some annotations of the superclass
 */
@IfBrowserURL(regExp = "docs.google.com/spreadsheets/")
@IfBrowserURL(regExp = "/spreadsheets/")
public class SpreadSheet<T extends Handle> extends AnyDocument<T> {

	protected SpreadSheet(T handle) {
		super(handle);
	}

}
