package com.dd;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.dd.operations.Description;
import com.dd.operations.impl.StructureManagerImpl;
import com.dd.operations.intf.IStructureManager;

/**
 * The Class CLI.
 */
public class CLI {

	/** The commands. */
	static String[] commands = { "create", "describe", "delete", "list", "types" };

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {

		Options options = new Options();

		Option help = new Option("h", "help", false,
				"Useful commands : \t-t TYPE, --type TYPE The type of the project created from a specific template"
						+ "\n\t\t\t-p PATH, --path PATH The base path in which to create the project.\n Usage:\n pm [options] SUBCMD [NAME]\n"
						+ "Arguments:\n\t SUBCMD The subcommand to execute. (list,create,delete,types)"
						+ "\n\tNAME The name of the project to create, delete, or run types on.");
		options.addOption(help);

		Option type = new Option("t", "type", true, " Project strucure name eg Maya,Houdini....");
		options.addOption(type);

		Option path = new Option("p", "path", true, "Path to create/delete project");
		options.addOption(path);

		List<String> crudCommand = Arrays.asList(args);
		CommandLineParser parser = new BasicParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			formatter.printHelp("utility-name", options);

			System.exit(4);
			return;
		}

		List<String> subCommand = Arrays.asList(commands);

		String projectType = cmd.getOptionValue("t");
		if (subCommand.contains(projectType)) {
			System.out.println("invalid command, TYPE not mentioned");
			System.exit(4);
		}
		String projectPath = cmd.getOptionValue("p");

		if (projectPath == null || projectPath.isEmpty()) {

			projectPath = System.getenv("PROJMAN_LOCATION");
		}

		// check all subcommand type
		IStructureManager structureManager = new StructureManagerImpl();
		if (crudCommand.contains("create")) {

			if (projectType != null) {
				if (!crudCommand.get(args.length - 1).equals(projectType)) {
					// create at pro loc or path by type only
					projectPath = projectPath + File.separator + crudCommand.get(args.length - 1);

				}

			}else {
				projectPath = projectPath + File.separator + crudCommand.get(args.length - 1);
			}

			try {
			structureManager.createStructure(projectType, projectPath);
			System.out.println("Structure created");
			} catch (Exception e) {
				System.out.println("Exception while creating structure");
				System.exit(4);
				return;
			}

		} else if (crudCommand.contains("delete")) {
			try {

				boolean flag = false;

				if(projectType != null)
				{
					if (!crudCommand.get(args.length - 1).equals(projectType)) {
						projectPath = projectPath + File.separator + crudCommand.get(args.length - 1);

					}
				}else{
					projectPath = projectPath + File.separator + crudCommand.get(args.length - 1);
				
				}
				flag = structureManager.deleteStructure(projectType,projectPath );
			
				if (flag)
					System.out.println("Structure deleted");
			} catch (Exception e) {
				System.out.println("Exception while deleting structure");
				System.exit(4);
			}
		} else if (crudCommand.contains("describe")) {
			try {
				String tree = Description.printDirectoryTree();
				System.out.println(tree);
			} catch (Exception e) {
				System.out.println("Project directory not found" + e);
			}
		} else if (crudCommand.contains("list")) {
			structureManager.showAllProjects(projectType, projectPath);
		} else if (crudCommand.contains("types")) {
			Set<String> projectsType = structureManager.getTypesOfProjects();
			System.out.println(projectsType);
		} else {
			System.out.println(
					"Useful commands : \t-t TYPE, --type TYPE The type of the project created from a specific template"
							+ "\n\t\t\t-p PATH, --path PATH The base path in which to create the project.\n Usage:\n pm [options] SUBCMD [NAME]\n"
							+ "Arguments:\n\t SUBCMD The subcommand to execute. (list,create,delete,types)"
							+ "\n\tNAME The name of the project to create, delete, or run types on.");
		}
	}

}