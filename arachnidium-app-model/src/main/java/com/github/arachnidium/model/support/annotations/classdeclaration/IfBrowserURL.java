package com.github.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is for
 * UI specifications when it is 
 * possible the interaction with
 * more than one browser window/loaded pages at the same time.
 * 
 * It is possible that desired UI can be identified
 * by the set of possible URLs. If there is only one URL 
 * <p>
 * <code>
 * <p>@IfBrowserURL(@IfBrowserURL(regExp = "https://accounts.google.com/ServiceLogin")) 
 * <p>public class ...
 * </code> 
 * 
 * If UI is the same but it is possible more than one URL
 * <p>
 * <code>
 * <p>@IfBrowserURL(regExp = "docs.google.com/document/") 
 * <p>@IfBrowserURL(regExp = "docs.google.com/spreadsheets/") 
 * <p>public class ...
 * </code> 
 * Each URL can be specified by regular expression.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(IfBrowserURLs.class)
public @interface IfBrowserURL {
	/**
	 * @return The value of specified URL.
	 */
	String regExp();
}
