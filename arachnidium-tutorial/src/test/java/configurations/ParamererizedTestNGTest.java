package configurations;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.tutorial.confuguration.google.Google;
import com.github.arachnidium.util.configuration.Configuration;


public class ParamererizedTestNGTest {
	
  private static String PATH_TO_CONFIG = "src/test/resources/configs/";	
	
  @Test
  @Parameters(value={"configurations"})
  public void test(@Optional("android_emulator_chrome_remoteWebDriver.json,"+
		  					 "android_emulator_chrome.json,"+
		  					 "chrome_remote.json,"+
		  					 "chrome.json,"+
		  					 "firefox_remote.json,"+
		  					 "firefox.json,"+
		  					 "internetexplorer_remote.json,"+
		  					 "internetexplorer.json,"+
		  					 "phantomjs_remote.json,"+
		  					 "phantomjs.json,"+
		  					 "safari_remote.json,"+
		  					 "safari.json") String configurations) {
		List<String> configs = Arrays.asList(configurations.split(","));
		configs.forEach((config) -> {
			Configuration configuration = Configuration.get(PATH_TO_CONFIG + config);
			Google google = new WebFactory(configuration).launch(Google.class);
			
			google.performSearch("Hello world Wikipedia");
			Assert.assertNotSame(0, google.getLinkCount());	
			google.openLinkByIndex(1);
			google.quit();
		});
	  
  }
}
