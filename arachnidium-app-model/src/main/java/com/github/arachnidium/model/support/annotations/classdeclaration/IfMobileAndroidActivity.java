package com.github.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is used when mobile screen needs to be identified
 * by Android activity. Is ignored by iOS.
 * 
 * This annotation is for
 * UI specifications when it is 
 * possible to identify UI by Android activity or
 * the set of activities
 * 
 * If there is only one activity 
 * <p>
 * <code>
 * <p>@IfMobileAndroidActivity(regExp = "WebViewActivity")
 * <p>public class ...
 * </code> 
 * 
 * If UI is the same but it is possible more than one activity
 * <p>
 * <code>
 * <p>@IfMobileAndroidActivity(regExp = "WebViewActivity") 
 * <p>@IfMobileAndroidActivity(regExp = "SomeOneActivity")
 * <p>public class ...
 * </code> 
 * Each activity can be specified by regular expression.
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(IfMobileAndroidActivities.class)
public @interface IfMobileAndroidActivity {
	/**
	 * @return The activity specification
	 */
	String regExp();
}
