package arachnidium.thucydides.steps;

import net.thucydides.core.annotations.Step;

import org.arachnidium.model.common.Application;
import org.arachnidium.model.mobile.MobileFactory;
import org.arachnidium.util.configuration.Configuration;
import org.junit.Assert;

import arachnidium.thucydides.pageobjects.bbc.BBCMain;
import arachnidium.thucydides.pageobjects.bbc.TopicList;

public class AndroidBBCSteps extends StepsWithApplication {

	private static final long serialVersionUID = 1L;

	@Override
	@Step
	public void insatantiateApp(Configuration configuration) {
		setInstantiatedApplication(MobileFactory.getApplication(
				Application.class, configuration));
	}
	
	@Step
	public void makeSureThatArticlesArePresent(){
		BBCMain bbcMain = app.getPart(BBCMain.class);
		Assert.assertNotSame(0, bbcMain.getArticleCount());
	}
	
	@Step
	public void selectArticle(int num){
		BBCMain bbcMain = app.getPart(BBCMain.class);
		bbcMain.selectArticle(1);
	}
	
	@Step
	public void makeShureThatArticleIsHere(){
		BBCMain bbcMain = app.getPart(BBCMain.class);
		Assert.assertEquals(true, bbcMain.isArticleHere());
	}
	
	@Step
	public void edit(){
		BBCMain bbcMain = app.getPart(BBCMain.class);
		bbcMain.edit();
	}
	
	@Step
	public void setTopicIsChecked(String topic, boolean checked){
		TopicList topicList = app.getPart(TopicList.class);
		topicList.setTopicChecked(topic, checked);
	}
	
	@Step
	public void select(){
		TopicList topicList = app.getPart(TopicList.class);
		topicList.ok();
	}
}
