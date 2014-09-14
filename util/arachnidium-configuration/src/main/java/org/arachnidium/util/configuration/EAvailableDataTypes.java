package org.arachnidium.util.configuration;

import java.lang.reflect.Constructor;

/**
 * It has got types which are used by {@link Configuration} and
 * returns objects of these types. These classes should have constructor
 * like this: new Class(String value);
 */
enum EAvailableDataTypes {
	/**
	 * java.lang.String
	 */
	STRING(String.class), 
	/**
	 * java.lang.Boolean
	 */
	BOOL(Boolean.class), 
	/**
	 * java.lang.Long
	 */	
	LONG(Long.class), 
	/**
	 * java.lang.Float
	 */	
	FLOAT(
			Float.class), 
	/**
	* java.lang.Integer
	*/			
	INT(Integer.class);

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
