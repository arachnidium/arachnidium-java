package arachnidium.htmlelements.googledrive;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.annotations.classdeclaration.Frame;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

@Frame(howToGet = How.CLASS_NAME, locator = "share-client-content-iframe")
public class ShareDocumentSettings<S extends Handle> extends FunctionalPart<S> {

	@FindBy(xpath = ".//*[contains(@id,'fakeRecipient')]")
	private TextInput invite;
	@FindBy(xpath = ".//*[contains(@id,'close')]")
	private Button done;	
	@FindBy(xpath = ".//*[contains(@id,'cancel')]")
	private Button cancel;
	
	protected ShareDocumentSettings(FunctionalPart<S> parent,
			HowToGetByFrames path) {
		super(parent, path);
		// (!!!)
		HtmlElementLoader.populatePageObject(this,
						driverEncapsulation.getWrappedDriver());
	}
	
	protected ShareDocumentSettings(S handle,
			HowToGetByFrames path) {
		super(handle, path);
		// (!!!)
		HtmlElementLoader.populatePageObject(this,
								driverEncapsulation.getWrappedDriver());
	}	
	
	@InteractiveMethod
	public void invite(String eMail){
		Actions a = new Actions(driverEncapsulation.getWrappedDriver());
		a.sendKeys(invite.getWrappedElement(), eMail);
		highlightAsInfo(invite.getWrappedElement(), "eMails of people "
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
}
