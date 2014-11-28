package com.github.arachnidium.model.common;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.mockito.Mockito;
import com.github.arachnidium.model.abstractions.ModelObject;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootAndroidElement;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.RootIOSElement;
import com.github.arachnidium.util.proxy.EnhancedProxyFactory;

abstract class DecompositionUtil {

	/**
	 * Creation of any decomposable part of application
	 */
	static <T extends IDecomposable> T get(Class<T> partClass,
			Object[] paramValues) {
		try{
			T decomposable = EnhancedProxyFactory.getProxy(partClass,
					MethodReadingUtil.getParameterClasses(paramValues, partClass), paramValues,
					new InteractiveInterceptor() {
					});
			DecompositionUtil.populateFieldsWhichAreDecomposable((ModelObject<?>) decomposable);
			return decomposable;
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}		
	}
	
	/**
	 * This method populates fields of classes which implements {@link ModelObject}
	 * This field should be merked by {@link Static}. It can be marked by {@link Frame},
	 * {@link RootElement}/{@link RootAndroidElement}/{@link RootIOSElement}
	 * 
	 * @param targetDecomposableObject this is the object whose fields should be populated
	 */
	static void populateFieldsWhichAreDecomposable(
			ModelObject<?> targetDecomposableObject) {
		Class<?> clazz = targetDecomposableObject.getClass();
		while (clazz != Object.class) {
			List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
			fields.forEach(field -> {
				if (!field.isAnnotationPresent(Static.class)) {
					return;
				}

				if (!ModelObject.class.isAssignableFrom(field
						.getDeclaringClass())) {
					return;
				}

				field.setAccessible(true);
				try {
					Object value = Mockito.mock(field.getType(),
							new ModelObjectFieldAnswer(field, targetDecomposableObject));
					field.set(targetDecomposableObject, value);
					//ModelObject fields of a new mock-instance are mocked too 
					populateFieldsWhichAreDecomposable((ModelObject<?>) value);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
			clazz = clazz.getSuperclass();
		}
	}
}
