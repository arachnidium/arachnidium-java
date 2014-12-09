package modeling.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
//import com.github.arachnidium.model.browser.BrowserApplication;
import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.AccountOptions;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.Document;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.FilterListAndButton;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.GoogleDriveMainPage;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.GoogleSearchBar;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.ItemList;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.LoginToGoogleService;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.ShareSettingsDialog;
import com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects.SpreadSheet;

public class WebTest_Simplified_as_There_is_Default_Behavior_and_Structure {
	private final String GOOGLE_DRIVE = "https://drive.google.com";
	//please don't use this account. There is nothing interesting for you. 
	private final String USER = "ArachnidiumTester"; //I have created it only for this test.
	private final String PASSWORD = "ArachnidTester_456"; //It is temporary. Later
	//I will made a simple site which will be used by the similar test 
		
	
	private Application<?, ?> googleDrive;
	//or googleDrive = new WebFactory(ESupportedDrivers.CHROME).launch(BrowserApplication.class);
	private GoogleDriveMainPage googleDriveMainPage;
	
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
		/**The first request to this object will check the current location*/
		googleDriveMainPage = googleDrive.getPart(GoogleDriveMainPage.class);
	}	
	
	@Test
	public void test() {
		/**As you can see there is no need to describe the whole page. To avoid the 
		 * God Object implementation each part of any page that is supposed to be repeatable
		 * can be described separately. Classes which come out can be used this way*/
		FilterListAndButton filterListAndButton = googleDriveMainPage.getPart(FilterListAndButton.class);	
		filterListAndButton.choseSection(0); //Selects My Disc
		
		GoogleSearchBar googleSearchBar = googleDriveMainPage.getPart(GoogleSearchBar.class);
		googleSearchBar.performSearch("TestDocument"); //It performs the searching for the 
		//document using Google text edit and button
		//These elements can be found in any Gooogle service
		
		ItemList itemList = googleDriveMainPage.getPart(ItemList.class);
		itemList.clickOnDoc("TestDocument"); //Clicks on document in order to open it
	
		Document document = googleDrive.getPart(Document.class);
		document.clickShareButton(); //invokes the document permissions settings form		
		/**
		 * This form is usually placed inside the iframe. 
		 */
		ShareSettingsDialog shareSettings = document.getPart(ShareSettingsDialog.class);
		
		/**Ok! We try to do something with this form further*/
		shareSettings.clickOnManagePermissions();
		shareSettings.invite("arachnidiumtester@gmail.com");
		shareSettings.clickCancel();
		shareSettings.clickDone();
		
		/**We will try to the same with the spreadsheet*/
		googleSearchBar.performSearch("TestSpreadSheet");
		itemList.clickOnDoc("TestSpreadSheet"); 
		
		SpreadSheet table = googleDrive.getPart(SpreadSheet.class);
		table.clickShareButton(); //invokes the document permissions settings form	
		
		ShareSettingsDialog shareSettings2 = table.getPart(ShareSettingsDialog.class);
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
				getPart(AccountOptions.class);
		accountOptions.quit();
		/**The test is finished*/
	}

	@After
	public void tearDown(){
		googleDrive.quit();
	}

}
