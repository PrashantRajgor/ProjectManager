package com.dd.operations;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class ProjectList {

	public void showAllProjects(String projectType, String projectPath) {
		List<String> projects = findFoldersInDirectory(projectPath);
		if(projectType!=null && projects.contains(projectType))
			System.out.println(projectType);
		else{
			for (String project : projects) {
				System.out.println(project);
			}
		}
	}

	
	private List<String> findFoldersInDirectory(String directoryPath) {
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
