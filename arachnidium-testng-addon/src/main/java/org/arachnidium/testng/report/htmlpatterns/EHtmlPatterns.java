package org.arachnidium.testng.report.htmlpatterns;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public enum EHtmlPatterns {
	HTMLPATTERN("htmlPattern.htm"), FILEMASK("fileMask.htm");

	private String fileName;

	private EHtmlPatterns(String fileName) {
		this.fileName = fileName;
	}

	public String getHtmlCode() {
		InputStream inputStream = getClass().getResourceAsStream(fileName);
		try {
			InputStreamReader reader = new InputStreamReader(inputStream);

			BufferedReader buffer = new BufferedReader(reader);
			StringBuffer stringBuff = new StringBuffer();
			int data;

			while ((data = buffer.read()) != -1)
				stringBuff.append((char) data);
			return stringBuff.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
