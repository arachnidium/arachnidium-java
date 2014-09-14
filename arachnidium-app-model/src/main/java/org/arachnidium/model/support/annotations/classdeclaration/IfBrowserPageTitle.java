package org.arachnidium.model.support.annotations.classdeclaration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is for
 * UI specifications when it is 
 * possible the interaction with
 * more than one browser window/loaded pages at the same time.
 * Some times it needs to identify page by title.
 * <p>
 * There is an assumption that it is an additional
 * condition.
 * <p>
 * If the desired UI is always on the page with
 * the same title each time the class-specification could be marked 
 * by the annotation. Title value can be specified by regular expression.
 * 
 * <p>
 * <code>
 * <p>@IfBrowserPageTitle(regExp = "^*[?[Hello]\\?[world]]") //we need the page with 
 * <p>//title that contains "Hello world"
 * <p>public class ...
 * </code> 
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IfBrowserPageTitle {
	/**
	 * The string specification of the default page title
	 * @return
	 */
	String regExp();
}
