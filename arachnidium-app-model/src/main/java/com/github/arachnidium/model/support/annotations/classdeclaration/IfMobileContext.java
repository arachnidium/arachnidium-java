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
 * Some times it needs to identify context by name.
 * <p>
 * There is an assumption that it is an additional
 * condition.
 * <p>
 * If the desired UI is always on the context with
 * the same name each time the class-specification could be marked 
 * by the annotation. Context name can be specified by regular expression.
 * 
 * <p>
 * <code>
 * <p>@IfMobileContext(regExp = "WEBVIEW_")
 * <p>public class ...
 * </code> 
 * 
 * @see ContextAware
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IfMobileContext {
	/**
	 * @return The specified context name.
	 * @see ContextAware 
	 */
	String regExp();
}
