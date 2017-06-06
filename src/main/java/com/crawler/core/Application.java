package com.crawler.core;

import java.net.MalformedURLException;
import java.util.concurrent.ForkJoinPool;

import org.apache.log4j.Logger;

public class Application {

	private final static Logger logger = Logger.getLogger(Application.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String startingURL = "http://google.com/";
		int maxThreads = 200;
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
