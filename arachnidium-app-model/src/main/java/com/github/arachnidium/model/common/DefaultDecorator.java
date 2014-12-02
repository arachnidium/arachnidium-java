package com.github.arachnidium.model.common;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SearchContext;

import com.github.arachnidium.model.interfaces.IDecomposable;

class DefaultDecorator extends AppiumFieldDecorator {
	private final IDecomposable decomposable;

	public DefaultDecorator(SearchContext context, IDecomposable decomposable) {
		super(context);
		this.decomposable = decomposable;
	}
	
	public DefaultDecorator(SearchContext context, IDecomposable decomposable, long time, TimeUnit timeUnit) {
		super(context, time, timeUnit);
		this.decomposable = decomposable;
	}	
	
	public Object decorate(ClassLoader ignored, Field field) {
		Object result = super.decorate(ignored, field);
		if (result != null)
			return result;
		
		field.setAccessible(true);
		try {
			result = field.get(decomposable);
			int modifiers = field.getModifiers();
			if (result!=null && !Modifier.isStatic(modifiers) 
							&& !Modifier.isFinal(modifiers))
				return result;
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	

}
