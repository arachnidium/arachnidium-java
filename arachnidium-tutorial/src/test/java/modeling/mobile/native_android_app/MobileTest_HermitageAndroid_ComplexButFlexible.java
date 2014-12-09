package modeling.mobile.native_android_app;

import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
	   unannotated_pageobjects.HermitageMuseumMainScreen;  /**<==*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
       unannotated_pageobjects.HermitageMuseumQuickGuide; /**<==*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
	   unannotated_pageobjects.InformationAboutTickets;   /**<==*/
import com.github.arachnidium.tutorial.app_modeling.mobile.native_app.
	   unannotated_pageobjects.TheHistoryOfTheHermitage; /**<==*/

public class MobileTest_HermitageAndroid_ComplexButFlexible {

	private Application<?, HowToGetMobileScreen> hermitage;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		hermitage = new MobileFactory(
				ESupportedDrivers.ANDROID_APP,
				new DesiredCapabilities() {
					private static final long serialVersionUID = 1L;
					{
						setCapability(MobileCapabilityType.APP, new File(
								"src/test/resources/org.hermitagemuseum.apk")
								.getAbsolutePath()); //here is the path to *.apk, *.zip, *.app files
						setCapability(MobileCapabilityType.APP_WAIT_PACKAGE,
								"org.hermitagemuseum"); //Android app package we wait for 
						setCapability(MobileCapabilityType.APP_WAIT_ACTIVITY,
								"ui.activities.AcHermitageSplash_"); //Android app activity we wait for 
						setCapability(MobileCapabilityType.DEVICE_NAME,
								"Android Emulator");
					}
				}, new URL("http://127.0.0.1:4723/wd/hub")). //URL of the remote host where Appium NodeJS server is launched
				launch(Application.class 
						//MobileApplication.class
						//AndroidApp.class  are available here
						); 
			//When app is launched then we close the quick tab guide
		    hermitage.getPart(HermitageMuseumQuickGuide.class).close();
	}

	@After
	public void tearDown() throws Exception {
		hermitage.quit();
	}

	@Test
	public void test() {
		//here we want to see the history of the museum
		HermitageMuseumMainScreen mainScreen = hermitage.getPart(HermitageMuseumMainScreen.class);
		mainScreen.clickHistory();
		
		//ok!
		//We want to read the history. It is native content which we should 
		//get by some conditions
		HowToGetMobileScreen howToGet = new HowToGetMobileScreen();
		howToGet.setExpected("NATIVE_APP"); /**<= here I set the required context name. It can be full name 
		or defined by regular expression*/
		howToGet.setExpected(0);  /**<= here I set the required context index. Here it is just example*/
		List<String> activities = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("SomeActivity"); /**<= here I set expressions of possible activities. They can by defined by regular 
				expressions*/
				add("AcSingleFragment_"); /**They are used when here is Android and they are ignored when here is iOS*/
			}
		};
		howToGet.setExpected(activities);
		
		TheHistoryOfTheHermitage theHistoryOfTheHermitage = 
				hermitage.getPart(TheHistoryOfTheHermitage.class, howToGet, 
						By.id("android:id/action_bar_overlay_layout"));
		/**We should see that it is the history of the museum is shown*/
		Assert.assertEquals("History", theHistoryOfTheHermitage.getTitle());
		/**We look at pictures and read the text*/
		Assert.assertNotEquals(0, theHistoryOfTheHermitage.getPictiresCount());
		Assert.assertNotEquals(0, theHistoryOfTheHermitage.getParagraphCount());
		/**Ok! And then we get back to the general screen*/
		theHistoryOfTheHermitage.back();
		
		/**And now we want to see the price of entry ticket*/
		mainScreen.clickTickets();
		InformationAboutTickets informationAboutTickets = hermitage.getPart(InformationAboutTickets.class, 
				howToGet, /**<== Here we use the same strategies. 
				Actually it is the similar UI as the history of museum*/
				By.id("android:id/action_bar_overlay_layout")); 
		/**We should see that the entry price is here*/
		Assert.assertEquals(true, informationAboutTickets.isEntryTicketLabelVisible());
		Assert.assertEquals(true, informationAboutTickets.isTicketForNonProfitPhotographyAndVideoFilmingVisible());
		
		/**Ok! And then we get back to the general screen*/
		theHistoryOfTheHermitage.back();		
	}

}
