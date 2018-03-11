package com.dd.operations.impl;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.dd.operations.CreateStructure;
import com.dd.operations.DeleteStructure;
import com.dd.operations.ProjectList;
import com.dd.operations.intf.IStructureManager;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;

// TODO: Auto-generated Javadoc
/**
 * The Class StructureManagerImpl.
 */
public class StructureManagerImpl implements IStructureManager {

	/** The create structure. */
	CreateStructure createStructure;
	
	/** The delete structure. */
	DeleteStructure deleteStructure;
	
	/** The project list. */
	ProjectList projectList;

	/* (non-Javadoc)
	 * @see com.dd.operations.intf.IStructureManager#createStructure(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void createStructure(String projectType, String projectPath, List<String> templatePath) throws IOException, JSONException, ParseException {
		createStructure = new CreateStructure();
		createStructure.createStructure(projectType, projectPath, templatePath);
	}

	/* (non-Javadoc)
	 * @see com.dd.operations.intf.IStructureManager#deleteStructure(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteStructure(String projectType, String projectPath) throws IOException {
		deleteStructure = new DeleteStructure();
		return deleteStructure.deleteStructure(projectType, projectPath);
	}

	/* (non-Javadoc)
	 * @see com.dd.operations.intf.IStructureManager#showAllProjects(java.lang.String, java.lang.String)
	 */
	@Override
	public void showAllProjects(String projectType, String projectPath) {
		projectList = new ProjectList();
		projectList.showAllProjects(projectType, projectPath);
	}

}
