package arachnidium.thucydides.pageobjects.mobile.android.bbc;

import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.arachnidium.model.mobile.android.AndroidScreen;
import org.arachnidium.core.MobileScreen;


public class BBCMain extends AndroidScreen{
	
	@AndroidFindBy(id = "bbc.mobile.news.ww:id/articleWrapper")
	private List<WebElement> articles;
	@AndroidFindBy(id = "bbc.mobile.news.ww:id/articleWebView")
	private WebElement currentArticle;			
	@AndroidFindBy(id = "bbc.mobile.news.ww:id/optMenuShareAction")
	private WebElement share;
	@AndroidFindBy(id = "bbc.mobile.news.ww:id/optMenuWatchListenAction")
	private WebElement play;
	@AndroidFindBy(id = "bbc.mobile.news.ww:id/optMenuEditAction")
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
		tap.tap(1, play, 2);		
	}

	@InteractiveMethod
	public void edit() {
		TouchAction touchAction = touchActions.getTouchAction();
		touchAction.tap(edit);
		touchActionsPerformer.performTouchAction(touchAction);
	}

	@InteractiveMethod
	public void sendKeyEvent(int key) {
		keyEventSender.sendKeyEvent(key);		
	}

}
