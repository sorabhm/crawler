/**
 * 
 */
package com.crawler.core;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Test class for PageCrawlerAction
 * 
 * @author smend1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class PageCrawlerActionTest {

	private Set<String> listOfTraversedUrls = new HashSet<>();
	
	@InjectMocks
	private PageCrawlerAction pc;
	
	private SpideyTheCrawler crawler;
	private Document document;
	private Elements elements;
	private HttpConnection conn;
	
	@Before
	public void setUp() throws Exception {
		setupData();
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(Jsoup.class);
		crawler = mock(SpideyTheCrawler.class);
		conn = mock(HttpConnection.class);
		pc = new PageCrawlerAction("http://wiprodigital.com", crawler);
	}

	private void setupData() {
		listOfTraversedUrls.add("http://wiprodigital.com/join-our-team");
		listOfTraversedUrls.add("http://wiprodigital.com/privacy-policy");
		listOfTraversedUrls.add("http://wiprodigital.com/what-we-do#wdwork_cases");
		
		document = Jsoup.parse("<head></head><body>"
			+ "<a href=\"http://wiprodigital.com/link1\">link1</a>"
			+ "<a href=\"http://wiprodigital.com/link2\">link2</a>"
			+ "<a href=\"http://wiprodigital.com/link3#anchor\">anchor</a>"
			+ "<a href=\"http://wiprodigital.com/\">no link</a>"
			+ "<a href=\"http://twitter.com/relative\">relative</a>"
			+ "<img src=\"http://wiprodigital.com/img.logo\"/>"
			+ "</body>");
		elements = document.getAllElements();
	}

	@Ignore
	@Test
	public void testComputeWithoutAnySubAction() throws IOException {
		
		when(crawler.isURLAlreadyTraversed(anyString())).thenReturn(true);
		when(crawler.getHostName()).thenReturn("wiprodigital.com");
		PowerMockito.when(Jsoup.connect(anyString())).thenReturn(conn);
		PowerMockito.when(conn.get()).thenReturn(document);
		when(crawler.sizeOfTraversedList()).thenReturn(3);
		
		pc.compute();
		
		assertNotNull(elements);
		verify(crawler,times(1)).addToTraversedList(anyString());
		verify(crawler,times(1)).addToExternalLinks(anyString());
		verify(crawler,times(1)).addToImagesList(anyString());
	}
	
	@Test
	public void testComputeThrowsIOException() throws IOException {

	    doThrow(IOException.class).when(conn).get();
	    pc.compute();
		
	}
	
	@Test
	public void testComputeThrowsMalformedUrlException() {

		doThrow(MalformedURLException.class).when(crawler).getHostName();
	    pc.compute();
		
	}

}
