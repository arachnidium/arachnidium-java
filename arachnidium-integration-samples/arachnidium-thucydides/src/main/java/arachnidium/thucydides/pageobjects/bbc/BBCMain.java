package arachnidium.thucydides.pageobjects.bbc;

import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;
import java.util.NoSuchElementException;

import org.arachnidium.core.Handle;
import org.arachnidium.model.common.FunctionalPart;
import org.arachnidium.model.support.annotations.classdeclaration.IfBrowserURL;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileAndroidActivity;
import org.arachnidium.model.support.annotations.classdeclaration.IfMobileContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Imagine that we have to check browser and Android versions
 * How?! See below.
 */
@IfBrowserURL(regExp = "http://www.bbc.com/news/")
@IfMobileContext(regExp = "NATIVE_APP")
@IfMobileAndroidActivity(regExp = "HomeWwActivity")
public class BBCMain extends FunctionalPart<Handle>{
	
	@FindBy(className = "someClass1")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/articleWrapper\")")
	private List<RemoteWebElement> articles;
	
	@FindBy(className = "someClass2")
	@AndroidFindBy(id = "bbc.mobile.news.ww:id/workspace")
	private RemoteWebElement currentArticle;
	
	@FindBy(className = "someClass3")
	@AndroidFindBy(id = "bbc.mobile.news.ww:id/optMenuShareAction")
	private RemoteWebElement share;
	
	@FindBy(className = "someClass4")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/optMenuWatchListenAction\")")
	private RemoteWebElement play;
	
	@FindBy(className = "someClass5")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/optMenuEditAction\")")
	private RemoteWebElement edit;
	
	@FindBy(className = "someClass6")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/optMenuRefreshAction\")")
	private RemoteWebElement refresh;
	
	protected BBCMain(Handle context) {
		super(context);
		load();
	}

	@InteractiveMethod
	public int getArticleCount(){
		return articles.size();
	}
	
	@InteractiveMethod
	public String getArticleTitle(int index){
			
		WebElement article = null;
		try{
			article = articles.get(index);
		}
		catch (Exception e){
			int count = articles.size();
			throw new NoSuchElementException("Required article index is more than actual article list size " + count);
		}
		
		return article.findElement(By.id("bbc.mobile.news.ww:id/articleTitleId")).getText();
	}
	
	@InteractiveMethod
	public void selectArticle(int index){
		try{
			articles.get(index).click();
		}
		catch (Exception e){
			int count = articles.size();
			throw new NoSuchElementException("Required article index is more than actual article list size " + count);
		}		
	}
	
	@InteractiveMethod
	public boolean isArticleHere(){
		return currentArticle.isDisplayed();
	}

	@InteractiveMethod
	public void refresh() {
		refresh.click();		
	}

	@InteractiveMethod
	public void share() {
		share.click();		
	}

	@InteractiveMethod
	public void play() {
		play.click();		
	}

	@InteractiveMethod
	public void edit() {
		edit.click();
	}

}
