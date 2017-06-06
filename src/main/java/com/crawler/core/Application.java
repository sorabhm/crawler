package com.crawler.core;

import java.net.MalformedURLException;
import java.util.concurrent.ForkJoinPool;

import org.apache.log4j.Logger;

/**
 * Input:
 * The purpose of this class is to invoke the crawler. The main method expects 2 arguments: 
 * a) Starting URL
 * b) Number of threads to run in parallel
 * 
 * Output: 
 * Creates a simple text file with format output_<CURRENT_DATE> having the 
 * list of unique URLs which are visited, list of External URLs found during parsing, 
 * list of Images identified. 
 * 
 * As per implementation this crawler is based on Fork/Join framework, where parsing a page and 
 * fetching required information is a recursive sub task. 
 * 
 * If no arguments are passed, the default URL will be http://wiprodigital.com/ and maxThreads = 20
 * 
 * Format for execution and other details can be found in the Readme.md file available in the repository.
 * 
 * @author smend1
 *
 */
public class Application {

	private final static Logger logger = Logger.getLogger(Application.class);
	
	/**
	 * Main method of the application
	 * 
	 * Execution format from command line
	 * mvn exec:java -Dexec.args="http://wiprodigital.com/ 20"
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String startingURL = "http://wiprodigital.com/";
		int maxThreads = 20;
		if(null!=args && args.length>0) {
			if(null!=args[0] && !"".equals(args[0].trim())) {
				startingURL = args[0];
			}
			if(null!=args[1]) {
				maxThreads = Integer.parseInt(args[1]);
			}
		}
		logger.debug("Starting the crawler application for URL = " + startingURL);
		
		ForkJoinPool pool = new ForkJoinPool(maxThreads);
		CustomFileWriter writer = new CustomFileWriter();
		try {
			SpideyTheCrawler crawler = new SpideyTheCrawler(pool, startingURL, writer);
			crawler.setHostName(URLFormatter.getHostName(startingURL));
			crawler.invokeCrawler();
		} catch (MalformedURLException e) {
			logger.error("Malformed URL exception = ", e);
		} finally {
			if(!pool.isTerminated()) {
				logger.debug("Shutting down the pool");
				pool.shutdown();
			}
		}
		
		logger.debug("Completed the crawling for URL = " + startingURL);
	}
}
