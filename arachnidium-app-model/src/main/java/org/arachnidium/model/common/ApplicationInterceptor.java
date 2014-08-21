package org.arachnidium.model.common;

import java.lang.annotation.Annotation;
import java.util.List;

import org.arachnidium.core.fluenthandle.IHowToGetHandle;
import org.arachnidium.model.abstractions.ModelObjectInterceptor;
import org.arachnidium.model.interfaces.IDecomposable;
import org.arachnidium.model.support.annotations.classdeclaration.ClassDeclarationReader;

public abstract class ApplicationInterceptor<IndexAnnotation extends Annotation, 
	HandleUniqueIdentifiers extends Annotation, 
		AdditionalStringIdentifier extends Annotation,
		HowTo extends IHowToGetHandle>
		extends
		ModelObjectInterceptor{

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

}
