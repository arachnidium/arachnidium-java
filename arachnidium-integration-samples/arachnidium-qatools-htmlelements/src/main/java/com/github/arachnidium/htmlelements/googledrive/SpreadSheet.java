package com.github.arachnidium.htmlelements.googledrive;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;

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
