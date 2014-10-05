package com.github.arachnidium.mobile.android.bbc;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.android.AndroidNativeContent;


public class BBCMain extends AndroidNativeContent implements IBar{
	
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/articleWrapper\")")
	private List<WebElement> articles;
	@FindBy(id = "bbc.mobile.news.ww:id/workspace")
	private WebElement currentArticle;			
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/optMenuShareAction\")")
	private WebElement share;
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/optMenuWatchListenAction\")")
	private WebElement play;
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/optMenuEditAction\")")
	private WebElement edit;
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bbc.mobile.news.ww:id/optMenuRefreshAction\")")
	private WebElement refresh;
	
	protected BBCMain(MobileScreen context) {
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

	@Override
	@InteractiveMethod
	public void refresh() {
		refresh.click();		
	}

	@Override
	@InteractiveMethod
	public void share() {
		share.click();		
	}

	@Override
	@InteractiveMethod
	public void play() {
		tap(1, play, 2);		
	}

	@Override
	@InteractiveMethod
	public void edit() {
		TouchAction touchAction = new TouchAction(
				(MobileDriver) getWrappedDriver());
		touchAction.tap(edit);
		touchActionsPerformer.performTouchAction(touchAction);
	}
	
	@InteractiveMethod
	public void zoomArticle(){
		zoom(currentArticle);
	}

}
