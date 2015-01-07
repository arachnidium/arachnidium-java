package com.github.arachnidium.util.reflect.executable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class works with {@link Executable} implementations e.g. 
 * {@link Method} and {@link Constructor}
 */
public final class ExecutableUtil {
	private static final HashMap<Class<?>, Class<?>> FOR_USED_SIMPLE_TYPES = new HashMap<Class<?>, Class<?>>() {
		private static final long serialVersionUID = 1L;
		{
			put(Integer.class, int.class);
			put(Long.class, long.class);
			put(Byte.class, byte.class);
			put(Short.class, short.class);
			
			put(Boolean.class, boolean.class);
			
			put(Double.class, double.class);
			put(Float.class, float.class);
			
			put(Character.class, char.class);
			// other can be added
		}
	};
	
	private ExecutableUtil(){
		super();
	}
	
	/**
	 * This methods returns the index of the desired class if the array of {@link Executable} 
	 * {@link Parameter}'s has a class that relevant (equals or assignable from).
	 *  
	 * @param executable The given method or constructor
	 * @param requredClass The class whose index should be returned
	 * @return The index if given class is in array (equals or assignable from) of {@link Executable} parameters.
	 * -1 will be returned otherwise.
	 */
	public static int getParameterIndex(Executable executable, Class<?> requredClass){
		Parameter[] parameters = executable.getParameters();
		for (int i = 0; i < parameters.length; i ++){
			if (parameters[i].getType().isAssignableFrom(requredClass)){
				return i;
			}
		}
		return -1;
	}
	
	private static Class<?>[] getSuitableParameters(
			Executable[] executables, Object[] paramerers) {
	
		Class<?>[] givenParameters = new Class<?>[paramerers.length];
		for (int i = 0; i < paramerers.length; i++) {
			if (paramerers[i] == null){
				givenParameters[i] = null;
				continue;
			}
			givenParameters[i] = paramerers[i].getClass();
		}
	
		for (Executable executable : executables) {
			Class<?>[] declaredParameters = executable.getParameterTypes();
	
			if (declaredParameters.length != givenParameters.length) {
				continue;
			}
	
			boolean isMatch = true;
			for (int i = 0; i < declaredParameters.length; i++) {	
				
				if (givenParameters[i] == null){
					continue;
				}
				
				boolean areParametersMatch = declaredParameters[i]
						.isAssignableFrom(givenParameters[i]);
				Class<?> simpleType = FOR_USED_SIMPLE_TYPES
						.get(givenParameters[i]);
				boolean isCastedToSimple = (simpleType != null);
				if (!areParametersMatch && isCastedToSimple) {
					areParametersMatch = declaredParameters[i]
							.isAssignableFrom(simpleType);
				}
				if (!areParametersMatch) {
					isMatch = false;
					break;
				}
			}
	
			if (isMatch) {
				return declaredParameters;
			}
		}
		return null;
	}
	
	/**
	 * This method returns the relevant {@link Method} using it's and if formal parameters of 
	 * method match the given argument values. It means that class of each parameter
	 * should be assignable from the class of given value. Length of method parameterS array
	 * and length of values array should be equal.
	 * 
	 * @param clazz is the {@link Class} whose {@link Method} should be returned
	 * @param methodName is the name of the {@link Method} which is supposed to be returned
	 * @param argValues are values which are needed by method to be returned
	 * @return a {@link Method}. If there is no relevant {@link Method} then <code>null</code>
	 * will be returned
	 */
	public static Method getRelevantMethod(Class<?> clazz, String methodName, Object[] argValues){
		Method[] declaredMethods = clazz.getMethods();
		List<Method> found = new ArrayList<Method>();
		for (Method m: declaredMethods){
			if (!m.getName().equals(methodName)){
				continue;
			}
			found.add(m);
		}
		
		if (found.size() == 0){
			return null;
		}
		
		for (Method m: found){
			Method[] methods = new Method[] {m};
			Class<?>[] result = getSuitableParameters(methods, argValues);
			if (result == null){
				continue;
			}
			return m;
		}
		return null;
	}
	
	/**
	 * This method returns the relevant declared {@link Constructor} using it's and if formal parameters of 
	 * constructor match the given argument values. It means that class of each parameter
	 * should be assignable from the class of given value. Length of constructor parameters array
	 * and length of values array should be equal.
	 * 
	 * @param clazz is the {@link Class} declared {@link Constructor} should be returned
	 * @param argValues are values which are needed by {@link Constructor} to be returned
	 * @return a {@link Constructor}. If there is no relevant {@link Constructor} then <code>null</code>
	 * will be returned
	 */
	public static Constructor<?> getRelevantConstructor(Class<?> clazz, Object[] argValues){
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		
		if (constructors.length == 0){
			return null;
		}
		
		for (Constructor<?> c: constructors){
			Constructor<?>[] cs = new Constructor<?>[] {c};
			Class<?>[] result = getSuitableParameters(cs, argValues);
			if (result == null){
				continue;
			}
			return c;
		}
		return null;
	}
}
