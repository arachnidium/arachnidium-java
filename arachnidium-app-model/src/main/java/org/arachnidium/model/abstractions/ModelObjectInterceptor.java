package org.arachnidium.model.abstractions;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.support.HowToGetByFrames;
import org.arachnidium.model.support.annotations.classdeclaration.ClassDeclarationReader;
import org.arachnidium.model.support.annotations.classdeclaration.Frame;
import org.arachnidium.util.proxy.DefaultInterceptor;

/**
 *
 * A default interceptor for any {@link ModelObject}
 *
 */
public abstract class ModelObjectInterceptor	extends DefaultInterceptor {

	protected HowToGetByFrames ifClassIsAnnotatedByFrames(
			Class<? extends IDecomposable> annotatedDecomposable) {
		List<Object> framePath = ClassDeclarationReader
				.getFramePath(ClassDeclarationReader.getAnnotations(
						Frame.class, annotatedDecomposable));
		if (framePath.size() == 0) {
			return null;
		}
	
		HowToGetByFrames howTo = new HowToGetByFrames();
		framePath.forEach((chainElement) -> {
			howTo.addNextFrame(chainElement);
		});
		return howTo;
	}	
	
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
