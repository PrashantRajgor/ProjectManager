/*
 * 
 */
package com.dd.operations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

/**
 * The Class CreateStructure.
 */
public class CreateStructure {

	/**
	 * Creates the structure.
	 *
	 * @param projectType the project type
	 * @param projectPath the project path
	 * @param templatePath the template path
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JSONException the JSON exception
	 * @throws ParseException the parse exception
	 */
	public void createStructure(String projectType, String projectPath, List<String> templatePath) throws IOException, JSONException, ParseException {

		Map<String, Boolean> repeated = new HashMap<String, Boolean>();

		if(!FileUtil.createDirectoryIfNotPresent(projectPath)){
			System.out.println("Exception while creating project structure for "+projectType);
			return;
		
		}
		//parse each ymls
		for (String template : templatePath) {

			JSONArray jArray = new JSONArray(FileUtil.convertYamlToJson(template));

			for (int i = 0; i < jArray.length(); i++) {
				List<String> addPath = new ArrayList<String>();
				addPath.add(projectPath);
				JSONObject jObject = jArray.getJSONObject(i);
				String permission = jObject.getString("permission");
				JSONObject value = jObject.getJSONObject("value");
				JSONArray data = value.names();
				// if project type not match with yml file type name, go to next
				if(!data.get(0).toString().equalsIgnoreCase(projectType) && projectType!=null){
					continue;
				}
				addPath.add(data.get(0).toString());
				//if second structure with same type,override existing
				if (repeated.containsKey(data.get(0).toString())) {
					deleteExistingDirectory(projectPath);
					
				}
				createStructureDirectory(addPath);
				JSONArray directoryData = value.getJSONArray(data.get(0).toString());

				for (int j = 0; j < directoryData.length(); j++) {
					if (directoryData.getJSONObject(j).get("value") != null) {
						String stringData = directoryData.getJSONObject(j).get("value").toString();
						if (stringData.contains(".") && !stringData.contains("{")) {
							addPath.add(stringData);
							createFile(addPath);
							//remove path to backtrack
							addPath.remove(stringData);
						} else if (stringData.contains("{")) {// if it has more directory create that structure
							createInternalStructure(directoryData.getJSONObject(j).getJSONObject("value"), addPath);
						} else {
							addPath.add(directoryData.getJSONObject(j).get("value").toString());
							createStructureDirectory(addPath);
							addPath.remove(directoryData.getJSONObject(j).get("value"));
						}
					} else {
						createInternalStructure(directoryData.getJSONObject(j), addPath);
					}
				}

			}
		}

	}

	/**
	 * Delete existing directory.
	 *
	 * @param projectPath the project path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void deleteExistingDirectory(String projectPath) throws IOException {

		FileUtils.deleteDirectory(new File(projectPath));
	}

	/**
	 * Creates the file.
	 *
	 * @param addPath the add path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void createFile(List<String> addPath) throws IOException {
		StringBuilder fullPath = new StringBuilder();
		for (String path : addPath) {
			fullPath.append(path + "\\");
		}
		
			File file = new File(fullPath.toString());
			file.createNewFile();
		
	}

	/**
	 * Creates the structure directory.
	 *
	 * @param addPath the add path
	 */
	private void createStructureDirectory(List<String> addPath) {
		StringBuilder fullPath = new StringBuilder();
		for (String path : addPath) {
			fullPath.append(path + "\\");
		}
		File theDir = new File(fullPath.toString());
		theDir.mkdir();
	}

	/**
	 * Creates the internal structure.
	 *
	 * @param value the value
	 * @param addPath the add path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void createInternalStructure(JSONObject value, List<String> addPath) throws IOException {
		JSONArray data = value.names();
		addPath.add(data.get(0).toString());
		createStructureDirectory(addPath);
		JSONArray directoryData = value.getJSONArray(data.get(0).toString());

		for (int j = 0; j < directoryData.length(); j++) {
			if (directoryData.getJSONObject(j).get("value") != null) {
				String stringData = directoryData.getJSONObject(j).get("value").toString();
				if (stringData.contains(".") && !stringData.contains("{")) {
					addPath.add(stringData);
					createFile(addPath);
					addPath.remove(stringData);
				} else if (stringData.contains("{")) {
					createInternalStructure(directoryData.getJSONObject(j).getJSONObject("value"), addPath);
				} else {
					addPath.add(directoryData.getJSONObject(j).get("value").toString());
					createStructureDirectory(addPath);
					addPath.remove(directoryData.getJSONObject(j).get("value"));
				}
			}
		}
		addPath.remove(data.get(0).toString());

	}


}
