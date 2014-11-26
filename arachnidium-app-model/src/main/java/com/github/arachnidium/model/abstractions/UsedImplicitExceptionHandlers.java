package com.github.arachnidium.model.abstractions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation should mark subclasses of 
 * {@link ModelObject}. Here is the list of {@link ModelObjectExceptionHandler} subclasses
 * which are used implicitly.
 *
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UsedImplicitExceptionHandlers {
	Class<? extends ModelObjectExceptionHandler>[] areUsed();
}
