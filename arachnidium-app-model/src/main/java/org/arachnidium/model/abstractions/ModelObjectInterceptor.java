package org.arachnidium.model.abstractions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.arachnidium.core.fluenthandle.IHowToGetHandle;
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
public class ModelObjectInterceptor<IndexAnnotation extends Annotation, HandleUniqueIdentifiers extends Annotation, AdditionalStringIdentifier extends Annotation, HowTo extends IHowToGetHandle>
		extends DefaultInterceptor {

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

	protected HowTo getHowToGetHandleStrategy(
			Class<IndexAnnotation> indexAnnotation,
			Class<HandleUniqueIdentifiers> handleUniqueIdentifiers,
			Class<AdditionalStringIdentifier> additionalStringIdentifier,
			Class<? extends IDecomposable> annotatedDecomposable,
			Class<HowTo> howToClass) throws ReflectiveOperationException {
		IndexAnnotation[] indexAnnotations = ClassDeclarationReader
				.getAnnotations(indexAnnotation, annotatedDecomposable);
		Integer index = null;
		if (indexAnnotations.length > 0) {
			index = ClassDeclarationReader.getIndex(indexAnnotations[0]);
		}

		HandleUniqueIdentifiers[] handleUniqueIdentifiers2 = ClassDeclarationReader
				.getAnnotations(handleUniqueIdentifiers, annotatedDecomposable);
		List<String> identifiers = ClassDeclarationReader
				.getRegExpressions(handleUniqueIdentifiers2);
		if (identifiers.size() == 0) {
			identifiers = null;
		}

		String additionalStringIdentifier2 = null;
		AdditionalStringIdentifier[] additionalStringIdentifiers = ClassDeclarationReader
				.getAnnotations(additionalStringIdentifier,
						annotatedDecomposable);
		if (additionalStringIdentifiers.length > 0) {
			additionalStringIdentifier2 = ClassDeclarationReader
					.getRegExpressions(additionalStringIdentifiers).get(0);
		}

		if (index == null && identifiers == null
				&& additionalStringIdentifier2 == null) {
			return null;
		}

		try {
			return howToClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw e;
		}
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
