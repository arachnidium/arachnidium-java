package modeling.mobile.hybrid_ios_app;


import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
	annotated_pageobjects.aggregated_page_objects.UICatalogApp; /**<==!!!*/

public class MobileTest_UICatalogIOS_Very_Simple_Example2 {

	private UICatalogApp uiCatalogApp;
	
	@Before
	public void setUp() throws Exception {
		uiCatalogApp = new MobileFactory(
				ESupportedDrivers.IOS_APP,
				new DesiredCapabilities() {
					private static final long serialVersionUID = 1L;
					{
						setCapability(MobileCapabilityType.APP, new File(
								"src/test/resources/UICatalog.app.zip")
								.getAbsolutePath()); //here is the path to *.apk, *.zip, *.app files
						setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
						setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Simulator");
					}
				}, new URL("http://127.0.0.1:4723/wd/hub")). //URL of the remote host where Appium NodeJS server is launched
				launch(UICatalogApp.class 
						//MobileApplication.class
						//IOSApp.class  are available here
						); 
		//When app is launched then we chose the "WebView" section
		uiCatalogApp.uiCatalog.selectItem("Web View");
	}

	@After
	public void tearDown() throws Exception {
		uiCatalogApp.quit();
	}

	@Test
	public void test() {
		/**We want to interact with apple.com which is loaded
		 * in web view*/
		uiCatalogApp.appleCom.selectLink("Store");
		uiCatalogApp.appleCom.selectShop("Shop iPhone");
		//Later we read something about iPhone
		//...................................
	}

}
