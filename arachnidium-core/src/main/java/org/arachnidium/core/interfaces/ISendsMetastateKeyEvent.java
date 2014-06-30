package org.arachnidium.core.interfaces;

public interface ISendsMetastateKeyEvent {
	/**
	 * Send a key event along with an Android metastate to an Android device
	 * Metastates are things like *shift* to get uppercase characters
	 */
	public void sendKeyEvent(int key, Integer metastate);

}
