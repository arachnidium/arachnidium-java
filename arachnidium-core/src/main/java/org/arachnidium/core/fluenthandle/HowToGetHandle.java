package org.arachnidium.core.fluenthandle;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * The default implementor of {@link IHowToGetHandle}
 *
 */
public abstract class HowToGetHandle implements IHowToGetHandle {

	protected Integer index = null;;
	protected String stringIdentifier = null;
	protected List<String> uniqueIdentifiers = null;

	/**
	 * @see org.arachnidium.core.fluenthandle.IHowToGetHandle#setExpected(int)
	 */
	@Override
	public void setExpected(int index) {
		this.index = index;
	}

	/**
	 * @see org.arachnidium.core.fluenthandle.IHowToGetHandle#setExpected(java.lang.String)
	 */
	@Override
	public void setExpected(String stringIdentifier) {
		this.stringIdentifier = stringIdentifier;
	}

	/**
	 * @see org.arachnidium.core.fluenthandle.IHowToGetHandle#setExpected(java.util.List)
	 */
	@Override
	public void setExpected(List<String> uniqueIdentifiers) {
		this.uniqueIdentifiers = uniqueIdentifiers;
	}

	/**
	 * @see org.arachnidium.core.fluenthandle.IHowToGetHandle#getExpectedCondition(org.arachnidium.core.fluenthandle.IFluentHandleWaiting)
	 */
	@Override
	public ExpectedCondition<String> getExpectedCondition(
			IFluentHandleWaiting fluentHandleWaiting) {

		if (index == null && stringIdentifier == null
				&& uniqueIdentifiers == null) {
			throw new IllegalArgumentException(
					"There is no defined condition to get a new handle");
		}

		if (index != null && stringIdentifier == null
				&& uniqueIdentifiers == null) {
			return fluentHandleWaiting.getHandle(index);
		}

		if (index == null && stringIdentifier != null
				&& uniqueIdentifiers == null) {
			return fluentHandleWaiting.getHandle(stringIdentifier);
		}

		if (index == null && stringIdentifier == null
				&& uniqueIdentifiers != null) {
			return fluentHandleWaiting.getHandle(uniqueIdentifiers);
		}

		if (index != null && stringIdentifier != null
				&& uniqueIdentifiers == null) {
			return fluentHandleWaiting.getHandle(index, stringIdentifier);
		}

		if (index != null && stringIdentifier == null
				&& uniqueIdentifiers != null) {
			return fluentHandleWaiting.getHandle(index, uniqueIdentifiers);
		}

		if (index == null && stringIdentifier != null
				&& uniqueIdentifiers != null) {
			return fluentHandleWaiting.getHandle(stringIdentifier,
					uniqueIdentifiers);
		}

		return fluentHandleWaiting.getHandle(index, stringIdentifier,
				uniqueIdentifiers);
	}
}
