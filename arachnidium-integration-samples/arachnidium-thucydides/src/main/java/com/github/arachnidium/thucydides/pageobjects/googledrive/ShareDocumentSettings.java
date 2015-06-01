package com.github.arachnidium.thucydides.pageobjects.googledrive;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.annotations.Frame;

@Frame(howToGet = How.CLASS_NAME, locator = "share-client-content-iframe")
public class ShareDocumentSettings<S extends Handle> extends FunctionalPart<S> {

	@FindBy(xpath = "//*[contains(@id,'simpleInviter')]//*[@class='simple-inviter-recipient-area']//textarea")
	private WebElement invite;
	@FindBy(xpath = ".//*[contains(@id,'close')]")
	private WebElement done;	
	@FindBy(xpath = ".//*[contains(@id,'cancel')]")
	private WebElement cancel;
	@FindBy(className = "simple-sharing-manage-permissions-link")
	private WebElement managePermissions;
	
	
	protected ShareDocumentSettings(S handle) {
		super(handle);
	}	
	
	@InteractiveMethod
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
}
