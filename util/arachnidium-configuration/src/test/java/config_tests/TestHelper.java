package config_tests;


import org.aspectj.lang.reflect.AdviceKind;

import com.github.arachnidium.util.configuration.AbstractConfigurationAccessHelper;
import com.github.arachnidium.util.configuration.Configuration;
import com.github.arachnidium.util.configuration.Group;

@Group(settingGroup = "test")
public class TestHelper extends AbstractConfigurationAccessHelper {

	public TestHelper(Configuration configuration, String desiredSettingGroup) {
		super(configuration, desiredSettingGroup);
	}
	
	@Setting(setting = "aspect")
	public AdviceKind getAspectKind(){
		return getSetting();
	}
	
	@Setting(setting = "int1")
	public Integer getInt1(){
		return getSetting();
	}
	
	@Setting(setting = "int2")
	public Integer getInt2(){
		return getSetting();
	}	
}
