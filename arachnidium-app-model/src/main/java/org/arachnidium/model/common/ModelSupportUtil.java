package org.arachnidium.model.common;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.arachnidium.core.Handle;
import org.arachnidium.core.Manager;
import org.arachnidium.core.WebDriverEncapsulation;

abstract class ModelSupportUtil {

	private ModelSupportUtil() {
		super();
	}

	static Handle getTheFirstHandle(
			Class<? extends Manager<?>> handleManagerClass,
			Class<?>[] wdEncapsulationParams, Object[] wdEncapsulationParamVals) {
		try {
			Constructor<?> wdeC = WebDriverEncapsulation.class
					.getConstructor(wdEncapsulationParams);
			WebDriverEncapsulation wdeInstance = (WebDriverEncapsulation) wdeC
					.newInstance(wdEncapsulationParamVals);
			return getTheFirstHandle(handleManagerClass, wdeInstance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Handle getTheFirstHandle(
			Class<? extends Manager<?>> handleManagerClass,
			WebDriverEncapsulation wdeInstance) {
		try {
			Constructor<?> c = handleManagerClass
					.getConstructor(new Class<?>[] { WebDriverEncapsulation.class });
			Manager<?> m = (Manager<?>) c
					.newInstance(new Object[] { wdeInstance });
	
			return m.getHandle(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Class<?>[] getParameterClasses(Object[] paramerers,
			Class<?> requiredClass) {
	
		Class<?>[] givenParameters = new Class<?>[paramerers.length];
		for (int i = 0; i < paramerers.length; i++) {
			givenParameters[i] = paramerers[i].getClass();
		}
	
		Constructor<?>[] declaredConstructors = requiredClass
				.getDeclaredConstructors();
		for (Constructor<?> constructor : declaredConstructors) {
			Class<?>[] declaredParameters = constructor.getParameterTypes();
	
			if (declaredParameters.length != givenParameters.length) {
				continue;
			}
	
			boolean isMatch = true;
			for (int i = 0; i < declaredParameters.length; i++) {
				if (!declaredParameters[i].isAssignableFrom(givenParameters[i])) {
					isMatch = false;
					break;
				}
			}
	
			if (isMatch) {
				return declaredParameters;
			}
		}
		throw new RuntimeException(new NoSuchMethodException(
				"There is no suitable constructor! Given parameters: "
						+ Arrays.asList(givenParameters).toString() + ". "
						+ "Class is " + requiredClass.getName()));
	}

}
