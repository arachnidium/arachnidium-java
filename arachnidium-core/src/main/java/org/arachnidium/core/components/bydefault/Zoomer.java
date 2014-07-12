package org.arachnidium.core.components.bydefault;

import org.arachnidium.core.interfaces.IZoom;
import org.openqa.selenium.WebDriver;

public abstract class Zoomer extends WebdriverInterfaceImplementor implements
		IZoom {
	public Zoomer(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
