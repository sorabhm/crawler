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
		
		SpideyTheCrawler crawler = new SpideyTheCrawler(pool, startingURL, writer);
		try {
			crawler.setHostName(URLFormatter.getHostName(startingURL));
		} catch (MalformedURLException e) {
			logger.error("Malformed URL exception = ", e);
		}
		crawler.invokeCrawler();
		logger.debug("Completed the crawling for URL = " + startingURL);
	}
}
