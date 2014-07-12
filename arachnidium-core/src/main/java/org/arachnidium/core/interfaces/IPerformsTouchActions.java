package org.arachnidium.core.interfaces;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

/**
 * Performs {@link TouchAction} and {@link MultiTouchAction}
 *
 */
public interface IPerformsTouchActions {
	public void performMultiTouchAction(MultiTouchAction multiAction);

	public TouchAction performTouchAction(TouchAction touchAction);
}
