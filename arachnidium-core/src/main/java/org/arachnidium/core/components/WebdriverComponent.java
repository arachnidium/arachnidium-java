package org.arachnidium.core.components;

import org.openqa.selenium.WebDriver;

/**
 * This abstraction wraps {@link WebDriver} and divides {@link WebDriver} implementer
 * into logically final parts that easy to use
 */
public abstract class WebdriverComponent {
    /**
     * Wrapped {@link WebDriver}
     */
	protected WebDriver driver;
	/**
	 * Objects whose methods will be invoked
	 */
	protected Object delegate;
	
	public WebdriverComponent(WebDriver driver) {
		this.driver = driver;
	}
}
