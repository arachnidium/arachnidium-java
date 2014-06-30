package org.arachnidium.core.components.overriden;

import org.arachnidium.core.components.WebdriverComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

public final class PageFactoryWorker extends WebdriverComponent {

	public PageFactoryWorker(WebDriver driver) {
		super(driver);
	}

	public <T> T initPageFactory(Class<T> pageClassToProxy) {
		return PageFactory.initElements(driver, pageClassToProxy);
	}

	public void initPageFactory(ElementLocatorFactory factory, Object page) {
		PageFactory.initElements(factory, page);
	}

	public void initPageFactory(FieldDecorator decorator, Object page) {
		PageFactory.initElements(decorator, page);
	}

	// To provide work with page factory
	public void initPageFactory(Object page) {
		PageFactory.initElements(driver, page);
	}

}
