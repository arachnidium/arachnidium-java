package com.github.arachnidium.core.interfaces.functions;

import org.testng.internal.Nullable;

import com.google.common.base.Function;

public interface IFunction<F, T> extends Function<F, T>,
		java.util.function.Function<F, T> {
	@Override
	T apply(@Nullable F input);
}
