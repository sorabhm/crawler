/**
 * 
 */
package com.crawler.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * This class is used to initialize the ForkJoinPool and trigger the initial recursive task. 
 * Given there was no specific returning data hence RecursiveAction class has been overridden. 
 * Once the data (URLs of Pages traversed, External links, and Image urls) is populated in the synchronized Sets
 * then it is written to a text file created with in the workspace itself with the name output_<CURRENT_DATE>
 * 
 * @author smend1
 *
 */
public class SpideyTheCrawler {

	private Set<String> uniqueListOfUrlsAlreadyTraversed = Collections.synchronizedSet(new HashSet<String>());
	
	private Set<String> listOfImages = Collections.synchronizedSet(new HashSet<String>());
	
	private Set<String> listOfExternalLinks = Collections.synchronizedSet(new HashSet<String>());
	
	private ForkJoinPool forkJoinPool;
	
	public static final int MAX_PAGES_TO_SEARCH = 10;
	
	private String startingURL;
	
	private CustomFileWriter writer;
	
	private AtomicInteger count = new AtomicInteger();
	
	private String hostName;
	
	private final static Logger logger = Logger.getLogger(SpideyTheCrawler.class);
	
	/**
	 * Main constructor for the class
	 * 
	 * @param pool ForkJoinPool instance instantiated by the called
	 * @param url Starting URL
	 * @param writer Custom File writer to generate the output in required format
	 * 
	 */
	public SpideyTheCrawler(ForkJoinPool pool, String url, CustomFileWriter writer) {
		this.startingURL = url;
		forkJoinPool = pool;
		this.writer = writer;
	}
	
	/**
	 * Invoker of the subtasks using ForkJoinPool. Rest is managed by the subclass of RecursiveAction.
	 * Once processing is done the data is dumped into a text file.
	 */
	public void invokeCrawler() {
		logger.debug("Invoking the crawler for URL => " + startingURL);
		boolean success = true;
		forkJoinPool.invoke(new PageCrawlerAction(startingURL, this));
		
		logger.debug("Data parsing is completed, writing data to file");
		writer.writeDataToFile(getListOfTraversedUrls(), "Unique URLs accessed by the application\n");
		writer.writeDataToFile(getListOfImageUrls(), "\n\nList of images parsed during processing\n");
		writer.writeDataToFile(getListOfExternalLinks(), "\n\nList of External links found during processing \n\n");
		success = writer.moveGeneratedFile();
		if(success) {
			logger.debug("All data successfully written to the file, and the file has been renamed.");
		} else {
			logger.error("ERROR:: Some error while writing/renaming file. Please check the output.");
		}
	}
	
	public boolean isURLAlreadyTraversed(String url) {
		return uniqueListOfUrlsAlreadyTraversed.contains(url);
	}

	public void addToTraversedList(String url) {
		uniqueListOfUrlsAlreadyTraversed.add(url);
		count.incrementAndGet();
	}

	public int sizeOfTraversedList() {
		return uniqueListOfUrlsAlreadyTraversed.size();
	}

	public void addToExternalLinks(String url) {
		listOfExternalLinks.add(url);
	}
	
	public void addToImagesList(String url) {
		listOfImages.add(url);
	}
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public Set<String> getListOfExternalLinks() {
		return listOfExternalLinks;
	}
	
	public Set<String> getListOfImageUrls() {
		return listOfImages;
	}
	
	public Set<String> getListOfTraversedUrls() {
		return uniqueListOfUrlsAlreadyTraversed;
	}
}
