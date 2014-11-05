package simple;

import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.model.mobile.android.AndroidApp;
import com.github.arachnidium.tutorial.simple.mobile.UserScreen;
import com.github.arachnidium.tutorial.simple.mobile.VKLogin;
import com.github.arachnidium.tutorial.simple.mobile.Videos;


//This is the test of a native Android application example.
//Here we use VK (https://play.google.com/store/apps/details?id=com.vkontakte.android) as an application under test.
//This test performs:
//- login to the social network
//- selection of the Video section
//- the searching for video which name contains "Selenium"  
//- the selection of the first video which is found
public class MobileTest {
	
	private Application<?, ?> vk;
	//There are another possible declaration variant:
	//
	//private Application<Handle, IHowToGetHandle> vk;
	//
	//private MobileApplication vk; - if we are going to perform tests
	//only against mobile client
	//
	//private AndroidApp vk; - if we are going to perform tests
	//only against Adroid mobile client
	//
	//private IOSApp vk; - if we are going to perform tests
	//only against iOS mobile client

	/**
	 * We launch VK and run test against Android 4.4.4
	 */
	@Before
	public void setUp() throws Exception {
		
		//com.github.arachnidium.model.mobile.MobileFactory is a factory
		//which instantiates:
		
		//- com.github.arachnidium.model.common.Application - it the default representation  
		//of the launched client (browser as well as mobile).
				
		//- com.github.arachnidium.model.mobile.MobileApplication - it the default representation  
		//of the launched mobile client. Actually it is com.github.arachnidium.model.common.Application
		//subclass which is already adapted to mobile apps. 
		//
		//com.github.arachnidium.model.mobile
		//...
		//public abstract class MobileApplication extends Application<MobileScreen, HowToGetMobileScreen> implements 
		//DeviceActionShortcuts, LocationContext {
				
		//- customized subclasses of com.github.arachnidium.model.common.Application and 
		//com.github.arachnidium.model.mobile.MobileApplication
		//(!!!)
		//except com.github.arachnidium.model.browser.BrowserApplication and its subclasses		
		vk = new MobileFactory(
				ESupportedDrivers.ANDROID_APP,
				new DesiredCapabilities() {
					private static final long serialVersionUID = 1L;
					{
						setCapability(MobileCapabilityType.APP, new File(
								"src/test/resources/com.vkontakte.android.apk")
								.getAbsolutePath()); //here is the path to *.apk, *.zip, *.app files
						setCapability(MobileCapabilityType.APP_PACKAGE,
								"com.vkontakte.android"); //Desired Android app package
						setCapability(MobileCapabilityType.APP_ACTIVITY,
								"LoginActivity"); //Desired Android app activity
						setCapability(MobileCapabilityType.DEVICE_NAME,
								"Android Emulator");
					}
				}, new URL("http://127.0.0.1:4723/wd/hub")). //URL of the remote host where Appium NodeJS server is launched
				launch(//Application.class, 
						//MobileApplication.class
						AndroidApp.class);  
		
		//Here we perform log in
		VKLogin<?> vkLogin = vk.getPart(VKLogin.class);
		//By default we consider that all elements are located
		//at the first context (NATIVE_APP). More advanced example
		//when there are more than one context (NATIVE_APP and WEBVIEWs (some could have content inside iframes))
		//will be described in another chapters
		vkLogin.setLogin("ArachnidiumTester@gmail.com"); //set eMail or phone number
		vkLogin.setPassword("ArachnidTester123");//set passoword
		vkLogin.enter();//enter
		/**
		 * When here is Android we have to launch activity 'MainActivity' forcefully
		 * is it a bug of VK? 
		 */
		Thread.sleep(3000);
		((AndroidApp) vk).startActivity("com.vkontakte.android", "MainActivity");
	}

	@Test
	public void test() throws Exception{		
		UserScreen<?> userScreen = vk.getPart(UserScreen.class);
		userScreen.goHome();
		userScreen.selectVideos();
		
		
		Videos<?> videos = userScreen.getPart(Videos.class); //Here we take that Videos is the 
		//child widget which appears when we select "Video"		
		
		videos.clickSearchButton(); //click on search button
		videos.enterSearchString("Selenium");//search for a video
		Assert.assertNotEquals(0, videos.getVideosCount()); //check that video is found
		videos.playVideo(0);//select the first video
	}
	
	@After
	public void tearDown(){
		vk.quit();
	}
}
