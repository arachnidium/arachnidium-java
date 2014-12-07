package modeling.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.github.arachnidium.core.HowToGetPage;
import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
//import com.github.arachnidium.model.browser.BrowserApplication;
import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.AccountOptions;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.Document;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.FilterListAndButton;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.GoogleDriveMainPage;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.GoogleSearchBar;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.ItemList;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.LoginToGoogleService;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.ShareSettingsDialog;
import com.github.arachnidium.tutorial.app_modeling.web.unannotated_pageobjects.SpreadSheet;

public class WebTest_GoogleDrive_ComplexButFlexible {
	private final String GOOGLE_DRIVE = "https://drive.google.com";
	//please don't use this account. There is nothing interesting for you. 
	private final String USER = "ArachnidiumTester"; //I have created it only for this test.
	private final String PASSWORD = "ArachnidTester_456"; //It is temporary. Later
	//I will made a simple site which will be used by the similar test 
		
	
	private Application<?, HowToGetPage> googleDrive;
	//or googleDrive = new WebFactory(ESupportedDrivers.CHROME).launch(BrowserApplication.class);
	private GoogleDriveMainPage googleDriveMainPage;
	
	@SuppressWarnings("unchecked")
	@Before //performs the opening of the Drive Google
	//and login to the account
	public void setUp() throws Exception {
		googleDrive = new WebFactory(ESupportedDrivers.CHROME).launch(Application.class, GOOGLE_DRIVE);	
		// or googleDrive = new WebFactory(ESupportedDrivers.CHROME).launch(BrowserApplication.class);
		
		LoginToGoogleService<?> login = 
		/**It is supposed that here login page 
		 * is loaded -->*/ googleDrive.getPart(LoginToGoogleService.class);
		login.clickOnPersistentCookie();
		login.setEmail(USER);
		login.setPassword(PASSWORD);
		login.singIn(); /**<-- it is supposed that after this the browser
		should be navigated to https://drive.google.com */
		
		/**So, we have to check that browser is at https://drive.google.com*/
		/**before test is run.*/
		HowToGetPage howToGetGoogleDriveMainPage = new HowToGetPage();
		/**It is a strategy. It the similar as By from the Selenium. But it is need
		 * for the recognition of the whole page*/
		howToGetGoogleDriveMainPage.setExpected(0); /**<-- it is window/tab index*/
		List<String> expectedURLs = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("someservice/someurl");
				add(GOOGLE_DRIVE);
			}
		}; /**<- it is the list of expected URLs which are defined by 
		regular expressions*/
		howToGetGoogleDriveMainPage.setExpected(expectedURLs);
		howToGetGoogleDriveMainPage.setExpected("Google"); /**It is expected title of the page.
		This title is defined by regular expression*/
		
		/**So this way we instantiate Drive Google main page representation*/
		/**The first request to this object will activate the given HowToGetPage strategy 
		 * and the current location will be checked*/
		googleDriveMainPage = googleDrive.getPart(GoogleDriveMainPage.class, howToGetGoogleDriveMainPage);
	}	
	
	@Test
	public void test() {
		/**As you can see there is no need to describe the whole page. To avoid the 
		 * God Object implementation each part of any page that is supposed to be repeatable
		 * can be described separately. Classes which come out can be used this way*/
		FilterListAndButton filterListAndButton = googleDriveMainPage. /**Lets image that there is more than one widgets/
		        similar pieces of browser UI. So in order to avoid confusion we can define the root element using
		        Selenium locator By-strategy*/
				getPart(FilterListAndButton.class, By.id("navpane"));	
		filterListAndButton.choseSection(0); //Selects My Disc
		
		GoogleSearchBar googleSearchBar = googleDriveMainPage.getPart(GoogleSearchBar.class, By.id("gbqfw"));
		googleSearchBar.performSearch("TestDocument"); //the similar example
		//performs the searching for the document using Google text edit and button
		//These elements can be found in any Gooogle service
		
		ItemList itemList = googleDriveMainPage.getPart(ItemList.class, By.id("viewpane"));
		itemList.clickOnDoc("TestDocument"); //Clicks on document in order to open it
		
		/**
		 * Later we get the opened document from the new opened window/tab
		 */
		
		/**Here we set up parameters for document recognition*/
		HowToGetPage howToGetDoc = new HowToGetPage();
		/**It is a strategy. It the similar as By from the Selenium. But it is need
		 * for the recognition of the whole page*/
		howToGetDoc.setExpected(1); /**<-- it is window/tab index. This is optional for this example*/
		List<String> expectedURLs = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("//docs.google.com/");
				add("//docs.google.com/document/");
			}
		}; /**<- it is the list of expected URLs which are defined by 
		regular expressions*/
		howToGetDoc.setExpected(expectedURLs);
		//howToGetDoc.setExpected("TestDocument"); /**It is expected title of the page.
		//This title is defined by regular expression*/
		
		/**So this way we instantiate Document page representation*/
		/**The first request to this object will activate the given HowToGetPage strategy 
		 * and the current location will be checked*/
		/**
		 * Here is an example how to find something in the new opened window
		 */
		Document document = googleDrive.getPart(Document.class, howToGetDoc);
		document.clickShareButton(); //invokes the document permissions settings form		
		/**
		 * This form is usually placed inside the iframe. So the path to this
		 * frame should be defined by strategy which is similar as By-strategy 
		 * from the Selenium 
		 */
		HowToGetByFrames framePath = new HowToGetByFrames();
		framePath.addNextFrame(By.className("share-client-content-iframe"));
		//there are possible variants:
		//framePath.addNextFrame("string path to frame"); //String path
		//framePath.addNextFrame(1); //frame index
		
		/**
		 * And the the object instantiates like this
		 */
		ShareSettingsDialog shareSettings = document.getPart(ShareSettingsDialog.class, framePath);
		
		/**Ok! We try to do something with this form further*/
		shareSettings.clickOnManagePermissions();
		shareSettings.invite("arachnidiumtester@gmail.com");
		shareSettings.clickCancel();
		shareSettings.clickDone();
		
		/**We will try to the same with the spreadsheet*/
		googleSearchBar.performSearch("TestSpreadSheet");
		itemList.clickOnDoc("TestSpreadSheet"); 
		
		HowToGetPage howToGetTable = new HowToGetPage();
		List<String> expectedURLs2 = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("//docs.google.com/spreadsheets/");
			}
		}; /**<- it is the list of expected URLs which are defined by 
		regular expressions*/
		howToGetTable.setExpected(expectedURLs2);
		
		SpreadSheet table = googleDrive.getPart(SpreadSheet.class, howToGetTable);
		table.clickShareButton(); //invokes the document permissions settings form	
		
		//iframe is the same here
		ShareSettingsDialog shareSettings2 = table.getPart(ShareSettingsDialog.class, framePath);
		shareSettings2.clickOnManagePermissions();
		shareSettings2.invite("arachnidiumtester@gmail.com");
		shareSettings2.clickCancel();
		shareSettings2.clickDone();
		
		/**
		 * Finally, we are closing all which is opened... 
		 */
		document.close();
		table.close();
		
		/**
		 * and perform the logout from here
		 */
		googleDriveMainPage.clickOnVisibleProfile();
		AccountOptions<?> accountOptions = googleDriveMainPage.
				getPart(AccountOptions.class, By.xpath(".//*[@class='gb_ia gb_D']"));
		accountOptions.quit();
		/**The test is finished*/
	}

	@After
	public void tearDown(){
		googleDrive.quit();
	}

}
