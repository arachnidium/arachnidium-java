package modeling.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.arachnidium.core.settings.supported.ESupportedDrivers;
import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.tutorial.app_modeling.web.
    unannotated_pageobjects.aggregated_page_objects_with_annotated_fields.GoogleDriveApp;  /**<== */

public class WebTest_Very_Simple_Example1 {
	private final String GOOGLE_DRIVE = "https://drive.google.com";
	//please don't use this account. There is nothing interesting for you. 
	private final String USER = "ArachnidiumTester"; //I have created it only for this test.
	private final String PASSWORD = "ArachnidTester_789"; //It is temporary. Later
	//I will made a simple site which will be used by the similar test 
		
	
	private GoogleDriveApp googleDrive;
	
	@Before //performs the opening of the Drive Google
	//and login to the account
	public void setUp() throws Exception {
		googleDrive = new WebFactory(ESupportedDrivers.CHROME).launch(GoogleDriveApp.class, GOOGLE_DRIVE);			
		/**It is supposed that here login page 
		 * is loaded -->*/ googleDrive.loginToGoogleService.clickOnPersistentCookie();
		googleDrive.loginToGoogleService.setEmail(USER);
		googleDrive.loginToGoogleService.setPassword(PASSWORD);
		googleDrive.loginToGoogleService.singIn(); 
	}	
	
	@Test
	public void test() {
		googleDrive.googleDriveMainPage.filterListAndButton.choseSection(0); //Selects My Disc
		googleDrive.googleDriveMainPage.googleSearchBar.performSearch("TestDocument"); //It performs the searching for the 
		//document using Google text edit and button
		//These elements can be found in any Gooogle service
		
		googleDrive.googleDriveMainPage.itemList.clickOnDoc("TestDocument"); //Clicks on document in order to open it
	
		googleDrive.document.clickShareButton(); //invokes the document permissions settings form		
		/**
		 * This form is usually placed inside the iframe. 
		 */		
		/**Ok! We try to do something with this form further*/
		googleDrive.document.shareSettingsDialog.clickOnManagePermissions();
		googleDrive.document.shareSettingsDialog.invite("arachnidiumtester@gmail.com");
		googleDrive.document.shareSettingsDialog.clickCancel();
		googleDrive.document.shareSettingsDialog.clickDone();
		
		/**We will try to the same with the spreadsheet*/
		googleDrive.googleDriveMainPage.googleSearchBar.performSearch("TestSpreadSheet");
		googleDrive.googleDriveMainPage.itemList.clickOnDoc("TestSpreadSheet"); 
		
		googleDrive.spreadSheet.clickShareButton(); //invokes the document permissions settings form	

		googleDrive.spreadSheet.shareSettingsDialog.clickOnManagePermissions();
		googleDrive.spreadSheet.shareSettingsDialog.invite("arachnidiumtester@gmail.com");
		googleDrive.spreadSheet.shareSettingsDialog.clickCancel();
		googleDrive.spreadSheet.shareSettingsDialog.clickDone();
		
		/**
		 * Finally, we are closing all which is opened... 
		 */
		googleDrive.document.close();
		googleDrive.spreadSheet.close();
		
		/**
		 * and perform the logout from here
		 */
		googleDrive.googleDriveMainPage.clickOnVisibleProfile();
		googleDrive.googleDriveMainPage.accountOptions.quit();
		/**The test is finished*/
	}

	@After
	public void tearDown(){
		googleDrive.quit();
	}

}
