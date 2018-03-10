package com.dd;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.dd.operations.CreateStructure;
import com.dd.operations.DeleteStructure;
import com.dd.operations.Description;
import com.dd.operations.ProjectList;

public class Test {

	public static void main(String[] args) throws Exception {

		Options options = new Options();

		Option help = new Option("h", "help", true,
				"Useful commands" + " -t TYPE, --type TYPE The type of the project created from a specific template"
						+ "\n-p PATH, --path PATH The base path in which to create the project. If not supplied, it");
		// help.setRequired(true);
		options.addOption(help);

		Option type = new Option("t", "type", true, " Project strucure name eg Maya,Houdini....");
		// type.setRequired(true);
		options.addOption(type);

		Option path = new Option("p", "path", true, "Path to create/delete project");
		// path.setRequired(true);
		options.addOption(path);

		System.out.println(Arrays.asList(args));

		List<String> crudCommand = Arrays.asList(args);
		CommandLineParser parser = new BasicParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(4);
			return;
		}

		String projectType = cmd.getOptionValue("t");

		String projectPath = cmd.getOptionValue("p");

		if (projectPath == null || projectPath.isEmpty()) {

			projectPath = System.getenv("PROJMAN_LOCATION");

		}

		String templatePath = System.getenv("PROJMAN_TEMPLATES");
		// templatePath = "";
		List<String> templateList = Arrays.asList(templatePath.split(","));
		// check all subcommand type
		CreateStructure createOperations = new CreateStructure();
		if (crudCommand.contains("create")) {
			if (cmd.getOptionValue("p") == null && crudCommand.get(args.length - 1) != null)
				createOperations.createStructure(projectType, projectPath + "\\" + crudCommand.get(args.length - 1),
						templateList);
			else
				createOperations.createStructure(projectType, projectPath, templateList);

		} else if (crudCommand.contains("delete")) {
			DeleteStructure deleteOperations = new DeleteStructure();
			/*if (cmd.getOptionValue("p") == null && cmd.getOptionValue("t") != null
					&& crudCommand.get(args.length - 1) != null)
				deleteOperations.deleteStructure(projectType, projectPath + "\\" + crudCommand.get(args.length - 1));
			else*/
				deleteOperations.deleteStructure(projectType, projectPath);
		} else if (crudCommand.contains("describe")) {
			System.out.println(Description.printDirectoryTree(new File(projectPath + "\\" + projectType)));
		} else if (crudCommand.contains("list") || crudCommand.contains("types")) {
			ProjectList listProject = new ProjectList();
			listProject.showAllProjects(projectType, projectPath);
		} /*else if (crudCommand.contains("types")) {
			
			System.out.println("Yet to implement");
		}*/
		else{
			System.out.println("Invalid subcommand");
		}
	}

}