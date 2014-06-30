package org.arachnidium.util.configuration;

import java.lang.reflect.Constructor;

/**
 * @author s.tihomirov It has got types that are used by Configuration.java and
 *         returns objects of this types This classes should have constructors
 *         like this: new Class(String value);
 */
enum EAvailableDataTypes {
	STRING(String.class), BOOL(Boolean.class), LONG(Long.class), FLOAT(
			Float.class), INT(Integer.class);

	private final Class<?> usingClass;

	private EAvailableDataTypes(Class<?> usingClass) {
		this.usingClass = usingClass;
	}

	Object getValue(String strValue) {
		Class<?>[] params = new Class<?>[] { String.class };
		Object[] values = new Object[] { strValue };

		try {
			Constructor<?> suitableConstructor = usingClass
					.getConstructor(params);
			return suitableConstructor.newInstance(values);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
