package configurations;

import java.util.Arrays;
import java.util.Collection;




import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.tutorial.confuguration.google.Google;
import com.github.arachnidium.util.configuration.Configuration;

@RunWith(Parameterized.class)
public class ParameterizedJUnit {
		
	@Parameters
	public static Collection<Object[]> data() throws Exception {
		return Arrays.asList(new Object[][] {
				{"android_emulator_chrome_remoteWebDriver.json", Google.class},
				{"android_emulator_chrome.json", Google.class},				
				{"android_emulator_browser_remoteWebDriver.json", Google.class},
				{"android_emulator_browser.json", Google.class},
				{"chrome_remote.json", Google.class},
				{"chrome.json", Google.class},
				{"firefox_remote.json", Google.class},
				{"firefox.json", Google.class},
				{"internetexplorer_remote.json", Google.class},
				{"internetexplorer.json", Google.class},
				{"phantomjs_remote.json", Google.class},
				{"phantomjs.json", Google.class},
				{"safari_remote.json", Google.class},
				{"safari.json", Google.class}
				});
	}
	
	private static String PATH_TO_CONFIG = "src/test/resources/configs/";
			
	private Google google;
	@Parameter(0)
	public String configFile;
	@Parameter(1)
	public Class<? extends Application<?, ?>> desiredApp;

	@Before
	public void setUp() throws Exception {
		Configuration config = Configuration.get(PATH_TO_CONFIG + configFile);
		google = (Google) new WebFactory(config).launch(desiredApp);
	}
	
	@Test
	public void test() {
		google.performSearch("Hello world Wikipedia");
		Assert.assertNotSame(0, google.getLinkCount());	
		google.openLinkByIndex(1);
	}
	
	@After
	public void tearDown(){
		google.quit();
	}

}
