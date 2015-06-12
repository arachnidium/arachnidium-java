package com.github.arachnidium.tutorial.app_modeling.web.annotated_pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
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
public class ShareSettingsDialog extends FunctionalPart<Handle> {/** <==
	 * Here I demonstrate something that is supposed to be used by the web and 
	 * mobile testing 
	 */ 
	
	protected ShareSettingsDialog(Handle handle) {
		super(handle);
	}

	@FindBy(xpath = "//*[contains(@id,'simpleInviter')]//*[@class='simple-inviter-recipient-area']//textarea")
	private WebElement invite;
	@FindBy(xpath = "//*[contains(@id,'close')]")
	private WebElement done;	
	@FindBy(xpath = "//*[contains(@id,'cancel')]")
	private WebElement cancel;
	@FindBy(className = "simple-sharing-manage-permissions-link")
	private WebElement managePermissions;
	
	@InteractiveMethod /**<-- This annotations is useful for methods which simulate
	some interaction. By default the presence of it means that Webdriver should be focused
	on the window/context and switched to the required frame if currently this content is 
	placed inside iframe. All this will have been done before the annotated method
	will be invoked*/
	public void invite(String eMail){
		Actions a = new Actions(getWrappedDriver());
		invite.click();
		a.sendKeys(invite, eMail, Keys.ENTER);
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
