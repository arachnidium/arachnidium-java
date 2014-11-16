package com.github.arachnidium.util.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Subclasses of this should make access to
 *         {@link Configuration} data easier
 */
public abstract class AbstractConfigurationAccessHelper{

	static final String GET_SETTING_VALUE_METHOD = "getSetting";
	/**
	 * This annotation defines the desired
	 * setting that should be received from {@link Configuration}
	 * by the defined setting group. 
	 */
	@Target(value = ElementType.METHOD)
	@Retention(value = RetentionPolicy.RUNTIME)
	protected @interface Setting {
		String setting();
	}

	private final Configuration configuration;
	private final String DESIRED_GROUP;

	/**
	 * This constructor should present in subclass
	 * 
	 * @param configuration to get data from
	 * @see Configuration
	 */
	protected AbstractConfigurationAccessHelper(Configuration configuration, 
			String desiredSettingGroup) {
		super();
		this.configuration = configuration;
		this.DESIRED_GROUP = desiredSettingGroup;
	}

	/**
	 * This method returns a value of the desired setting
	 * 
	 * @param settingName It is desired setting name
	 * @return a value of the desired setting
	 */
	protected final <T extends Object> T getSettingValue(String settingName) {
		return configuration.getSettingValue(DESIRED_GROUP, settingName);
	}
	
	/**
	 * This method is used by CGLIB tools
	 * and returns any value when a method which
	 * invokes it is annotated by {@link Setting}.
	 * It returns <code>null</code> otherwise.
	 * 
	 * @return Value of required setting when the root method is annotated by {@link Setting}
	 * <code>null</code> is returned otherwise	 
	 * */
	protected <T extends Object> T getSetting(){
		return null;
	}
}
