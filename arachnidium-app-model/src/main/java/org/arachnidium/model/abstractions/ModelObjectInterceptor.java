package org.arachnidium.model.abstractions;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.annotations.classdeclaration.ClassDeclarationReader;
import org.arachnidium.model.support.annotations.classdeclaration.Frame;
import org.arachnidium.util.proxy.DefaultInterceptor;

/**
 *
 * A default interceptor for any {@link ModelObject}
 * 
 * It invokes methods. If some exception is thrown
 * it attempts to handle it implicitly 
 * 
 * @see MethodInterceptor
 * 
 * @see DefaultInterceptor
 */
public abstract class ModelObjectInterceptor	extends DefaultInterceptor {

	protected static final String GET_PART = "getPart";
	
	/**
	 * Creates a strategy of {@link HowToGetByFrames} class if
	 * the given class is annotated by {@link Frame} annotation
	 * 
	 * @param annotated It is class that is supposed to be annotated by {@link Frame} annotation
	 * 
	 * @return The specified {@link HowToGetByFrames}  strategy if the 
	 * given class is annotated by {@link Frame} annotation
	 * 
	 * <code>null</code> if the 
	 * given class isn't by {@link Frame} annotation
	 */
	protected HowToGetByFrames ifClassIsAnnotatedByFrames(
			Class<?> annotated) {
		List<Object> framePath = ClassDeclarationReader
				.getFramePath(ClassDeclarationReader.getAnnotations(
						Frame.class, annotated));
		if (framePath.size() == 0) {
			return null;
		}
	
		HowToGetByFrames howTo = new HowToGetByFrames();
		framePath.forEach((chainElement) -> {
			howTo.addNextFrame(chainElement);
		});
		return howTo;
	}	
	
	/**
	 * @see org.arachnidium.util.proxy.DefaultInterceptor#intercept(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[],
	 *      net.sf.cglib.proxy.MethodProxy)
	 *      
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[],
	 *      net.sf.cglib.proxy.MethodProxy)     
	 */
	@Override
	public Object intercept(Object modelObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		try {
			return super.intercept(modelObj, method, args, proxy);
		} catch (Exception e) {
			return ((ModelObject<?>) modelObj).exceptionHandler
					.handleException(modelObj, method, proxy, args, e);
		}
	}

}
