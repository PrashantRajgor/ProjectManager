package com.dd.operations;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProjectList.
 */
public class ProjectList {

	/**
	 * Show all projects.
	 *
	 * @param projectType the project type
	 * @param projectPath the project path
	 */
	public void showAllProjects(String projectType, String projectPath) {
		List<String> projects = FileUtil.findFoldersInDirectory(projectPath);
		if(projectType!=null && projects.contains(projectType))
			System.out.println(projectType);
		else{
			for (String project : projects) {
				System.out.println(project);
			}
		}
	}

	}
