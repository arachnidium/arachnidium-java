package mobile.android;

import io.appium.java_client.android.AndroidKeyCode;

import java.util.ArrayList;
import java.util.List;


//import com.github.arachnidium.mobile.android.selendroid.testapp.ImplicitlyDefinedWebViewFrame;
import com.github.arachnidium.util.configuration.Configuration;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.github.arachnidium.core.HowToGetMobileScreen;
import com.github.arachnidium.mobile.android.bbc.BBCMain;
import com.github.arachnidium.mobile.android.bbc.TopicList;
import com.github.arachnidium.mobile.android.selendroid.testapp.HomeScreenActivity;
import com.github.arachnidium.mobile.android.selendroid.testapp.RegisterANewUser;
import com.github.arachnidium.mobile.android.selendroid.testapp.Webview;
import com.github.arachnidium.model.mobile.MobileApplication;
import com.github.arachnidium.model.mobile.MobileFactory;

public class AndroidTestExamples {

  /**
   * Test is run on Android 4.4	
   */
  @Test
  public void androidNativeAppTest() {
		Configuration config = Configuration
				.get("src/test/resources/configs/mobile/app/android/android_bbc.json");
		MobileApplication bbc = new MobileFactory(config).launch(MobileApplication.class);
		try {
			BBCMain bbcMain = bbc.getPart(BBCMain.class);
			Assert.assertNotSame("", bbcMain.getAppStrings());
			Assert.assertNotSame(0, bbcMain.getArticleCount());
			Assert.assertNotSame("", bbcMain.getArticleTitle(1));
			Assert.assertNotSame(bbcMain.getArticleTitle(1),
					bbcMain.getArticleTitle(0));
			bbcMain.selectArticle(1);
			Assert.assertEquals(true, bbcMain.isArticleHere());
			bbcMain.zoomArticle();
			bbcMain.goHome();
			bbcMain.refresh();
			bbcMain.edit();

			TopicList<?> topicList = bbcMain.getPart(TopicList.class);
			topicList.setTopicChecked("LATIN AMERICA", true);
			topicList.setTopicChecked("UK", true);
			topicList.ok();

			bbcMain.edit();
			topicList.setTopicChecked("LATIN AMERICA", false);
			topicList.setTopicChecked("UK", false);
			topicList.ok();
			
			bbcMain.sendKeyEvent(AndroidKeyCode.ENTER);
			bbcMain.play();
		} finally {
			bbc.quit();
		}	  
  }
  
  @Test
  public void androidHybridAppTest() {
		Configuration config = Configuration
				.get("src/test/resources/configs/mobile/app/android/android_selendroid-test-app.json");
		MobileApplication selendroidTestApp = new MobileFactory(config).launch(MobileApplication.class);
		try {
			HomeScreenActivity homeScreenActivity = selendroidTestApp.getPart(HomeScreenActivity.class);
		
			homeScreenActivity.fillMyTextField("Test text. Hello world!");
			homeScreenActivity.clickOnVisibleButtonTest();
			homeScreenActivity.waitForVisibleTextIsVisible(10);
			Assert.assertEquals("Text is sometimes displayed", 
					homeScreenActivity.getVisibleTextView());
			homeScreenActivity.waitingButtonTestClick();
			
			RegisterANewUser registerForm = selendroidTestApp.getPart(RegisterANewUser.class);
			registerForm.inputUsername("MrSergeyTikhomirov");
			registerForm.inputEmail("tichomirovsergey@gmail.com");
			registerForm.inputPassword("test666");
			registerForm.inputName("Mr Sergey Tikhomirov");
			registerForm.clickVerifyUser();
			registerForm.clickRegisterUser();
				
			
			homeScreenActivity.startWebviewClick();
			HowToGetMobileScreen h = new HowToGetMobileScreen();
			List<String> activities = new ArrayList<String>();
			activities.add("WebViewActivity");			
			h.setExpected(activities);
			h.setExpected(1);
			
			Webview webview = selendroidTestApp.getPart(Webview.class, h);	
			webview.refresh();
			webview.setName("Sergey");
			webview.selectCar("mercedes");
			webview.sendMeYourName();	
			webview.getCurrentUrl();
			webview.refresh();
			webview.to("https://www.google.com");
			webview.back();
			
			homeScreenActivity.goBackClick();
			
			homeScreenActivity.startWebviewClick();
			homeScreenActivity.clickOnSpinner();
			homeScreenActivity.selectSpinnerItem("iframes");
			
			//ImplicitlyDefinedWebViewFrame implicitlyDefinedWebView = 
			//		selendroidTestApp.getPart(ImplicitlyDefinedWebViewFrame.class, h);
			//implicitlyDefinedWebView.getCurrentUrl();
			//implicitlyDefinedWebView.clickOnFoo();
			selendroidTestApp.getManager();
		} finally {
			selendroidTestApp.quit();
		}
	}  
}
