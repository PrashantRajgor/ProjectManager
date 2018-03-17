package com.dd.operations;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectList.
 */
public class ProjectList {

	/**
	 * Show all projects.
	 *
	 * @param projectType
	 *            the project type
	 * @param projectPath
	 *            the project path
	 */
	public void showAllProjects(String projectType, String projectPath) {

		//List<String> projects = FileUtil.findFoldersInDirectory(projectPath);
		//projects.add("");
		//for (String project : projects) {
			listFilesForFolder(new File(projectPath + File.separator /*+ project*/), projectType);
		//}

		System.out.println(ymlPath);
	
	}

	Set<String> ymlPath = new HashSet<String>();

	private void listFilesForFolder(final File folder, String type) {

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				// listFilesForFolder(fileEntry);
				if (fileEntry.getName().equalsIgnoreCase(type)) {
					//listFilesForFolder(fileEntry,type);
					ymlPath.add(folder.getName());
					return;
				} else if (type == null) {
					ymlPath.add(folder.getName());
					//listFilesForFolder(fileEntry,type);
					return;
				}
					listFilesForFolder(fileEntry,type);
				//return;
			}
		}
	}

	public Set<String> displayProjectType() throws JSONException, ParseException{
		String[] templateLocations;
		templateLocations = System.getenv("PROJMAN_TEMPLATES").split(":");
		
		for (int k = 0; k < templateLocations.length; k++) {
		// parse each ymls
		Set<String>	ymlPath = FileUtil.listFilesForFolder(new File(templateLocations[k]));

		for (String template : ymlPath) {
			JSONArray jArray = new JSONArray(FileUtil.convertYamlToJson(template));
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObject = jArray.getJSONObject(i);
				//Iterator<String> iterator = jObject.keys();
				JSONObject value = jObject.getJSONObject("value");
				
				Iterator<String> iterator = value.keys();
				
				while(iterator.hasNext()){
					String key = iterator.next();
					projectsType.add(key);
				}
			}
		}
		}
		return projectsType;
			//System.out.println(projectsType);
	}
	
	Set<String> projectsType = new HashSet<String>();
}
