package com.dd.operations;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

public class Description {

	/**
	 * Pretty print the directory tree and its file names.
	 * 
	 * @param folder
	 *            must be a folder.
	 * @return
	 */
	static Set<String> ymlPath = new HashSet<String>();

	public static String printDirectoryTree() throws IllegalArgumentException, JSONException, ParseException {

		int indent = 0;
		StringBuilder sb = new StringBuilder();
		Set<String> ymlPath;
		String[] templateLocations;
		templateLocations = System.getenv("PROJMAN_TEMPLATES").split(":");
		for (int j = 0; j < templateLocations.length; j++) {

			ymlPath = FileUtil.listFilesForFolder(new File(templateLocations[j]));
			// parse each ymls
			for (String template : ymlPath) {
				JSONArray jArray = new JSONArray(FileUtil.convertYamlToJson(template));
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jObject = jArray.getJSONObject(i);
					JSONObject value = jObject.getJSONObject("value");
					printDirectoryTreeYML(value, indent, sb);
				}
			}
		}
		return sb.toString();

	}

	private static void printDirectoryTreeYML(JSONObject jObject, int indent, StringBuilder sb) {

		Iterator<String> iterator = jObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			sb.append(getIndentString(indent));
			sb.append("|--");
			sb.append(key);
			sb.append("\n");

			Object value = jObject.get(key);

			if (value instanceof JSONArray) {

				JSONArray jArray = (JSONArray) value;
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jObject1 = jArray.getJSONObject(i);
					Object value1 = jObject1.get("value");

					if (value1 instanceof JSONObject)
						printDirectoryTreeYML((JSONObject) value1, indent + 1, sb);
					else {
						printFileYML((String) value1, indent + 1, sb);
					}
				}

			} else if (value instanceof JSONObject) {
				printFileYML((JSONObject) value, indent + 1, sb);
			}
		}

	}

	private static void printFileYML(JSONObject file, int indent, StringBuilder sb) {
		sb.append(getIndentString(indent));
		sb.append("|--");
		sb.append(file.names());
		sb.append("\n");
	}

	private static void printFileYML(String file, int indent, StringBuilder sb) {
		sb.append(getIndentString(indent));
		sb.append("|--");
		sb.append(file);
		sb.append("\n");
	}

	private static String getIndentString(int indent) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append("|  ");
		}
		return sb.toString();
	}

}