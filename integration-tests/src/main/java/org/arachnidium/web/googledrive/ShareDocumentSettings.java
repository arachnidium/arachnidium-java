package org.arachnidium.web.googledrive;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.annotations.classdeclaration.Frame;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

@Frame(howToGet = How.CLASS_NAME, locator = "share-client-content-iframe")
public class ShareDocumentSettings<S extends Handle> extends FunctionalPart<S> {

	@FindBy(xpath = ".//*[contains(@id,'fakeRecipient')]")
	private WebElement invite;
	@FindBy(xpath = ".//*[contains(@id,'close')]")
	private WebElement done;	
	@FindBy(xpath = ".//*[contains(@id,'cancel')]")
	private WebElement cancel;
	@FindBy(className = "simple-sharing-manage-permissions-link")
	private WebElement managePermissions;
	
	protected ShareDocumentSettings(FunctionalPart<S> parent,
			HowToGetByFrames path) {
		super(parent, path);
		load();
	}
	
	protected ShareDocumentSettings(S handle,
			HowToGetByFrames path) {
		super(handle, path);
		load();
	}	
	
	@InteractiveMethod
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
}
