package com.github.arachnidium.model.support.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is for
 * UI specifications when it is 
 * possible the interaction with
 * more than one browser window at the same time.
 * 
 * If some page is always loaded at the first window (or second
 * and so on) the class-specification could be marked 
 * by the annotation.
 * <p>
 * Examples:
 * <p>
 * <code>
 * <p>
 * <p>@DefaultPageIndex(index = 0) //is always on the first browser window
 * <p>public class ...
 * </code>
 * <p>
 * or
 * <p>
 * <code>
 * <p>
 * <p>@DefaultPageIndex(index = 1) //is always on the second browser window
 * <p>public class ...
 * </code>
 * 
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DefaultPageIndex {
	/**
	 * @return The specified default browser window index
	 */
	int index();
}
