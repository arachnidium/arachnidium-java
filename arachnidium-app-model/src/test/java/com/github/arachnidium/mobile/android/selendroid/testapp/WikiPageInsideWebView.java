package com.github.arachnidium.mobile.android.selendroid.testapp;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.mobile.WebViewContent;
import com.github.arachnidium.model.support.annotations.DefaultPageIndex;
import com.github.arachnidium.model.support.annotations.ExpectedPageTitle;
import com.github.arachnidium.model.support.annotations.ExpectedURL;

@ExpectedURL(regExp = "https://ru.wikipedia.org/wiki")
@ExpectedURL(regExp = "wikipedia.org")
@ExpectedPageTitle(regExp = "^*[?[Hello]\\?[world]]")
@DefaultPageIndex(index = 0)

public class WikiPageInsideWebView extends WebViewContent {

	public WikiPageInsideWebView(MobileScreen context) {
		super(context);
	}

}
