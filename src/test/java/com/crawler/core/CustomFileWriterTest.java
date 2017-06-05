/**
 * 
 */
package com.crawler.core;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author smend1
 *
 */
public class CustomFileWriterTest {

	private CustomFileWriter writer;
	
	private Set<String> listOfUrls = new HashSet<>();
	
	@Before
	public void setUp() {
		writer = Mockito.mock(CustomFileWriter.class);
		listOfUrls.add("http://wiprodigital.com/join-our-team");
		listOfUrls.add("http://wiprodigital.com/privacy-policy");
		listOfUrls.add("http://wiprodigital.com/what-we-do#wdwork_cases");
	}

	/**
	 * Test method for {@link com.crawler.core.CustomFileWriter#writeDataToFile(java.util.Set, java.lang.String)}.
	 */
	@Test
	public void testWriteDataToFile() {
		writer.writeDataToFile(listOfUrls, "Writing test cases");
		verify(writer).writeDataToFile(listOfUrls, "Writing test cases");
	}
	
	@Ignore
	@Test(expected=IOException.class)
	public void testWriteDataThrowsException() {
		doThrow(IOException.class).when(writer).writeDataToFile(null, "Writing test cases");
	}

}
