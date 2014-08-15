package org.arachnidium.core;

import java.util.List;

import org.arachnidium.core.fluenthandle.AbstractFluentHandleStrategy;

/**
 * Is for mobile contexts only
 */
public class FluentContextStrategy extends AbstractFluentHandleStrategy {
	@Override
	public void setExpected(int index) {
		super.setExpected(index);
	}

	@Override
	public void setExpected(String contextRegExp) {
		super.setExpected(contextRegExp);
	}

	@Override
	public void setExpected(List<String> activitiesRegExps) {
		super.setExpected(activitiesRegExps);
	}
}
