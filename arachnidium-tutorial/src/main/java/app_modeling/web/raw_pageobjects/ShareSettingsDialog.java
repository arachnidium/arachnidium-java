package app_modeling.web.raw_pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;

public class ShareSettingsDialog extends FunctionalPart<Handle> {
	@FindBy(xpath = "//*[contains(@id,'fakeRecipient')]")
	private WebElement invite;
	@FindBy(xpath = "//*[contains(@id,'close')]")
	private WebElement done;	
	@FindBy(xpath = "//*[contains(@id,'cancel')]")
	private WebElement cancel;
	@FindBy(className = "simple-sharing-manage-permissions-link")
	private WebElement managePermissions;
	
	public ShareSettingsDialog(FunctionalPart<?> parent, HowToGetByFrames path,
			By by) {
		super(parent, path, by);
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
	
	//Some more actions could be implemented here
	//.......

}
