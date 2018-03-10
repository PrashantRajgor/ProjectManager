package com.dd.operations;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class CreateStructure {

	public void createStructure(String projectType, String projectPath, List<String> templatePath) {
		Map<String, Boolean> repeated = new HashMap<>();

		if(!FileUtil.createDirectoryIfNotPresent(projectPath)){
			System.out.println("Exception while creating project structure for "+projectType);
			return;
		
		}

		//projectPath = "D:\\test";
		//String templatePath1 = "./src/main/resources/TestExample.yml";
		for (String templatePath1 : templatePath) {

			JSONArray jArray = new JSONArray(FileUtil.convertYamlToJson(templatePath1));

			for (int i = 0; i < jArray.length(); i++) {
				List<String> addPath = new ArrayList<String>();
				addPath.add(projectPath);
				JSONObject jObject = jArray.getJSONObject(i);
				String permission = jObject.getString("permission");
				JSONObject value = jObject.getJSONObject("value");
				JSONArray data = value.names();
				// if project type not match with yml file type name go to next
				if(!data.get(0).toString().equalsIgnoreCase(projectType) && projectType!=null){
					continue;
				}
				addPath.add(data.get(0).toString());
				if (repeated.containsKey(data.get(0).toString())) {
					try {
						deleteExistingDirectory(projectPath);
					} catch (IOException e) {
						System.out
								.println("Exception while overriding project structure for " + data.get(0).toString());
						return;
					}
				}
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
							createDummyFun(directoryData.getJSONObject(j).getJSONObject("value"), addPath);
						} else {
							addPath.add(directoryData.getJSONObject(j).get("value").toString());
							createStructureDirectory(addPath);
							addPath.remove(directoryData.getJSONObject(j).get("value"));
						}
					} else {
						createDummyFun(directoryData.getJSONObject(j), addPath);
					}
				}

			}
		}

	}

	private void deleteExistingDirectory(String projectPath) throws IOException {

		FileUtils.deleteDirectory(new File(projectPath));
	}

	private void createFile(List<String> addPath) {
		StringBuilder fullPath = new StringBuilder();
		for (String path : addPath) {
			fullPath.append(path + "\\");
		}
		try {
			File file = new File(fullPath.toString());
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createStructureDirectory(List<String> addPath) {
		StringBuilder fullPath = new StringBuilder();
		for (String path : addPath) {
			fullPath.append(path + "\\");
		}
		File theDir = new File(fullPath.toString());
		theDir.mkdir();
	}

	void createDummyFun(JSONObject value, List<String> addPath) {
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
					createDummyFun(directoryData.getJSONObject(j).getJSONObject("value"), addPath);
				} else {
					addPath.add(directoryData.getJSONObject(j).get("value").toString());
					createStructureDirectory(addPath);
					addPath.remove(directoryData.getJSONObject(j).get("value"));
				}
			}
		}
		addPath.remove(data.get(0).toString());

	}


/*	String convertYamlToJson(String yaml) {

		ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
		Object obj;
		try {
			obj = yamlReader.readValue(new FileReader(yaml), Object.class);
			ObjectMapper jsonWriter = new ObjectMapper();
			return jsonWriter.writeValueAsString(obj);
		} catch (IOException e) {
		}
		return "";
	}
*/
}
