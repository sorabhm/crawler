package com.crawler.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ForkJoinPool;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Test class for the primary class SpideyTheCrawler
 * 
 * @author smend1
 *
 */
public class SpideyTheCrawlerTest {
	@Mock
	private ForkJoinPool pool;
	
	private SpideyTheCrawler crawler;
	
	@Mock
	private CustomFileWriter writer;
	
	@Before
	public void beforeEachTest() {
		pool = Mockito.mock(ForkJoinPool.class);
		writer = Mockito.mock(CustomFileWriter.class);
		crawler = new SpideyTheCrawler(pool, "http://wiprodigital.com",writer);
		crawler.addToTraversedList("http://wiprodigital.com/");
	}
	
	@Test
	public void testSizeOfTraversedList() {
		crawler.addToTraversedList("http://wiprodigital.com/");
		assertEquals(crawler.sizeOfTraversedList(), 1);
	}
	
	@Test
	public void testIsUrlAlreadyTraversed() {
		crawler.addToTraversedList("http://wiprodigital.com/");
		assertTrue(crawler.isURLAlreadyTraversed("http://wiprodigital.com/"));
	}
	
	@Test
	public void testListOfExternalLinks() {
		crawler.addToExternalLinks("http://twitter.com/");
		assertEquals(crawler.getListOfExternalLinks().size(), 1);
	}
	
	@Test
	public void testListOfImageUrls() {
		crawler.addToImagesList("http://wiprodigital.com/logo.png");
		assertEquals(crawler.getListOfImageUrls().size(), 1);
	}
	
	@Test
	public void testInvokeCrawler() {
		crawler.invokeCrawler();
		
		verify(writer,times(3)).writeDataToFile(anySet(), anyString());
		when(writer.writeDataToFile(anySet(), anyString())).thenReturn(true);
		assertTrue(writer.writeDataToFile(anySet(), anyString()));
	}
	
}
