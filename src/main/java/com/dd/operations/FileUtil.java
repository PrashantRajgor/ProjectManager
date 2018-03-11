package com.dd.operations;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtil.
 */
public class FileUtil {

	/**
	 * Creates the directory if not present.
	 *
	 * @param projectPath the project path
	 * @return true, if successful
	 */
	public static boolean createDirectoryIfNotPresent(String projectPath) {
		File file = new File(projectPath.toString());
		if (!file.exists()) {
			file.mkdirs();
			if (!file.isDirectory()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Convert yaml to json.
	 *
	 * @param yaml the yaml
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String convertYamlToJson(String yaml) throws ParseException {

		ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
		Object obj;
		try {
			obj = yamlReader.readValue(new FileReader(yaml), Object.class);
			ObjectMapper jsonWriter = new ObjectMapper();
			return jsonWriter.writeValueAsString(obj);
		} catch (IOException e) {
			throw new ParseException("Unable to parse yml");
		}
	}

	/**
	 * Find folders in directory.
	 *
	 * @param directoryPath the directory path
	 * @return the list
	 */
	public static List<String> findFoldersInDirectory(String directoryPath) {
		File directory = new File(directoryPath);

		FileFilter directoryFileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};

		File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
		List<String> foldersInDirectory = new ArrayList<String>(directoryListAsFile.length);
		for (File directoryAsFile : directoryListAsFile) {
			foldersInDirectory.add(directoryAsFile.getName());
		}

		return foldersInDirectory;
	}

}
