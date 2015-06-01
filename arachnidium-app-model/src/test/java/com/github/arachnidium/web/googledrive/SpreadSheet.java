package com.github.arachnidium.web.googledrive;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;

/**
 * Overrides some annotations of the superclass
 */
@ExpectedURL(regExp = "docs.google.com/spreadsheets/")
@ExpectedURL(regExp = "/spreadsheets/")
public class SpreadSheet<T extends Handle> extends AnyDocument<T> {

	protected SpreadSheet(T handle) {
		super(handle);
	}

}
