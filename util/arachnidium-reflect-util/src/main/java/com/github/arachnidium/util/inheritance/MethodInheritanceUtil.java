package com.github.arachnidium.util.inheritance;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils.Interfaces;
import org.apache.commons.lang3.reflect.MethodUtils;

public final class MethodInheritanceUtil {

	private MethodInheritanceUtil() {
		super();
	}

	public static List<Class<?>> getDeclaringClasseses(Method m,
			Interfaces interfacesBehavior) {
		Set<Method> declaredMethods = MethodUtils.getOverrideHierarchy(m,
				interfacesBehavior);
		final List<Class<?>> classes = new LinkedList<Class<?>>();
		declaredMethods.forEach(method -> {
			classes.add(method.getDeclaringClass());
			}
		);
		return classes;
	}
	
	public static boolean isOverriddenFrom(Method m, Class<?> declaringClass){
		return getDeclaringClasseses(m, Interfaces.INCLUDE).contains(declaringClass);
	}
	
	public static boolean isOverriddenFromAny(Method m, List<Class<?>> declaringClasses){
		for (Class<?> declaringClass: declaringClasses){
			if (declaringClasses.contains(declaringClass))
				return true;
		}
		return false;
	}

}
