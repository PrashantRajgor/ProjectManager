package com.dd.operations;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DeleteStructure {

	public void deleteStructure(String projectType, String projectPath) {

		if (projectType != null) {
			projectPath += "\\" + projectType.toLowerCase();
		}

		File file = new File(projectPath.toLowerCase());
		if (!file.isDirectory()) {
			System.out.println("Invalid path for deleting project");
			return;
		}
		try {
			deleteExistingDirectory(projectPath);
		} catch (IOException e) {
			System.out.println("Error occured while deleting project");
			return;

		}

	}

	private void deleteExistingDirectory(String projectPath) throws IOException {

		FileUtils.deleteDirectory(new File(projectPath));
	}

}
