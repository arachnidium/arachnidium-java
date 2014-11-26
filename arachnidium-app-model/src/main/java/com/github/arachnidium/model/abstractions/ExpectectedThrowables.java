package com.github.arachnidium.model.abstractions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation should mark subclasses of
 * {@link ModelObjectExceptionHandler}. Here is the list of exceptions
 * that should be handled. The marked subclass of {@link ModelObjectExceptionHandler}
 * should have default constructor
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExpectectedThrowables {
	Class<? extends Throwable>[]  expectedThrowables();
}
