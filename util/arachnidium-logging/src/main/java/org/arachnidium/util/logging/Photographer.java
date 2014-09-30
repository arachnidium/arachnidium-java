package org.arachnidium.util.logging;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Takes screenshots by {@link WebDriver} instance
 */
public final class Photographer {
	private static Photographer get() {
		Photographer photographer = photographerThreadLocal.get();
		if (photographer == null) {
			photographer = new Photographer();
			photographerThreadLocal.set(photographer);
		}
		return photographer;
	}

	/**
	 * Resets path to the default folder where screenshots are collected
	 * 
	 * @param pathToFolder An absolute or relative path 
	 * to the default folder where screenshots are collected
	 */
	public static synchronized void setCommonOutputFolder(String pathToFolder) {
		pictureFolderNameByDefault = pathToFolder;
	}

	/**
	 *  takes pictures of full browser windows
	 * @param driver an instance of {@link WebDriver} 
	 * @param LogLevel A {@link Level} of the log message. To this message file will be 
	 * attached
	 * @param comment Narrative message text
	 */
	public static void takeAPictureForLog(WebDriver driver,
			eAvailableLevels LogLevel, String comment) {
		Photographer photographer = get();
		try {
			BufferedImage imageForLog = photographer.takeAPicture(driver);
			photographer.makeFileForLog(imageForLog, LogLevel, comment);
		} catch (IOException e) {
			Log.warning("Can't post a picture to log! " + e.getMessage());
			Log.log(LogLevel, comment);
		} catch (ClassCastException | UnsupportedOperationException e) {
			Log.debug(
					"Operation is not supported! Take a screenshot. "
					+ e.getMessage(), e);
			Log.log(LogLevel, comment);
		}
	}

	/**
	 * Creates a log message with the FINE {@link Level} and
	 * attached screenshot
	 * @param driver an instance of {@link WebDriver} 
	 * @param comment The narrative comment to the picture
	 */
	public static void takeAPictureOfAFine(WebDriver driver, String comment) {
		takeAPictureForLog(driver, eAvailableLevels.FINE, comment);
	}

	/**
	 * Creates a log message with the INFO {@link Level} and
	 * attached screenshot
	 * @param driver an instance of {@link WebDriver} 
	 * @param comment The narrative comment to the picture
	 */	
	public static void takeAPictureOfAnInfo(WebDriver driver, String comment) {
		takeAPictureForLog(driver, eAvailableLevels.INFO, comment);
	}
	
	
	/**
	 * Creates a log message with the SEVERE {@link Level} and
	 * attached screenshot
	 * @param driver an instance of {@link WebDriver} 
	 * @param comment The narrative comment to the picture
	 */	
	public static void takeAPictureOfASevere(WebDriver driver, String comment) {
		takeAPictureForLog(driver, eAvailableLevels.SEVERE, comment);
	}

	/**
	 * Creates a log message with the WARNING {@link Level} and
	 * attached screenshot
	 * @param driver an instance of {@link WebDriver} 
	 * @param comment The narrative comment to the picture
	 */		
	public static void takeAPictureOfAWarning(WebDriver driver, String comment) {
		takeAPictureForLog(driver, eAvailableLevels.WARN, comment);
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
		byte[] bytes = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.BYTES);
		BufferedImage buffer = getBufferedImage(bytes);
		return buffer;
	}

	// applies images
	private synchronized void makeFileForLog(BufferedImage imageForLog,
			eAvailableLevels LogLevel, String comment) {

		String FolderPath = folder;

		File ImgFolder = new File(FolderPath);
		ImgFolder.mkdirs();

		File picForLog = new File(FolderPath + pictureNameByDefault + '_'
				+ UUID.randomUUID().toString() + "." + format);
		try {
			ImageIO.write(imageForLog, format, picForLog);
			Log.log(LogLevel, comment, picForLog);
		} catch (IOException e) {
			Log.warning("Can't take a screenshot! " + e.getMessage());
		}
	}

	// takes pictures and makes buffered images
	private synchronized BufferedImage takeAPicture(WebDriver driver)
			throws IOException, 
			UnsupportedOperationException {
		try {
			return getImageFromDriver(driver);
		} catch (UnsupportedOperationException e) {
			throw e;
		} catch (IOException e2) {
			throw e2;
		}
	}
}
