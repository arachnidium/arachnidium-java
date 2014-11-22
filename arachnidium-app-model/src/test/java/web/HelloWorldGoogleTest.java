package web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import web.mocks.MockWebDriverListener;
import web.mocks.MockWindowListener;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.web.google.Google;

public class HelloWorldGoogleTest {
	
	// settings according to current OS
	private final HashMap<Platform, List<String>> settings = new HashMap<Platform, List<String>>();
	
	@BeforeTest
	public void beforeTest() {
		//for Windows
		settings.put(Platform.WINDOWS, new ArrayList<String>(){
			private static final long serialVersionUID = -1718278594717074313L;
			{
				add("chrome_remote.json");
				add("chrome.json");
				
				add("firefox_remote.json");
				add("firefox.json");
				
				add("internetexplorer_remote.json");
				add("internetexplorer.json");
				
				add("phantomjs_remote.json");
				add("phantomjs.json");
				
				add("android_emulator_chrome.json");
				add("android_emulator_chrome_remoteWebDriver.json");
				
				add("android_emulator_browser.json");
				add("android_emulator_browser_remoteWebDriver.json");
			}
			
		});
		//for MAC
		settings.put(Platform.MAC, new ArrayList<String>(){
			private static final long serialVersionUID = -1718278594717074313L;
			{
				add("chrome_remote.json");
				add("chrome.json");
				
				add("firefox_remote.json");
				add("firefox.json");
				
				add("safari_remote.json");
				add("safari.json");
				
				add("phantomjs_remote.json");
				add("phantomjs.json");
				
				add("iOS_emulator_safari.json");
				add("iOS_emulator_safari_remoteWebDriver.json");
			}
			
		});		
	}

	List<String> getConfigsByCurrentPlatform(){
		Set<Entry<Platform, List<String>>> entries = settings.entrySet();
		for (Entry<Platform, List<String>> entry: entries){
			if (Platform.getCurrent().is(entry.getKey())){
				return entry.getValue();
			}
		}
		
		return new ArrayList<String>();
	}
	
	private void workWithGoogle(Google google) throws Exception{
		google.performSearch("Hello world Wikipedia");
		Assert.assertNotSame(0, google.getLinkCount());	
	}
	
	private void test(Google google) throws Exception {
		try {
			workWithGoogle(google);
		} finally {
			google.quit();
		}
	}

	private void test2(Google google) {
		try {
			google.close();
		} finally {
			google.quit();
		}
	}
	
	@Test(description = "This is just a test of basic functionality without any configuration")
	public void typeHelloWorldAndOpenTheFirstLink() throws Exception{
		test(new WebFactory().launch(Google.class, "http://www.google.com/"));
	}

	@Test(description = "This is just a test of basic functionality with specified configurations")
	@Parameters(value={"path", "configList"})
	public void typeHelloWorldAndOpenTheFirstLink2(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("chrome.json,chrome_remote.json,firefox.json") String configList)
			throws Exception {
		
		List<String> configs = getConfigsByCurrentPlatform();
		String[] configNames = configList.split(",");
		
		for (String config: configNames){
			if (!configs.contains(config)){
				continue;
			}
			Configuration configuration = Configuration
					.get(path + config);
			test(new WebFactory(configuration).launch(Google.class, "http://www.google.com/"));		
		}
	}

	@Test(description = "This is just a test of basic functionality. It closes google as visible browser window")
	@Parameters(value={"path","configList"})
	public void typeHelloWorldAndOpenTheFirstLink4(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("chrome.json,firefox.json") String configList) throws Exception {
		
		String[] configNames = configList.split(",");
		List<String> configs = getConfigsByCurrentPlatform();
		
		for (String config: configNames){
			if (!configs.contains(config)){
				continue;
			}
			Configuration configuration = Configuration
					.get(path + config);
			test2(new WebFactory(configuration).launch(Google.class, "http://www.google.com/"));
		}
	}

	@Test(description = "This is just a test of basic functionality. Checks possibility of service provider")
	public void typeHelloWorldAndOpenTheFirstLink5() throws Exception{
		MockWebDriverListener.listener = null;
		MockWindowListener.listener = null;
		MockWebDriverListener.wasInvoked = false;
		MockWindowListener.wasInvoked = false;
		try {
			test(new WebFactory().launch(Google.class, "http://www.google.com/"));
		} catch (Exception e) {
		}
		Assert.assertEquals(true, MockWebDriverListener.wasInvoked);
		Assert.assertEquals(true, MockWindowListener.wasInvoked);
	}
	
	@Test(description = "Extertal webdriver quit test")
	@Parameters(value={"path", "configList"})
	public void typeHelloWorldAndOpenTheFirstLink6(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("chrome.json,firefox.json") String configList)
			throws Exception {

		List<String> configs = getConfigsByCurrentPlatform();
		String[] configNames = configList.split(",");

		for (String config : configNames) {
			if (!configs.contains(config)) {
				continue;
			}
			Configuration configuration = Configuration.get(path + config);
			Google google = new WebFactory(configuration).launch(Google.class, "http://www.google.com/");
			workWithGoogle(google);
			google.getWrappedDriver().quit();
		}
	}
}
