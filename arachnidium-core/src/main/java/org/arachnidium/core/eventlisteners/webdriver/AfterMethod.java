package org.arachnidium.core.eventlisteners.webdriver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * It is not public annotation which is used
 * by {@link IWebDriverEventListener}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AfterMethod {
	Class<? extends Object>[] clazzes() default {};
	String[] methods() default {};
	Class<? extends Object>[] arguments() default {};
}
