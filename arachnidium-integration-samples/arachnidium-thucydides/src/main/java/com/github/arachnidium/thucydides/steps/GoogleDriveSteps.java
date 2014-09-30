package com.github.arachnidium.thucydides.steps;

import net.thucydides.core.annotations.Step;

import com.github.arachnidium.util.configuration.Configuration;

import com.github.arachnidium.model.browser.WebFactory;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.thucydides.pageobjects.googledrive.Document;
import com.github.arachnidium.thucydides.pageobjects.googledrive.LogOut;
import com.github.arachnidium.thucydides.pageobjects.googledrive.LoginToGoogleService;
import com.github.arachnidium.thucydides.pageobjects.googledrive.ShareDocumentSettings;

public class GoogleDriveSteps extends StepsWithApplication {
	private static final long serialVersionUID = 1L;
	private static String DOC_LINK = "https://docs.google.com/document/d/1PNtBQwSHQyedeIZqV5VYakPWJtW5incbuiKekB33HyQ/edit";

	@Override
	@Step
	public void insatantiateApp(Configuration configuration) {
		setInstantiatedApplication(WebFactory.getApplication(Application.class,
				configuration, DOC_LINK));
	}

	@Step
	public void login(String eMail, String password) {
		LoginToGoogleService<?> loginToGoogleService = app
				.getPart(LoginToGoogleService.class);
		loginToGoogleService.clickOnPersistentCookie();
		loginToGoogleService.setEmail(eMail);
		loginToGoogleService.setPassword(password);
		loginToGoogleService.singIn();
	}

	@Step
	public void startShare() {
		Document<?> document = app.getPart(Document.class);
		document.clickShareButton();
	}

	@Step
	public void setInviteEmail(String whoWillBeInvited) {
		Document<?> document = app.getPart(Document.class);
		ShareDocumentSettings<?> shareSettings = document
				.getPart(ShareDocumentSettings.class);
		shareSettings.invite(whoWillBeInvited);
	}

	@Step
	public void clickCancel() {
		Document<?> document = app.getPart(Document.class);
		ShareDocumentSettings<?> shareSettings = document
				.getPart(ShareDocumentSettings.class);
		shareSettings.clickCancel();
	}

	@Step
	public void clickFinishShare() {
		Document<?> document = app.getPart(Document.class);
		ShareDocumentSettings<?> shareSettings = document
				.getPart(ShareDocumentSettings.class);
		shareSettings.clickDone();
	}

	@Step
	public void logOut() {
		Document<?> document = app.getPart(Document.class);
		LogOut<?> logOut = document.getPart(LogOut.class);
		logOut.clickOnProfile();
		logOut.quit();
		;
	}
	
	@Step
	public void clickOnManagePermissions(){
		Document<?> document = app.getPart(Document.class);
		ShareDocumentSettings<?> shareSettings = document
				.getPart(ShareDocumentSettings.class);
		shareSettings.clickOnManagePermissions();
	}	
}
