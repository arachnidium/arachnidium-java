package com.github.arachnidium.model.support.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is used when mobile screen needs to be identified
 * by Android activity. Is ignored by iOS
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExpectedAndroidActivities {
	ExpectedAndroidActivity[] value();
}
