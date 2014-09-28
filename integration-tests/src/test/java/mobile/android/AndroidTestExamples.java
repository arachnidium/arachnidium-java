package mobile.android;

import io.appium.java_client.android.AndroidKeyCode;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.arachnidium.core.HowToGetMobileScreen;
import org.arachnidium.mobile.android.bbc.BBCMain;
import org.arachnidium.mobile.android.bbc.TopicList;
import org.arachnidium.mobile.android.selendroid.testapp.HomeScreenActivity;
import org.arachnidium.mobile.android.selendroid.testapp.ImplicitlyDefinedWebViewFrame;
import org.arachnidium.mobile.android.selendroid.testapp.RegisterANewUser;
import org.arachnidium.mobile.android.selendroid.testapp.Webview;
import org.arachnidium.model.mobile.MobileApplication;
import org.arachnidium.model.mobile.MobileFactory;
import org.arachnidium.util.configuration.Configuration;
import org.testng.annotations.Test;

public class AndroidTestExamples {
	
  @Test
  public void androidNativeAppTest() {
		Configuration config = Configuration
				.get("src/test/resources/configs/mobile/app/android/android_bbc.json");
		MobileApplication bbc = MobileFactory.getApplication(
				MobileApplication.class, config);
		try {
			BBCMain bbcMain = bbc.getPart(BBCMain.class);
			Assert.assertNotSame("", bbcMain.getAppStrings());
			Assert.assertNotSame(0, bbcMain.getArticleCount());
			Assert.assertNotSame("", bbcMain.getArticleTitle(1));
			Assert.assertNotSame(bbcMain.getArticleTitle(1),
					bbcMain.getArticleTitle(0));
			bbcMain.selectArticle(1);
			Assert.assertEquals(true, bbcMain.isArticleHere());
			bbcMain.pinchArticle();
			bbcMain.zoomArticle();
			
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
		MobileApplication selendroidTestApp = MobileFactory.getApplication(
				MobileApplication.class, config);		
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
			
			//hybrid part
			homeScreenActivity.startWebviewClick();
			Webview webview = selendroidTestApp.getPart(Webview.class, 1);			
			webview.setName("Sergey");
			webview.selectCar("mercedes");
			webview.sendMeYourName();
			homeScreenActivity.goBackClick();
			
			
			homeScreenActivity.startWebviewClick();
			HowToGetMobileScreen h = new HowToGetMobileScreen();
			h.setExpected("WEBVIEW_0");
			webview = selendroidTestApp.getPart(Webview.class, h);			
			webview.setName("Sergey");
			webview.selectCar("mercedes");
			webview.sendMeYourName();			
			homeScreenActivity.goBackClick();
			
			List<String> activities = new ArrayList<String>();
			activities.add("WebViewActivity");			
			h.setExpected(activities);
			h.setExpected(1);
			
			homeScreenActivity.startWebviewClick();
			webview = selendroidTestApp.getPart(Webview.class, h);			
			webview.setName("Sergey");
			webview.selectCar("mercedes");
			webview.sendMeYourName();			
			homeScreenActivity.goBackClick();
			
			homeScreenActivity.startWebviewClick();
			homeScreenActivity.clickOnSpinner();
			homeScreenActivity.selectSpinnerItem("iframes");
			
			ImplicitlyDefinedWebViewFrame implicitlyDefinedWebView = selendroidTestApp.getPart(ImplicitlyDefinedWebViewFrame.class);
			implicitlyDefinedWebView.clickOnFoo();
			selendroidTestApp.getManager();
		} finally {
			selendroidTestApp.quit();
		}
	}
}
