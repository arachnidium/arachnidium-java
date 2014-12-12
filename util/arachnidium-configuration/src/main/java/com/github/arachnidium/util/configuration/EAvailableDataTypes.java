package com.github.arachnidium.util.configuration;

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
	STRING(String.class){
		@Override
		<T> T getValue(String ignored, String strValue) {
			return getValue(strValue);
		}
	}, 
	/**
	 * java.lang.Boolean
	 */
	BOOL(Boolean.class){
		@Override
		<T> T getValue(String ignored, String strValue) {
			return getValue(strValue);
		}
	}, 
	/**
	 * java.lang.Long
	 */	
	LONG(Long.class){
		@Override
		<T> T getValue(String ignored, String strValue) {
			return getValue(strValue);
		}
	}, 
	/**
	 * java.lang.Float
	 */	
	FLOAT(
			Float.class){
		@Override
		<T> T getValue(String ignored, String strValue) {
			return getValue(strValue);
		}
	}, 
	/**
	* java.lang.Integer
	*/			
	INT(Integer.class){
		@Override
		<T> T getValue(String ignored, String strValue) {
			return getValue(strValue);
		}
		
	},
	/**
	 * Some {@link Enum}
	 */
	ENUM(Enum.class){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		<T> T getValue(String classFullName, String strValue) {
			Class<Enum> enumCls = null;
			try {
				enumCls = (Class<Enum>) Class.forName(classFullName);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			Enum<?> requiredEnumValue = Enum.valueOf((Class<Enum>) enumCls, strValue);
			return (T) requiredEnumValue;
		}
		
	};

	private final Class<?> usingClass;

	private EAvailableDataTypes(Class<?> usingClass) {
		this.usingClass = usingClass;
	}

	@SuppressWarnings("unchecked")
	<T> T getValue(String strValue) {
		Class<?>[] params = new Class<?>[] { String.class };
		Object[] values = new Object[] { strValue };

		try {
			Constructor<?> suitableConstructor = usingClass
					.getConstructor(params);
			return (T) suitableConstructor.newInstance(values);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	abstract <T> T getValue(String classFullName, String strValue);
}
