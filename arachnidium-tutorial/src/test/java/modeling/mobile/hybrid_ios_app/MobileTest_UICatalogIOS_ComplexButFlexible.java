package modeling.mobile.hybrid_ios_app;


import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.HowToGetPage;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		unannotated_pageobjects.AppleCom; /**<==!!!*/
//import com.github.arachnidium.model.support.annotations.MobileContextNamePatterns;
import com.github.arachnidium.tutorial.app_modeling.mobile.hybrid_app.
		unannotated_pageobjects.UICatalog; /**<==!!!*/

public class MobileTest_UICatalogIOS_ComplexButFlexible {

	private Application<?, HowToGetMobileScreen> uiCatalogApp;
	
	@SuppressWarnings("unchecked")
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
				launch(Application.class 
						//MobileApplication.class
						//IOSApp.class  are available here
						); 
		//When app is launched then we chose the "WebView" section
		uiCatalogApp.getPart(UICatalog.class).selectItem("Web View");
	}

	@After
	public void tearDown() throws Exception {
		uiCatalogApp.quit();
	}

	@Test
	public void test() {
		/**We want to interact with apple.com which is loaded
		 * in web view*/
		/**So want to be sure that it is the apple.com before actions are
		 * performed*/
		HowToGetMobileScreen howToGet = new HowToGetMobileScreen();
		howToGet.setExpected("WEBVIEW"); /**<= here I set the required context name. It can be full name 
		or name defined by regular expression*/ 
		//howToGet.setExpected(MobileContextNamePatterns.WEBVIEW);		
		howToGet.setExpected(0);  /**<= here I set the required context index. Here it is just example*/
		
		/**And... Right now we define the desired page */
		HowToGetPage howToGetPage = new HowToGetPage();
		/**It is a strategy. It the similar as By from the Selenium. But it is need
		 * for the recognition of the whole page*/
		howToGetPage.setExpected(0); /**<-- it is window/tab index. It is here just as example*/
		List<String> expectedURLs = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("someservice/someurl");
				add(".apple.com");
			}
		}; /**<- it is the list of expected URLs which are defined by 
		regular expressions*/
		howToGetPage.setExpected(expectedURLs);
		howToGetPage.setExpected("Apple"); /**It is expected title of the page.
		This title is defined by regular expression*/
		
		/**and here we set */ 
		howToGet.defineHowToGetPageStrategy(howToGetPage); /**It means that if the required context 
		(with WEBVIEW-like name) is found then it will wait/search for the required page. It the required context
		is NATIVE_APP then parameters of the page will be ignored*/
		
		
		/**So this way we instantiate apple.comt page representation*/
		/**The first request to this object will activate the given HowToGetMobileScreen strategy 
		 * and the current location will be checked. As it is web view and the strategy of the searching
		 * for a page was defined so the required page which is supposed to be loaded should be found*/
		/**
		 * Here is an example how to find the WEBVIEW context and loaded page
		 */
		AppleCom appleCom = uiCatalogApp.getPart(AppleCom.class, howToGet);
		appleCom.selectLink("Store");
		appleCom.selectShop("Shop iPhone");
		//Later we read something about iPhone
		//...................................
	}

}
