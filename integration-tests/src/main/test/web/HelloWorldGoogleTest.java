package web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.Assert;

import org.arachnidium.core.UnhandledWindowChecker;
import org.arachnidium.core.WebDriverEncapsulation;
import org.arachnidium.testng.ReportBuildingTestListener;
import org.arachnidium.util.configuration.Configuration;
import org.arachnidium.web.google.AnyPage;
import org.arachnidium.web.google.Google;
import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import web.mocks.MockUnhandledWindowEventListener;
import web.mocks.MockWebDriverEventListener2;
import web.mocks.MockWebDriverListener;
import web.mocks.MockWindowListener;

@Listeners(ReportBuildingTestListener.class)
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
			if (entry.getKey().is(Platform.getCurrent())){
				return entry.getValue();
			}
		}
		
		return new ArrayList<String>();
	}
	
	private void test(Google google, boolean toClickOnALinkWhichWasFound) throws Exception {
		try {
			google.performSearch("Hello world Wikipedia");
			Assert.assertNotSame(0, google.getLinkCount());
			if (!toClickOnALinkWhichWasFound){
				google.openLinkByIndex(1);
			}
			else{
				google.clickOnLinkByIndex(1);
			}
			AnyPage anyPage = google.getFromHandle(AnyPage.class, 1);
			Thread.sleep(5000);
			anyPage.close();
			if (!toClickOnALinkWhichWasFound){
				google.openLinkByIndex(1);
			}
			else{
				google.clickOnLinkByIndex(1);
			}
			anyPage = google.getFromHandle(AnyPage.class, 1);
		} finally {
			google.quit();
		}
	}

	private void test2(Google google) {
		try {
			google.performSearch("Hello world Wikipedia");
			Assert.assertNotSame(0, google.getLinkCount());
			google.close();
		} finally {
			google.quit();
		}
	}

	private void test3(Google google, boolean toClickOnALinkWhichWasFound) throws Exception{
		try {
			google.performSearch("Hello world Wikipedia");
			Assert.assertNotSame(0, google.getLinkCount());
			if (!toClickOnALinkWhichWasFound){
				google.openLinkByIndex(1);
			}
			else{
				google.clickOnLinkByIndex(1);
			}
			Thread.sleep(5000);
			UnhandledWindowChecker.getChecker(google.getWindowManager())
					.killUnexpectedWindows();
			Assert.assertEquals(true, MockUnhandledWindowEventListener.wasInvoked);
		} finally {
			google.quit();
		}
	}
	
	@Test(description = "This is just a test of basic functionality without any configuration")
	public void typeHelloWorldAndOpenTheFirstLink() throws Exception{
		test(Google.getNew(), false);
	}

	@Test(description = "This is just a test of basic functionality with specified configurations")
	@Parameters(value={"path", "toClick","configList"})
	public void typeHelloWorldAndOpenTheFirstLink2(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("false") String toClick,
			@Optional("") String configList)
			throws Exception {
		
		List<String> configs = getConfigsByCurrentPlatform();
		String[] configNames = configList.split(",");
		
		for (String config: configNames){
			if (!configs.contains(config)){
				continue;
			}
			Configuration configuration = Configuration
					.get(path + config);
			test(Google.getNew(configuration), new Boolean(toClick));		
		}
	}

	@Test(description = "This is just a test of basic functionality with a webdriver instance that was created externally")
	@Parameters(value={"path"})
	public void typeHelloWorldAndOpenTheFirstLink3(
			@Optional("src/test/resources/configs/desctop/") String path) 
					throws Exception{
		Configuration configuration = Configuration
				.get(path + "firefox.json");
		WebDriverEncapsulation encapsulation = new WebDriverEncapsulation(
				new FirefoxDriver(), configuration);
		test(Google.getNew(encapsulation), false);
	}

	@Test(description = "This is just a test of basic functionality. It performs search and closes google as visible browser window")
	@Parameters(value={"path","configList"})
	public void typeHelloWorldAndOpenTheFirstLink4(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("") String configList) throws Exception {
		
		String[] configNames = configList.split(",");
		List<String> configs = getConfigsByCurrentPlatform();
		
		for (String config: configNames){
			if (!configs.contains(config)){
				continue;
			}
			Configuration configuration = Configuration
					.get(path + config);
			test2(Google.getNew(configuration));
		}
	}

	@Test(description = "This is just a test of basic functionality. Checks possibility of service provider working")
	public void typeHelloWorldAndOpenTheFirstLink5() throws Exception{
		MockWebDriverListener.listener = null;
		MockWindowListener.listener = null;
		MockWebDriverListener.wasInvoked = false;
		MockWindowListener.wasInvoked = false;
		MockWebDriverEventListener2.wasInvoked = false;
		MockWebDriverEventListener2.wasInvoked = false;
		try {
			test(Google.getNew(),false);
		} catch (Exception e) {
		}
		Assert.assertEquals(true, MockWebDriverListener.wasInvoked);
		Assert.assertEquals(true, MockWindowListener.wasInvoked);
		Assert.assertEquals(true, MockWebDriverEventListener2.wasInvoked);
	}

	@Test(description = "This is just a test of basic functionality. It watches how UnhandledWindowChecker kills windows that exist but weren't instantiated as objects of SingleWindow")
	@Parameters(value={"path", "toClick","configList"})
	public void typeHelloWorldAndOpenTheFirstLink6(
			@Optional("src/test/resources/configs/desctop/") String path,
			@Optional("false") String toClick,
			@Optional("") String configList) 
					throws Exception{
		
		String[] configNames = configList.split(",");
		List<String> configs = getConfigsByCurrentPlatform();
		
		for (String config: configNames){
			if (!configs.contains(config)){
				continue;
			}
			Configuration configuration = Configuration
					.get(path + config);
			test3(Google.getNew(configuration), new Boolean(toClick));
		}
	}

}
