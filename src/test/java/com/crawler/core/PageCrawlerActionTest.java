/**
 * 
 */
package com.crawler.core;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author smend1
 *
 */
public class PageCrawlerActionTest {

	private Set<String> listOfTraversedUrls = new HashSet<>();
	
	private PageCrawlerAction pc;
	
	@Mock
	private SpideyTheCrawler crawler;
	
	@Mock
	Jsoup soup;
	
	@Before
	public void setUp() throws Exception {
		listOfTraversedUrls.add("http://wiprodigital.com/join-our-team");
		listOfTraversedUrls.add("http://wiprodigital.com/privacy-policy");
		listOfTraversedUrls.add("http://wiprodigital.com/what-we-do#wdwork_cases");
		crawler = Mockito.mock(SpideyTheCrawler.class);
		soup = Mockito.mock(Jsoup.class);
		
		pc = new PageCrawlerAction("http://wiprodigital.com", crawler);
	}

	@Ignore("Need to add PowerMock library")
	@Test
	public void testCompute() {
		pc.compute();
	}

}
