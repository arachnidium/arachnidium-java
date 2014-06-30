package org.arachnidium.testng.report.iconsforreport;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.internal.Base64Encoder;

public enum EIconsForReport {
	ERROR("error.png"),
	FINE("fine.png"),
	SUCCESS("success.png"),
	WARNING("warning.png");
	
	private String fileName;
	
	private EIconsForReport(String fileName)
	{
		this.fileName = fileName;
	}
	
	public String getBase64()
	{		
		InputStream inputStream = getClass().getResourceAsStream(fileName);
		byte[] iconBytes = null;
		try {
			iconBytes = IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Base64Encoder encoder = new Base64Encoder();
		return encoder.encode(iconBytes);		
	}
}
