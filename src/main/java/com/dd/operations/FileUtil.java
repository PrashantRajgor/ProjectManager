package com.dd.operations;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class FileUtil {

	public static boolean createDirectoryIfNotPresent(String projectPath){
		File file = new File(projectPath.toString());
		if (! file.exists()){
			file.mkdirs();
			if(!file.isDirectory()){
				return false;
			}
	    }
		return true;
	}
	
	public static String convertYamlToJson(String yaml) {

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

}
