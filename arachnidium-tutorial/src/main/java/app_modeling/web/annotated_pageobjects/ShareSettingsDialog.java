package app_modeling.web.annotated_pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.Frame;

@Frame(howToGet = How.CLASS_NAME, locator = "share-client-content-iframe") /**<--
If class is annotated by @Frame it means that the desired content is located inside 
iframe.  All @Frame declarations describe the path to the desired iframe. Declaration is applied to subclasses till they are 
annotated by @Frame with 
another values. Also if the class is going to be instantiated by {@link FunctionalPart#getPart(Class, com.github.arachnidium.model.support.HowToGetByFrames)}
then the given HowToGetByFrames-strategy will be used instead of declared by annotations*/ 
//@Frame(stringPath = "some|path")
//@Frame(frameIndex = 1)

/**it is the example which demonstrates how to implement a child page object*/
public class ShareSettingsDialog extends FunctionalPart<Handle> { /** <==
	 * Here I demonstrate something that is supposed to be used by the web and 
	 * mobile testing 
	 */
	@FindBy(xpath = "//*[contains(@id,'fakeRecipient')]")
	private WebElement invite;
	@FindBy(xpath = "//*[contains(@id,'close')]")
	private WebElement done;	
	@FindBy(xpath = "//*[contains(@id,'cancel')]")
	private WebElement cancel;
	@FindBy(className = "simple-sharing-manage-permissions-link")
	private WebElement managePermissions;
	
	/**
	 * If you want to represent some page object as a 
	 * "child" component of any page/screen then your implementation 
	 * should have constructors like these
	 * 
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart)}
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart, By)}
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart, com.github.arachnidium.model.support.HowToGetByFrames)}
	 * {@link FunctionalPart##FunctionalPart(FunctionalPart, com.github.arachnidium.model.support.HowToGetByFrames, By)}
	 * 
	 * As you can see the class should have (one of) constructors which instantiate it
	 *  class as a child of more generalized parent
	 */
	public ShareSettingsDialog(FunctionalPart<?> parent, HowToGetByFrames path) {
		super(parent, path);
	}
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void invite(String eMail){
		Actions a = new Actions(getWrappedDriver());
		a.sendKeys(invite, eMail);
		highlightAsInfo(invite, "eMails of people "
				+ "to be invited will be printed here");
		a.perform();
	}
	
	@InteractiveMethod
	public void clickDone(){
		done.click();
	}
	
	@InteractiveMethod
	public void clickCancel(){
		cancel.click();
	}
	
	@InteractiveMethod
	public void clickOnManagePermissions(){
		managePermissions.click();
	}	
	
	//Some more actions could be implemented here
	//.......

}
