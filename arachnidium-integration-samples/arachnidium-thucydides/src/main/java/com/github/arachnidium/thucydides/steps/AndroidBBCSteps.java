package com.github.arachnidium.thucydides.steps;

import net.thucydides.core.annotations.Step;

import com.github.arachnidium.util.configuration.Configuration;
import org.junit.Assert;

import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.mobile.MobileFactory;
import com.github.arachnidium.thucydides.pageobjects.bbc.BBCMain;
import com.github.arachnidium.thucydides.pageobjects.bbc.TopicList;

public class AndroidBBCSteps extends StepsWithApplication {

	private static final long serialVersionUID = 1L;

	@Override
	@Step
	public void insatantiateApp(Configuration configuration) {
		setInstantiatedApplication(new MobileFactory(configuration).launch(Application.class));
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
