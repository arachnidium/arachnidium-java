package com.github.arachnidium.model.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang3.ArrayUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;

import com.github.arachnidium.model.abstractions.ModelObject;
import com.github.arachnidium.model.interfaces.IDecomposable;
import com.github.arachnidium.model.support.HowToGetByFrames;
import com.github.arachnidium.model.support.annotations.classdeclaration.Frame;
import com.github.arachnidium.model.support.annotations.classdeclaration.rootelements.IRootElementReader;

class ModelObjectFieldAnswer extends CommonInterceptor implements Answer<Object> {

	private final Field instantiatedField;
	private final ModelObject<?> parent;
	
	public ModelObjectFieldAnswer(Field instantiatedField, ModelObject<?> parent) {
		this.instantiatedField = instantiatedField;
		this.parent = parent;
	}
	
	/**
	 * This method returns the real object which should execute the invoked 
	 * method. It extracts parameter of the real object instantiation.
	 * It is supposed that field is marked by {@link Frame} and {@link RootElement}.
	 * That is used by instantiation. Instantiation invokes one of {@link IDecomposable}
	 * methods. 
	 * 
	 * @return An instance of a Class declared by field
	 */
	private Object instantiate(){
		HowToGetByFrames howTo = AnnotationReadingUtil
				.getHowToGetByFramesStrategy(instantiatedField);
		
		IRootElementReader rootElementReader = AnnotationReadingUtil
				.getRootElementReader(parent.getWebDriverEncapsulation());
		By rootBy = rootElementReader.readClassAndGetBy(instantiatedField,
				parent.getWebDriverEncapsulation().getWrappedDriver());
		
		Class<?> declaredClass = instantiatedField.getType();
		Object[] result = new Object[]{declaredClass};
		if (howTo!=null)
			ArrayUtils.add(result, howTo);
		if (rootBy != null){
			ArrayUtils.add(result, rootBy);
		}
		
		Method getPart = MethodReadingUtil.getSuitableMethod(parent.getClass(), GET_PART, result);
		try {
			return getPart.invoke(parent, result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Object answer(InvocationOnMock invocation) throws Throwable {
		Method realMethod = invocation.getMethod();
		Object[] arguments = invocation.getArguments();
		Object realObject = instantiate();
		MethodProxy proxy = MethodReadingUtil.getMethodProxy(realObject.getClass(), realMethod);
		return super.intercept(realObject, realMethod, arguments, proxy);
	}

}
