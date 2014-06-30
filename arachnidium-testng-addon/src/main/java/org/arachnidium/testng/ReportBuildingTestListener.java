package org.arachnidium.testng;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.arachnidium.util.logging.Log;
import org.arachnidium.util.logging.Photographer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ReportBuildingTestListener implements ITestListener, IHasAttachmentFolder {
	private static final int defaultStatusOnWarning = ITestResult.SUCCESS_PERCENTAGE_FAILURE;

	private static ConverterToTestNGReport converter = new ConverterToTestNGReport();

	public void onFinish(ITestContext arg0) {
		Log.removeConverter(converter);
		//copying pictures for the index.html
		String ouptupDir = arg0.getOutputDirectory();
		String outputParentDir = new File(ouptupDir).getParentFile()
				.getAbsolutePath();
		try {
			FileUtils.copyDirectory(
					new File(ouptupDir + attachedFolder), new File(
							outputParentDir + attachedFolder));
		} catch (IOException e) {
			Log.warning("Can't make a copy of the folder " + ouptupDir + attachedFolder + 
					File.separator + " ! Destitation is " + outputParentDir + attachedFolder +
					File.separator, e);
		}
	}

	public void onStart(ITestContext arg0) {
		Log.addConverter(converter);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		synchronizeTestResults(arg0);
	}

	public void onTestFailure(ITestResult arg0) {
		Log.error("Test has failed");
	}

	public void onTestSkipped(ITestResult arg0) {
		Log.warning("Is skipped");
	}

	public void onTestStart(ITestResult arg0) {
		// it initiates the new result container
		ResultStore.get(arg0);
		TestResultThreadLocal.set(arg0);
		// screnshots for each test will be saved to specified directory
		String picFolder = UUID.randomUUID().toString() + "_"
				+ Calendar.getInstance().getTime().toString().replace(":", " ");
		String fileOutPutDir = arg0.getTestContext().getOutputDirectory()
				+ attachedFolder + picFolder + File.separator;
		new File(fileOutPutDir).mkdirs();
		Photographer.setOutputFolder(fileOutPutDir);
	}

	public void onTestSuccess(ITestResult arg0) {
		synchronizeTestResults(arg0);
	}

	// sets real status according to Log information
	protected void synchronizeTestResults(ITestResult resultForSync) {
		Level resultLevel = ResultStore.getLevel(resultForSync);
		if (resultLevel == Level.SEVERE) {
			resultForSync.setStatus(ITestResult.FAILURE);
			Log.error("Test has failed");
		} else if (resultLevel == Level.WARNING) {
			resultForSync.setStatus(defaultStatusOnWarning);
		}

	}

}
