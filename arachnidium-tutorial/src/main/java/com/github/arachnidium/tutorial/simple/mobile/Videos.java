package com.github.arachnidium.tutorial.simple.mobile;

import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.core.HowToGetByFrames;

public class Videos<S extends Handle> extends HasSearchField<S> {

	@AndroidFindBy(id = "com.vkontakte.android:id/album_thumb")
	private List<WebElement> videos;
	
	protected Videos(FunctionalPart<S> parent, HowToGetByFrames path, By by) {
		super(parent, path, by);
	}
	
	@InteractiveMethod
	public int getVideosCount(){
		return videos.size();
	}
	
	@InteractiveMethod
	public void playVideo(int index){
		videos.get(index).click();
	}

}
