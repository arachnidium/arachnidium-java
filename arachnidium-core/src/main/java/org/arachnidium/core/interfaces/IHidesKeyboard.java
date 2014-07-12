package org.arachnidium.core.interfaces;

public interface IHidesKeyboard {
	/**
	 * Hides the keyboard if it is showing. This is an iOS only command.
	 */
	public void hideKeyboard();

	/**
	 * Hides the keyboard by pressing the button specified by keyName if it is
	 * showing. This is an iOS only command.
	 */
	public void hideKeyboard(String keyName);
}
