package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import helpers.Constants;

/**
 * 
 * @author Riteesh
 *
 */
//This class creates text files from the html files
public class CreateTextFiles {

	public static void createFiles() {
		try {

			String htmlFilesDirectoryPath = Constants.PROJECT_PATH + "/resources/webpages/"; // directory path to
																								// webpages scraped
			String textFilesDirectoryPath = Constants.PROJECT_PATH + "/resources/textFiles/"; // directory path to text files that converted from Html files
																								 
																								

			File htmlFilesDirectory = new File(htmlFilesDirectoryPath); // Creating file object

			File[] htmlFilesList = htmlFilesDirectory.listFiles(); // returns files in directory

			// iterates through list of files in the directory
			for (File file : htmlFilesList) {
				String fileName = file.getName();
				processHtmltoText(file.getPath(), fileName.substring(0, fileName.lastIndexOf(".")),
						textFilesDirectoryPath); // calls processFile method with file details as parameters
				System.out.println(file.getName() + ".txt");
			}

		} catch (Exception io) {

		}
	}

	public static void processHtmltoText(String fileLoc, String fileName, String fileDestination) {
		try {

			FileReader in = new FileReader(fileLoc);
			HtmlToText parser = new HtmlToText();
			parser.parse(in);
			in.close();
			String textHTML = parser.getText();

			BufferedWriter textWrite = new BufferedWriter(new FileWriter(fileDestination + fileName + ".txt"));
			textWrite.write(textHTML);
			textWrite.close();

		} catch (Exception e) {
			System.out.print(e);
		}

	}
	/*
	 * public static void createInvertedIndexMap() throws IOException {
	 * ArrayList<String> FileNameArray = new ArrayList<String>();
	 * 
	 * String allTextFilesDirLocStr = Constants.PROJECT_PATH +
	 * "/resources/textFiles/";
	 * 
	 * File allFilesDirLoc = new File(allTextFilesDirLocStr);
	 * 
	 * File[] listOfAllHtmlFiles = allFilesDirLoc.listFiles(); int i=0; for (File
	 * file : listOfAllHtmlFiles) { String fileNameArray2 = file.getPath();
	 * FileNameArray.add(fileNameArray2.toString()); i=i+1;
	 * 
	 * } CreateInvertedIndex index = new CreateInvertedIndex(FileNameArray);
	 * String[] queryArray = {"cashback", "Low interest", "Airmiles", "mastercard"};
	 * for (String query : queryArray) { index.queryIndex(query); } //
	 * index.queryIndexAND("student", "mastercard"); }
	 */
}
