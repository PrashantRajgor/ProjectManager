package com.dd.operations;

import java.io.File;

public class Description {

	/**
	 * Pretty print the directory tree and its file names.
	 * 
	 * @param folder
	 *            must be a folder.
	 * @return
	 */
	public static String printDirectoryTree(File folder) throws IllegalArgumentException {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("folder is not a Directory");
		}
		int indent = 0;
		StringBuilder sb = new StringBuilder();
		printDirectoryTree(folder, indent, sb);
		return sb.toString();
	}

	/**
	 * Prints the directory tree.
	 *
	 * @param folder the folder
	 * @param indent the indent
	 * @param sb the sb
	 */
	private static void printDirectoryTree(File folder, int indent, StringBuilder sb) {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("folder is not a Directory");
		}
		sb.append(getIndentString(indent));
		sb.append("|--");
		sb.append(folder.getName());
		sb.append("\n");
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				printDirectoryTree(file, indent + 1, sb);
			} else {
				printFile(file, indent + 1, sb);
			}
		}

	}

	private static void printFile(File file, int indent, StringBuilder sb) {
		sb.append(getIndentString(indent));
		sb.append("|--");
		sb.append(file.getName());
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