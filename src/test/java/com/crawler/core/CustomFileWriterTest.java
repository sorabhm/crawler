/**
 * 
 */
package com.crawler.core;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImageFilter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.mockmaker.PowerMockMaker;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author smend1
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Files.class)
public class CustomFileWriterTest {

	private CustomFileWriter app;
	
	@Mock
	private BufferedWriter writer;
	
	private Set<String> listOfUrls = new HashSet<>();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		writer = mock(BufferedWriter.class);
		PowerMockito.mockStatic(Files.class);
		listOfUrls.add("http://wiprodigital.com/join-our-team");
		listOfUrls.add("http://wiprodigital.com/privacy-policy");
		listOfUrls.add("http://wiprodigital.com/what-we-do#wdwork_cases");
		
		app = new CustomFileWriter();
	}

	/**
	 * Test method for {@link com.crawler.core.CustomFileWriter#writeDataToFile(java.util.Set, java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testWriteDataToFile() throws IOException {
		PowerMockito.when(Files.newBufferedWriter(any(),any())).thenReturn(writer);
		app.writeDataToFile(listOfUrls, "Writing test cases");
		//verify(writer).write(anyString());
	}
	
	@Test
	public void testWriteDataThrowsException() throws IOException {
		doThrow(IOException.class).when(writer).write("");
	}

}
