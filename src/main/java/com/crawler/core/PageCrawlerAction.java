package com.crawler.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.RecursiveAction;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Subclass of RecursiveAction which further subtasks the job into smallest possible units and invoke them all
 * 
 * @author smend1
 *
 */
public class PageCrawlerAction extends RecursiveAction {
	
	private static final long serialVersionUID = 1857558065964121060L;

	private String url;
	
	private SpideyTheCrawler crawler;
	
	private List<RecursiveAction> subAction;
	
	private final static Logger logger = Logger.getLogger(PageCrawlerAction.class);
	
	ResourceBundle bundle = ResourceBundle.getBundle("CrawlerLabels");

	public PageCrawlerAction(String nextURL, SpideyTheCrawler crawler) {
		this.url = nextURL;
		this.crawler = crawler;
		subAction = new ArrayList<>();
	}

	/**
	 * Overridden compute method which carries out all necessary actions
	 */
	@Override
	protected void compute() {
		int maxPagesToSearch = Integer.parseInt(bundle.getString("maxPagesToSearch"));
		
		if(!crawler.isURLAlreadyTraversed(url) && crawler.sizeOfTraversedList()<maxPagesToSearch) {
			try {
				String hostName = crawler.getHostName();
				
				logger.debug("Connecting to URL = " + url);
				Document document = Jsoup.connect(url).get();
				logger.debug("Connection to URL successful = " + url);
				Elements linksOnPage = document.select("a[href]");
				Elements media = document.select("[src]");
				
				logger.debug("Number of Link elements found on url = " + url + " = " + linksOnPage.size());
				logger.debug("Number of Src attributes found on page url = " + url + " = " + media.size());
				
				parseLinksAvailableOnPage(linksOnPage, hostName);
				parseImagesURLFromPage(media);
				crawler.addToTraversedList(url);
				
				logger.debug("Size of traversed list = " + crawler.sizeOfTraversedList());
				logger.debug("Size of subActions = " + subAction.size());
				invokeAll(subAction);
				
			} catch(MalformedURLException me) {
				logger.error("PageCrawlerAction.compute:: Malformed URL Exception = " + me);
			} catch(IOException se) {
				logger.error("PageCrawlerAction.compute:: IO Exception = " + se);
			} catch(Exception e) {
				logger.error("PageCrawlerAction.compute:: Exception thrown during the page parsing process : " + e );
			}
		}
	}

	/**
	 * Helper methods to iterate over the Elements found on the page and retrieves the required URLs.
	 * If a particular URL is not traversed yet, it is added to the queue as a subtask.
	 * 
	 * @param linksOnPage - List of link elements retrieved from the parsed page
	 * @param host - Default host name of the starting URL, as remaining all will be considered as External URLs and will not be traversed
	 * @throws MalformedURLException
	 * 
	 */
	protected void parseLinksAvailableOnPage(Elements linksOnPage, String host) throws MalformedURLException {
		String urlOnPage="";
		for(Element ele : linksOnPage) {
			urlOnPage = ele.attr("abs:href");
			if(URLFormatter.getHostName(urlOnPage).contains(host)) {
				if(!crawler.isURLAlreadyTraversed(urlOnPage)) {
					logger.debug("This url is not traversed hence adding to subAction " + urlOnPage);
					subAction.add(new PageCrawlerAction(urlOnPage, crawler));
				}
			} else {
				crawler.addToExternalLinks(urlOnPage);
			}
		}
	}

	/**
	 * Another helper method to retrieve images from the document and saves their URLs
	 * 
	 * @param media - All tag elements which have src as part of their attributes
	 */
	protected void parseImagesURLFromPage(Elements media) {
		for(Element ele : media) {
			if("img".equals(ele.tagName())) {
				crawler.addToImagesList(ele.attr("abs:src"));
			}
		}
	}

}
