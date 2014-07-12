package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.IPerformsTouchActions;
import org.openqa.selenium.WebDriver;

public abstract class TouchActionsPerformer extends
WebdriverInterfaceImplementor implements IPerformsTouchActions {
	public TouchActionsPerformer(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
