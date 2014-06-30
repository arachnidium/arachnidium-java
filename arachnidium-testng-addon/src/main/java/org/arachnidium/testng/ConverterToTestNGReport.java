/**
 * 
 */
package org.arachnidium.testng;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.arachnidium.testng.report.htmlpatterns.EHtmlPatterns;
import org.arachnidium.testng.report.iconsforreport.EIconsForReport;
import org.arachnidium.util.logging.ILogConverter;
import org.arachnidium.util.logging.Log;
import org.arachnidium.util.logging.Log.LogRecWithAttach;
import org.arachnidium.util.logging.Photographer;
import org.arachnidium.util.logging.eLogColors;
import org.testng.Reporter;

/**
 * @author s.tihomirov It is the basic implementation of ISender for using by
 *         TESTNG framework. It posts log record in report
 */
class ConverterToTestNGReport implements ILogConverter, IHasAttachmentFolder {

	private final String debugColor = eLogColors.DEBUGCOLOR
			.getHTMLColorDescription();
	private final String errorColor = eLogColors.SEVERESTATECOLOR
			.getHTMLColorDescription();

	private final String successColor = eLogColors.CORRECTSTATECOLOR
			.getHTMLColorDescription();
	private final String warnColor = eLogColors.WARNSTATECOLOR
			.getHTMLColorDescription();

	private final String expressionOfFilePath = "#filepath";
	private final String expressionOfIconCode   = "#Icon";
	private final String expressionOfComment    = "#Comment";

	private final String expressionOfColorPattern = "#Color";
	private final String expressionOfTimePattern = "#Time";
	private final String expressionOfMessagePattern = "#Message";
	
	private final String successIcon = EIconsForReport.SUCCESS.getBase64();
	private final String warnIcon    = EIconsForReport.WARNING.getBase64();
	private final String errorIcon   = EIconsForReport.ERROR.getBase64();
	private final String debugIcon   = EIconsForReport.FINE.getBase64();

	private final String htmlPatternString = EHtmlPatterns.HTMLPATTERN
			.getHtmlCode();
	private final String htmlFileMaskString = EHtmlPatterns.FILEMASK
			.getHtmlCode();

	private String formatWithStackTrace(String original, LogRecWithAttach rec) {
		String formatted = null;

		StringBuilder stackBuilder = new StringBuilder();
		stackBuilder.append(original + "\n");
		// if there is a stack trace
		if (rec.getThrown() != null) {
			StackTraceElement stack[] = rec.getThrown().getStackTrace();
			int stackLength = stack.length;

			for (int i = 0; i < stackLength; i++) {
				stackBuilder.append(stack[i].toString() + "\n");
			}

			stackBuilder.append("...");
		}
		formatted = stackBuilder.toString();
		return formatted;
	}
	
	private File makeACopy(File original){
		File dirs = new File(getOutPutDir() + attachedFolder);
		dirs.mkdirs();
		File destination = new File(getOutPutDir() + attachedFolder + UUID.randomUUID().toString() + "_"
		+ original.getName());
		try {
			FileUtils.copyFile(original, destination);
			return destination;
		} catch (IOException e) {
			Log.warning("There is a problem with attaching file" + original.getName(), e);
			throw new RuntimeException(e);
		}
	}

	private String returnLogMessage(LogRecWithAttach rec) {
		String formattedMessage = null;
		File attachment = rec.getAttachedFile();
		if (attachment == null) // if there is no attached file
		{
			formattedMessage = rec.getMessage();
		} else {
			String pattern = null;
			File fileToAttach = attachment;
			pattern = htmlFileMaskString;
			if (!fileToAttach.getName().endsWith("." + Photographer.format)){ // if there is not a
															// picture				
				fileToAttach = makeACopy(fileToAttach);
			}
			formattedMessage = pattern.replace(expressionOfComment,
					rec.getMessage());
			String pathToAttach = fileToAttach.getAbsolutePath();
			formattedMessage = formattedMessage.replace(expressionOfFilePath,
					"." + File.separator + pathToAttach.replace(getOutPutDir(), ""));
		}
		return formatWithStackTrace(formattedMessage, rec);
	}
	
	private String getOutPutDir(){
		return TestResultThreadLocal.get().getTestContext()
				.getOutputDirectory();
	}

	private String returnHtmlString(LogRecWithAttach rec) {

		Level level = rec.getLevel();
		String color = null;
		String icon = null;
		if (level == Level.SEVERE) {
			color = errorColor;
			icon = errorIcon;
		} else if (level == Level.WARNING) {
			color = warnColor;
			icon = warnIcon;
		} else if (level == Level.INFO) {
			color = successColor;
			icon = successIcon;
		} else {
			color = debugColor;
			icon = debugIcon;
		}

		Date date = new Date(rec.getMillis());
		String turnedString = htmlPatternString;
		turnedString = htmlPatternString.replace(expressionOfColorPattern,
				color);
		turnedString = turnedString.replace(expressionOfIconCode, icon);
		turnedString = turnedString.replace(expressionOfTimePattern,
				date.toString());
		turnedString = turnedString.replace(expressionOfMessagePattern,
				returnLogMessage(rec));

		return turnedString;
	}

	private void setToReport(String htmlInjection) {
		Reporter.setEscapeHtml(false);
		Reporter.log(htmlInjection);
	}

	public void convert(LogRecWithAttach record) {
		setToReport(returnHtmlString(record));
		;
		ResultStore store = ResultStore.get(TestResultThreadLocal.get());
		store.addLogRecord(record);
	}
}
