package com.crawler.core;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Helper class containing utility methods
 * @author smend1
 *
 */
public class URLFormatter {

	public static String getHostName(String url) throws MalformedURLException {
		URL aURL = new URL(url);
		return aURL.getHost();
	}
}
