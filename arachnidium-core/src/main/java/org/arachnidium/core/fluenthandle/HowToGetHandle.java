package org.arachnidium.core.fluenthandle;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedCondition;

public abstract class HowToGetHandle implements IHowToGetHandle {

	protected Integer index = null;;
	protected String stringIdentifier = null;
	protected List<String> uniqueIdentifiers = null;

	@Override
	public void setExpected(int index) {
		this.index = index;
	}

	@Override
	public void setExpected(String stringIdentifier) {
		this.stringIdentifier = stringIdentifier;
	}

	@Override
	public void setExpected(List<String> uniqueIdentifiers) {
		this.uniqueIdentifiers = uniqueIdentifiers;
	}

	@Override
	public ExpectedCondition<String> getExpectedCondition(
			IFluentHandleWaiting fluentHandleWaiting) {

		if (index == null && stringIdentifier == null
				&& uniqueIdentifiers == null) {
			throw new IllegalArgumentException(
					"There is no defined condition to get a new handle");
		}

		// getHandle(int)
		if (index != null && stringIdentifier == null
				&& uniqueIdentifiers == null) {
			return fluentHandleWaiting.getHandle(index);
		}

		// getHandle(String)
		if (index == null && stringIdentifier != null
				&& uniqueIdentifiers == null) {
			return fluentHandleWaiting.getHandle(stringIdentifier);
		}

		// getHandle(List<String>)
		if (index == null && stringIdentifier == null
				&& uniqueIdentifiers != null) {
			return fluentHandleWaiting.getHandle(uniqueIdentifiers);
		}

		// getHandle(int, String)
		if (index != null && stringIdentifier != null
				&& uniqueIdentifiers == null) {
			return fluentHandleWaiting.getHandle(index, stringIdentifier);
		}

		// getHandle(String, List<String>)
		if (index != null && stringIdentifier == null
				&& uniqueIdentifiers != null) {
			return fluentHandleWaiting.getHandle(index, uniqueIdentifiers);
		}

		// getHandle(int, List<String>)
		if (index == null && stringIdentifier != null
				&& uniqueIdentifiers != null) {
			return fluentHandleWaiting.getHandle(stringIdentifier,
					uniqueIdentifiers);
		}

		return fluentHandleWaiting.getHandle(index, stringIdentifier,
				uniqueIdentifiers);
	}
}
