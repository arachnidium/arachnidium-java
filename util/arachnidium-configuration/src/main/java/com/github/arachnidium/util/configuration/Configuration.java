/*
 +Copyright 2014 Arachnidium contributors
 +Copyright 2014 Software Freedom Conservancy
 +
 +Licensed under the Apache License, Version 2.0 (the "License");
 +you may not use this file except in compliance with the License.
 +You may obtain a copy of the License at
 +
 +     http://www.apache.org/licenses/LICENSE-2.0
 +
 +Unless required by applicable law or agreed to in writing, software
 +distributed under the License is distributed on an "AS IS" BASIS,
 +WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 +See the License for the specific language governing permissions and
 +limitations under the License.
 + */

/**
 *
 */
package com.github.arachnidium.util.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Set;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Stores settings read from JSON file.
 * A JSON file has specific format
 * 
 * <p>
 * {<br/>
 * ...<br/>
 * &nbsp;&nbsp;"settingGroupName":<br/>
 * &nbsp;&nbsp;{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;"settingName1":{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"type":"Type you need", <code>// {@link String} (STRING),<br/> 
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// {@link Boolean} (BOOL), {@link Integer} (INT), {@link Long} (LONG), {@link Float} (Float)<br/></code>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"value":"some value"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br/>
 * &nbsp;&nbsp;}<br/> 
 * ...<br/> 
 * }<br/>
 * </p> 
 */
public class Configuration {
	public static Configuration get(String filePath) {
		Callback interceptor = new ConfigurationInterceptor();

		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(interceptor);
		enhancer.setSuperclass(Configuration.class);

		return (Configuration) enhancer.create(new Class[] { String.class },
				new Object[] { filePath });
	}

	private static String getPathToDefault(String startPath) {
		// attempt to find configuration in the specified directory
		File defaultConfig = new File(startPath);
		File list[] = defaultConfig
				.listFiles((FilenameFilter) (dir, name) -> name
						.endsWith(commonFileName));

		if (list.length > 0)
			return list[0].getPath();

		if (list.length == 0) {
			File inner[] = defaultConfig.listFiles();
			String result = null;
			for (File element : inner) {
				if (element.isDirectory())
					result = getPathToDefault(element.getPath());
				if (result != null)
					return result;
			}
		}
		return null;
	}

	private final static String commonFileName = "settings.json"; // default
																	// settings
	/**
	 * The default settings that are read 
	 * from <code>settings.json</code> located 
	 * in <code>.classPath</code> folder or subfolders
	 */
	public final static Configuration byDefault = get(getPathToDefault("."));

	private static final String typeTag = "type";
	private static final String requiredClassTag = "class";
	private static final String valueTag = "value";

	private final HashMap<String, HashMap<String, Object>> mappedSettings = new HashMap<String, HashMap<String, Object>>();

	private final HashMap<Class<? extends AbstractConfigurationAccessHelper>, AbstractConfigurationAccessHelper> initedHelpers = new HashMap<>();

	protected Configuration(String filePath) {
		super();
		parseSettings(String.valueOf(filePath));
	}

	/**
	 *  parsing of each one setting
	 */
	private HashMap<String, Object> getParsedGroup(JSONObject jsonObject) {
		HashMap<String, Object> result = new HashMap<>();
		@SuppressWarnings("unchecked")
		Set<String> keys = jsonObject.keySet();

		keys.forEach((key) -> {
			JSONObject value = (JSONObject) jsonObject.get(key);
			String type = (String) value.get(typeTag);

			EAvailableDataTypes requiredType = null;
			try {
				requiredType = EAvailableDataTypes.valueOf(type);
			} catch (IllegalArgumentException | NullPointerException e) {
				throw new RuntimeException(
						"Type specification that is not supported! Specification is "
								+ String.valueOf(type)
								+ ". "
								+ " STRING, BOOL, LONG, FLOAT, INT, ENUM are suppurted. Setting name is "
								+ key, e);
			}
			
			String className   =  (String) value.get(requiredClassTag);
			Object returnValue = null;
			String strValue = (String) value.get(valueTag);

			if ("".equals(strValue) || strValue == null)
				result.put(key, returnValue);
			else
				if ("".equals(className) || className == null) {
					result.put(key, requiredType.getValue(String.valueOf(strValue)));
				}else{
					result.put(key, requiredType.getValue(String.valueOf(className), 
							String.valueOf(strValue)));
				}
					
		});
		return result;
	}

	/**
	 * This method is similar as 
	 * 
	 * <p>Configuration.getSettingGroup(String)</p>
	 * 
	 * It returns some "helper" instead of HashMap. 
	 * This helper makes access to required section 
	 * easier. Class of the required helper
	 * overrides {@AbstractConfigurationAccessHelper} and 
	 * should have a constructor like this: new
	 *         Helper({@link{Configuration} configuration, {@link String} desiredSettingGroup).
	 * Also, required class should be annotated by {@link Group} annotation. {@link IllegalArgumentException} 
	 * is thrown otherwise.      
	 *               
     * @param requiredClass that extends {@link AbstractConfigurationAccessHelper} 
	 * @return instance of class specified by <code>requiredClass</code> parameter
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractConfigurationAccessHelper> T getSection(
			Class<T> requiredClass) throws IllegalArgumentException {
		T helper = (T) initedHelpers.get(requiredClass);
		if (helper != null)
			return helper;
		
		Callback interceptor = new HelperInterceptor();

		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(interceptor);
		enhancer.setSuperclass(requiredClass);

		T result = null;
		if (requiredClass.isAnnotationPresent(Group.class)){
			result = (T) enhancer.create(new Class[] {Configuration.class ,String.class},
					new Object[] { this, requiredClass.getAnnotation(Group.class).settingGroup()});
		}
		else {
			throw new IllegalArgumentException("Required class " + requiredClass.getClass().getName() + " should be annotated by "
					+ " @Group annotation!");
		}
		initedHelpers.put(requiredClass, result);
		return result;		
	}

	/**
	 *  gets mapped settings
     *
	 * <p>
	 * {<br/>
	 * ...<br/>
	 * &nbsp;&nbsp;"settingGroupName":<br/>
	 * &nbsp;&nbsp;{<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;"settingName1":{<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"type":"Type you need",<br/> 
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"value":"some value"<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;...<br/>
	 * &nbsp;&nbsp;}<br/> 
	 * ...<br/> 
	 * }<br/>
	 * </p> 
	 * 
	 * @param groupName is "settingGroupName".
	 * @return Instance of HashMap<String, Object> where key is  "settingName1" and 
	 * value is "some value" cast to "Type you need"
	 */
	public HashMap<String, Object> getSettingGroup(String groupName) {
		return mappedSettings.get(groupName);
	}
	
	/**
	 * Returns an object defined in JSON setting file
	 * <p>
	 * {<br/>
	 * ...<br/>
	 * &nbsp;&nbsp;"settingGroupName":<br/>
	 * &nbsp;&nbsp;{<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;"settingName1":{<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"type":"Type you need",<br/> 
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"value":"some value"<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;...<br/>
	 * &nbsp;&nbsp;}<br/> 
	 * ...<br/> 
	 * }<br/>
	 * </p> 
	 * 
	 * @param groupName is "settingGroupName"
	 * @param settingName is "settingName1"
	 * @return "some value" cast to "Type you need"
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getSettingValue(String groupName, String settingName) {
		HashMap<String, Object> group = getSettingGroup(groupName);
		// if there is no group with specified name
		if (group == null)
			return null;
		return (T) group.get(settingName);
	}

	/**
	 *  parsing of json configuration
	 * @param filePath - path to explicitly given JSON file 
	 */
	private void parseSettings(String filePath) {

		File settingFile = new File(filePath);
		if (!settingFile.exists())
			return;
		try {
			JSONObject jsonObject = (JSONObject) new JSONParser()
			.parse(new FileReader(settingFile));
			@SuppressWarnings("unchecked")
			Set<String> keys = jsonObject.keySet(); // there are groups
			keys.forEach((key) -> mappedSettings.put(key,
					getParsedGroup((JSONObject) jsonObject.get(key))));
		} catch (Exception e) {
			throw new RuntimeException(
					"Configuration building has failed! Please, check it. You can look at SAMPLE_SETTING.json for verifying. ",
					e);
		}
	}
	
	@Override
	public String toString(){
		String result = "";
		Set<String> keys = mappedSettings.keySet();
		for (String key: keys){
			result = result + key + ": " + mappedSettings.get(key).toString() + " ; ";
		}
		return result;
	}
}