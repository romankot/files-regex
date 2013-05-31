package com.michelcasanova.regex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

/**
 * The Class Regex.
 * 
 * @author Miguel A. Casanova
 * 
 */
public class Regex {
	
	private static final String TEMP_FILE_NAME = "temp.txt";
	private static final Charset ENCODING = StandardCharsets.UTF_8;
	
	/**
	 * Apply.
	 *
	 * @param file file or directory. In case of directory the changes are applied to all the files contained in the folder and sub-folders
	 * @param regEx the regular expression that is going to be replaced
	 * @param replacement the replacement
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void apply(File file, String regEx, String replacement)throws IOException{
		
		
		if(file.isDirectory()){
			
			for(File f : file.listFiles()){
				
				apply(f, regEx, replacement);
			}
			
		}
		else{
			applyRegexToFile(file, regEx, replacement);
		}
		
		
	}
	
	/**
	 * Apply.
	 *
	 * @param path file or directory path. In case of directory the changes are applied to all the files contained in the folder and sub-folders
	 * @param regEx the regular expression that is going to be replaced
	 * @param replacement the replacement
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void apply(String path, String regex, String replacement)throws IOException{
		
		apply(new File(path), regex, replacement);
	}
	
	
	/**
	 * Apply a regular expression replacement to a file.
	 *
	 * @param file the file
	 * @param regex the regular expresion
	 * @param replacement the replacement
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void applyRegexToFile(final File file, final String regex, final String replacement) throws IOException{
		
		final BufferedReader input;
		BufferedWriter output;
		String line, lineUpdated;
		File temp;
		
		temp = new File(file.getParent()+"\\"+TEMP_FILE_NAME);
		temp.createNewFile();
		input = Files.newBufferedReader(file.toPath(), ENCODING);
		output = Files.newBufferedWriter(temp.toPath(), ENCODING,  new OpenOption[] {StandardOpenOption.TRUNCATE_EXISTING});
		
		while( (line = input.readLine()) != null){
			
			lineUpdated = line.replaceAll(regex, replacement);
			output.write(lineUpdated+"\n");
		}	
		
		input.close();
		output.close();
		replaceFile(file, temp);
	}
	
	private static void replaceFile(File file, File fileToReplace){
		
		String path = file.getPath();
		file.delete();
		fileToReplace.renameTo(new File(path));
	}	
}