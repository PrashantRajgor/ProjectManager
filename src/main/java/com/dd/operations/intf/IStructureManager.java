package com.dd.operations.intf;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IStructureManager.
 */
public interface IStructureManager {

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
	void createStructure(String projectType, String projectPath, List<String> templatePath) throws IOException, JSONException, ParseException;

	/**
	 * Delete structure.
	 *
	 * @param projectType the project type
	 * @param projectPath the project path
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	boolean deleteStructure(String projectType, String projectPath) throws IOException;

	/**
	 * Show all projects.
	 *
	 * @param projectType the project type
	 * @param projectPath the project path
	 */
	void showAllProjects(String projectType, String projectPath);
}
