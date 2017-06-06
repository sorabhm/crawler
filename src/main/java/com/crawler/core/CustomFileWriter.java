package com.crawler.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * A helper class to write down the list of URLs into a separate file
 * 
 * @author smend1
 *
 */
public class CustomFileWriter {
	
	public static final Logger logger = Logger.getLogger(CustomFileWriter.class);
	
	public static final String ERR_MSG = "CustomFileWriter::IOException thrown while writing to file : ";

	/**
	 * Main method which generates the output file using the data
	 * 
	 * @param listOfUrls
	 * @param message
	 * @return
	 */
	public boolean writeDataToFile(Set<String> listOfUrls, String message) {
		boolean success  = true;
		
		try(BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"), 
				StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
			writer.write(message);
			listOfUrls.stream().forEach(x -> {
				try {
					writer.write(x + "\n");
				} catch (IOException e) {
					logger.error(ERR_MSG + e);
				}
			});
	
		} catch (IOException e) {
			logger.error(ERR_MSG + e);
			success=false;
		}
		
		return success;
	}

	/**
	 * Helper method to rename the output file and append current date to the same
	 * 
	 * @return
	 */
	public boolean moveGeneratedFile() {
		boolean fileMoveSuccess = true;
		try {
			Files.move(Paths.get("output.txt"), Paths.get("output_" + LocalDate.now() + ".txt"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error(ERR_MSG + e);
			fileMoveSuccess=false;
		}
		return fileMoveSuccess;
	}
}
