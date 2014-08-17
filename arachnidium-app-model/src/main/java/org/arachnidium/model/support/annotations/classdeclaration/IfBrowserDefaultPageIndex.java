package org.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A default index of the opened browser page 
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IfBrowserDefaultPageIndex {
	int index();
}
