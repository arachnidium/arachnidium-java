package com.github.arachnidium.core.interfaces.functions;

import org.testng.internal.Nullable;

import com.google.common.base.Predicate;

public interface IPredicate<T> extends Predicate<T>, java.util.function.Predicate<T>{
	
	@Override
	default boolean apply(@Nullable T input){
		return test(input);
	}

}
