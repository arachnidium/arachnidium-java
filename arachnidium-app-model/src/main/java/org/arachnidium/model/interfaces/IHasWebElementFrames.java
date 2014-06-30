package org.arachnidium.model.interfaces;

import org.openqa.selenium.WebElement;

public interface IHasWebElementFrames extends IDecomposable {
	// - with frame that specified as web element
	public <T extends IDecomposable> T getPart(Class<T> partClass, WebElement frameElement);

}
