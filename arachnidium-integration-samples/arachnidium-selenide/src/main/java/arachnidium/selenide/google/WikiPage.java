package arachnidium.selenide.google;

import org.arachnidium.model.browser.BrowserPage;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserPageTitle;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.arachnidium.core.BrowserWindow;

@IfBrowserURL(regExp = "https://ru.wikipedia.org/wiki")
@IfBrowserURL(regExp = "[wikipedia//org//wiki]")
@IfBrowserPageTitle(regExp = "^*[?[Hello]\\?[world]]")
public class WikiPage extends BrowserPage {

	public WikiPage(BrowserWindow browserWindow){
		super(browserWindow);
	}
}
