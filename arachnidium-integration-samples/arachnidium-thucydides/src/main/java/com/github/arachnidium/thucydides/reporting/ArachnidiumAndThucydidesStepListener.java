package com.github.arachnidium.thucydides.reporting;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.github.arachnidium.util.logging.ILogConverter;
import com.github.arachnidium.util.logging.Log;
import com.github.arachnidium.util.logging.Log.LogRecWithAttach;
import com.github.arachnidium.util.logging.Photographer;

import net.thucydides.core.model.DataTable;
import net.thucydides.core.model.Story;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.screenshots.ScreenshotAndHtmlSource;
import net.thucydides.core.steps.BaseStepListener;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.webdriver.SystemPropertiesConfiguration;

public class ArachnidiumAndThucydidesStepListener extends BaseStepListener implements StepListener, 
    //attention please
	ILogConverter {
	
	private static final File outputDirectory = new SystemPropertiesConfiguration(
			new SystemEnvironmentVariables()).getOutputDirectory();

	public ArachnidiumAndThucydidesStepListener() {
		super(outputDirectory);
		Photographer.setCommonOutputFolder(outputDirectory.getAbsolutePath()
				+ "/");
		Log.addConverter(this);
	}

	@Override
	public void testSuiteStarted(Class<?> storyClass) {
		super.testSuiteStarted(storyClass);
	}

	public void testSuiteStarted(Story story) {
		super.testSuiteStarted(story);
	}

	public void testSuiteFinished() {
		super.testSuiteFinished();
	}

	public void testStarted(String description) {
		super.testStarted(description);
	}

	// cause thucydides can't get screenshot from webdriver that has been
	// initiated externally, I made this workaround
	private void sync(TestOutcome result) {
		List<TestStep> myFlattenedSteps = getCurrentTestOutcome()
				.getFlattenedTestSteps();
		List<TestStep> externalFlattenedSteps = result.getFlattenedTestSteps();

		for (int i = 0; i < myFlattenedSteps.size(); i++) {
			TestStep myStep = myFlattenedSteps.get(i);
			TestStep externalStep = externalFlattenedSteps.get(i);

			List<ScreenshotAndHtmlSource> myScreenShots = myStep
					.getScreenshots();
			for (ScreenshotAndHtmlSource screenShot : myScreenShots) {
				externalStep.addScreenshot(screenShot);
			}
			externalStep.setResult(myStep.getResult());
		}
	}

	public void testFinished(TestOutcome result) {
		sync(result);
		super.testFinished(result);
	}

	public void testRetried() {
		super.testRetried();
	}

	public void stepStarted(ExecutedStepDescription description) {
		super.stepStarted(description);
	}

	public void skippedStepStarted(ExecutedStepDescription description) {
		super.skippedStepStarted(description);
	}

	public void stepFailed(StepFailure failure) {
		super.stepFailed(failure);
	}

	public void lastStepFailed(StepFailure failure) {
		super.stepFailed(failure);
	}

	public void stepIgnored() {
		super.stepIgnored();
	}

	public void stepPending() {
		super.stepPending();
	}

	public void stepPending(String message) {
		super.stepPending();
	}

	public void stepFinished() {
		TestStep step = getCurrentTestOutcome().currentStep();
		if ((step.getResult() != TestResult.ERROR)
				& (step.getResult() != TestResult.FAILURE)) {
			super.stepFinished();
		}

	}

	public void testFailed(TestOutcome testOutcome, Throwable cause) {
		sync(testOutcome);
		super.testFailed(testOutcome, cause);
	}

	public void testIgnored() {
		super.testIgnored();
	}

	@Override
	@Deprecated
	public void notifyScreenChange() {
	}

	public void useExamplesFrom(DataTable table) {
		super.useExamplesFrom(table);

	}

	public void exampleStarted(Map<String, String> data) {
		super.exampleStarted(data);
	}

	public void exampleFinished() {
		super.exampleFinished();

	}

	public void assumptionViolated(String message) {
		super.assumptionViolated(message);
	}

	public void convert(LogRecWithAttach arg0) {
		File screenShot = arg0.getAttachedFile();
		TestStep step = getCurrentTestOutcome().currentStep();
		if (screenShot != null) { // is it picture
			if (screenShot.getAbsolutePath().contains(Photographer.format)) {
				step.addScreenshot(new ScreenshotAndHtmlSource(arg0
						.getAttachedFile()));
			}
		}
		if ((arg0.getLevel() == Level.WARNING)
				& (step.getResult() != TestResult.FAILURE)) { // if there is a
																// warning
			step.setResult(TestResult.ERROR);
		}
		if (arg0.getLevel() == Level.SEVERE) { // if there is a warning
			step.setResult(TestResult.FAILURE);
		}
	}

}
