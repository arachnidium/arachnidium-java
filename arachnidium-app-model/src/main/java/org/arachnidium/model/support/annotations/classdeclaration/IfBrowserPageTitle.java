package org.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is used when browser page needs to be identified
 * by title
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IfBrowserPageTitle {
	String regExp();
}
