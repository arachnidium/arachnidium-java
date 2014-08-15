package org.arachnidium.core;

import java.util.List;

import org.arachnidium.core.fluenthandle.AbstractFluentHandleStrategy;

/**
 * Is for browser windows only
 */
public final class FluentWindowStrategy extends AbstractFluentHandleStrategy {
	@Override
	public void setExpected(int index) {
		super.setExpected(index);
	}

	@Override
	public void setExpected(String titleRegExp) {
		super.setExpected(titleRegExp);
	}

	@Override
	public void setExpected(List<String> urlsRegExps) {
		super.setExpected(urlsRegExps);
	}
}
