package com.github.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.ContextAware;

/**
 * This annotation is for
 * UI specifications when it is 
 * possible the interaction with
 * more than one mobile context at the same time.
 * (e.g. NATIVE_APP, WEBVIEW_0, WEBVIEW_1 and so on)
 * 
 * If some screen UI is always at the first context (or second
 * and so on) the class-specification could be marked 
 * by the annotation.
 * <p>
 * Examples:
 * <p>
 * <code>
 * <p>
 * <p>@IfMobileDefaultContextIndex(index = 0) //is always the first context
 * <p>public class ...
 * </code>
 * <p>
 * or
 * <p>
 * <code>
 * <p>
 * <p>@IfMobileDefaultContextIndex(index = 1) //is always the second context
 * <p>public class ...
 * </code>
 * 
 * @see ContextAware
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IfMobileDefaultContextIndex {
	/**
	 * @return The specified context index
	 * @see ContextAware
	 */
	int index();
}
