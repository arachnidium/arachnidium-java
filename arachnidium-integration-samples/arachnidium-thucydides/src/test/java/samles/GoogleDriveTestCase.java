package samles;

import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.runners.ThucydidesRunner;

import com.github.arachnidium.util.configuration.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.arachnidium.thucydides.steps.GoogleDriveSteps;

@RunWith(ThucydidesRunner.class)
public class GoogleDriveTestCase {
	private static String RESOURCE_PATH = "src/test/resources/";
	private static final String SETTIGS_OF_CHROME = "chrome.json";
	//please don't use this account. There is nothing interesting for you. 
    private final String USER = "ArachnidiumTester"; //I have created it only for demonstration of 
    //framework capabilities
	private final String PASSWORD = "ArachnidTester_456"; 

	
	@Steps
	GoogleDriveSteps steps;
	
	@Before
	public void setUp() throws Exception {
		steps.insatantiateApp(Configuration.get(RESOURCE_PATH + SETTIGS_OF_CHROME));
	}
	
	@Test
	public void sampleTestOnTheGoogleDrive(){
		steps.login(USER, PASSWORD);
		steps.startShare();
		steps.clickOnManagePermissions();
		steps.setInviteEmail("arachnidiumtester@gmail.com");
		steps.clickCancel();
		steps.clickFinishShare();
		steps.logOut();
	}

	@After
	public void tearDown() throws Exception {
		steps.quit();
	}

}
