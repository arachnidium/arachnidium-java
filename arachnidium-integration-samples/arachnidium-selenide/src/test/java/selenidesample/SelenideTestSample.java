package selenidesample;

import org.arachnidium.util.configuration.Configuration;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import arachnidium.selenide.google.Google;
import arachnidium.selenide.google.LinksAreFound;
import arachnidium.selenide.google.SearchBar;
import arachnidium.selenide.google.WikiPage;

public class SelenideTestSample {

	@Test(description = "This is a simple test that shows how work Arachnidium and Selenide in concern. "
			+ "Starts with Chrome (Win and Android), IE and PhantomJS")
	@Parameters(value = { "path", "configList" })
	public void selenideTest(@Optional("src/test/resources/") String path,
			@Optional("chrome.json,internetexplorer.json,phantomjs.json"
					+ "android_emulator_chrome_remoteWebDriver.json,android_emulator_chrome.json") String configList) {

		String[] configNames = configList.split(",");
		for (String config : configNames) {
			Configuration configuration = Configuration.get(path + config);
			Google google = Google.getNew(configuration);
			try {
				google.getPart(SearchBar.class).performSearch("Hello world Wikipedia");
				google.getPart(LinksAreFound.class).openLinkByIndex(1);
				WikiPage wiki = google.getPart(WikiPage.class, 1);
				wiki.close();
				google.getPart(LinksAreFound.class).openLinkByIndex(1);
				wiki = google.getPart(WikiPage.class, 1);
			} finally {
				google.quit();
			}
		}

	}
}
