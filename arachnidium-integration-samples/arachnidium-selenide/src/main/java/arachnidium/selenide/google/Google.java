package arachnidium.selenide.google;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver; //(!!!)
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.model.browser.BrowserApplication;
import org.arachnidium.model.browser.WebFactory;
import org.arachnidium.core.BrowserWindow;
import org.arachnidium.core.WebDriverEncapsulation;

public class Google extends BrowserApplication {
	
	private final static String url = "http://www.google.com/";
	
	protected Google(BrowserWindow browserWindow){
		super(browserWindow);
		//!!!!
		setWebDriver(getWrappedDriver());
	}
	
	public static Google getNew()
	{
		return WebFactory.getApplication(Google.class, url);
	}
	
	public static Google getNew(Configuration config)
	{
		return WebFactory.getApplication(Google.class, config, url);
	}
	
	public static Google getNew(WebDriverEncapsulation externalEncapsulation)
	{
		return WebFactory.getApplication(Google.class, externalEncapsulation, url);
	}
}
