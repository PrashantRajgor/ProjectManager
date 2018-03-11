package com.dd.operations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteStructure.
 */
public class DeleteStructure {

	/**
	 * Delete structure.
	 *
	 * @param projectType the project type
	 * @param projectPath the project path
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean deleteStructure(String projectType, String projectPath) throws IOException {
		boolean flag = false;
		List<String> projects = FileUtil.findFoldersInDirectory(projectPath);
		for (String name : projects) {
			File file = new File(projectPath.toLowerCase() + File.separator + name);

			if (name.equalsIgnoreCase(projectType)) {
				//delete all type structure for location if specific location not provided
				deleteExistingDirectory((projectPath.toLowerCase() + File.separator + name));
				flag =true;
			} else {

				if (file.isDirectory() && projectType!=null) {
					String updatedPath = projectPath.toLowerCase() + File.separator + name + File.separator + projectType.toLowerCase();

					File file2 = new File(updatedPath);
					if (file2.isDirectory()) {
						deleteExistingDirectory(updatedPath);
						flag = true;
					}
					
				}
			}
			
		}if(!flag){
			System.out.println(projectType+" Type not exist ");
		}
		return flag;
	}

	/**
	 * Delete existing directory.
	 *
	 * @param projectPath the project path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void deleteExistingDirectory(String projectPath) throws IOException {

		FileUtils.deleteDirectory(new File(projectPath));
	}

}
