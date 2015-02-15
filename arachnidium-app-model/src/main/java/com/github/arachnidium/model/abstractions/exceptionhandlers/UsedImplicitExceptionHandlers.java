package com.github.arachnidium.model.abstractions.exceptionhandlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.arachnidium.model.abstractions.ModelObject;

/**
 * This annotation should mark subclasses of 
 * {@link ModelObject}. Here is the list of {@link ModelObjectExceptionHandler} subclasses
 * which are used implicitly.
 *
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UsedImplicitExceptionHandlers {
	Class<? extends ModelObjectExceptionHandler>[] areUsed();
}
