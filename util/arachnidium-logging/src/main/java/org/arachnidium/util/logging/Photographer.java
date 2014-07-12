package org.arachnidium.util.logging;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.Augmenter;

public final class Photographer {
	private static Photographer get() {
		Photographer photographer = photographerThreadLocal.get();
		if (photographer == null) {
			photographer = new Photographer();
			photographerThreadLocal.set(photographer);
		}
		return photographer;
	}

	public static synchronized void setCommonOutputFolder(String pathToFolder) {
		pictureFolderNameByDefault = pathToFolder;
	}

	public static void setOutputFolder(String pathToFolder) {
		get().folder = pathToFolder;
	}

	// takes pictures of full browser windows
	public static void takeAPictureForLog(WebDriver driver,
			eAvailableLevels LogLevel, String Comment)
			throws NoSuchWindowException {
		Photographer photographer = get();
		try {
			BufferedImage imageForLog = photographer.takeAPicture(driver);
			photographer.makeFileForLog(imageForLog, LogLevel, Comment);
		} catch (IOException e) {
			Log.warning("Can't post a picture to log! " + e.getMessage());
			Log.log(LogLevel, Comment);
		} catch (NoSuchWindowException e) {
			throw e;
		} catch (ClassCastException | UnsupportedOperationException e) {
			Log.debug(
					"Operation is not supported! Take a screenshot. "
					+ e.getMessage(), e);
			Log.log(LogLevel, Comment);
		}
	}

	public static void takeAPictureOfAFine(WebDriver driver, String Comment) {
		takeAPictureForLog(driver, eAvailableLevels.FINE, Comment);
	}

	public static void takeAPictureOfAnInfo(WebDriver driver, String Comment) {
		takeAPictureForLog(driver, eAvailableLevels.INFO, Comment);
	}

	public static void takeAPictureOfASevere(WebDriver driver, String Comment) {
		takeAPictureForLog(driver, eAvailableLevels.SEVERE, Comment);
	}

	public static void takeAPictureOfAWarning(WebDriver driver, String Comment) {
		takeAPictureForLog(driver, eAvailableLevels.WARN, Comment);
	}

	private final static String pictureNameByDefault = "picture";

	// in case if there is no customized settings for picture storing
	private static String pictureFolderNameByDefault = "Imgs" + File.separator; 

	public static final String format = "png";

	private static final ThreadLocal<Photographer> photographerThreadLocal = new ThreadLocal<Photographer>();

	private String folder = pictureFolderNameByDefault;

	private Photographer() {
		super();
	}

	private BufferedImage getBufferedImage(byte[] original) throws IOException {
		BufferedImage buffer = null;
		try {
			buffer = ImageIO.read(new ByteArrayInputStream(original));
			return buffer;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private BufferedImage getImageFromDriver(WebDriver driver)
			throws IOException {
		byte[] bytes = null;
		try {
			bytes = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.BYTES);
		} catch (ClassCastException e) {
			WebDriver augmentedDriver = new Augmenter()
			.augment(((WrapsDriver) driver).getWrappedDriver());
			bytes = ((TakesScreenshot) augmentedDriver)
					.getScreenshotAs(OutputType.BYTES);
		}
		BufferedImage buffer = getBufferedImage(bytes);
		return buffer;
	}

	// applies images
	private synchronized void makeFileForLog(BufferedImage imageForLog,
			eAvailableLevels LogLevel, String Comment) {

		String FolderPath = folder;

		File ImgFolder = new File(FolderPath);
		ImgFolder.mkdirs();

		File picForLog = new File(FolderPath + pictureNameByDefault + '_'
				+ UUID.randomUUID().toString() + "." + format);
		try {
			ImageIO.write(imageForLog, format, picForLog);
			Log.log(LogLevel, Comment, picForLog);
		} catch (IOException e) {
			Log.warning("Can't take a screenshot! " + e.getMessage());
		}
	}

	// takes pictures and makes buffered images
	private synchronized BufferedImage takeAPicture(WebDriver driver)
			throws IOException, NoSuchWindowException,
			UnsupportedOperationException {
		try {
			return getImageFromDriver(driver);
		} catch (UnsupportedOperationException e) {
			throw e;
		} catch (NoSuchWindowException e1) {
			throw e1;
		} catch (IOException e2) {
			throw e2;
		}
	}
}
