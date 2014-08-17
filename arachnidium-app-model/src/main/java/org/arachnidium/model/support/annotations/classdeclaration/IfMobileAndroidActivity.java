package org.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is used when mobile screen needs to be identified
 * by Android activity. Is ignored by iOS
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(IfMobileAndroidActivities.class)
public @interface IfMobileAndroidActivity {
	String regExp();
}
