package com.crawler.core;

import java.net.MalformedURLException;
import java.net.URL;

public class URLFormatter {

	public static String getHostName(String url) throws MalformedURLException {
		URL aURL = new URL(url);
		return aURL.getHost();
	}
}
