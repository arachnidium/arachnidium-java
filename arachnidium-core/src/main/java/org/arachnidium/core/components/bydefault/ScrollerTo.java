package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.IScrollsTo;
import org.openqa.selenium.WebDriver;

public abstract class ScrollerTo extends WebdriverInterfaceImplementor
implements IScrollsTo {

	public ScrollerTo(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}

}
