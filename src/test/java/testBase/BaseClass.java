package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {
//	public static WebDriver driver;
	public WebDriver driver;
	public Logger logger;
	public Properties properties;

	@BeforeClass(groups = { "Regression", "Master", "Sanity", "datadriven" })
	@Parameters({ "os", "browser" })
	public void setup(@Optional("windows") String os, @Optional("chrome") String browser) throws IOException {

		FileReader fileReader = new FileReader("./src//test//resources//config.properties");
		properties = new Properties();
		properties.load(fileReader);

		logger = LogManager.getLogger(this.getClass());

		if (properties.getProperty("execution_env").equalsIgnoreCase("remote")) {
			String hubURL = "http://localhost:4444/wd/hub";
			DesiredCapabilities cap = new DesiredCapabilities();

			// OS
			if (os.equalsIgnoreCase("windows")) {
				cap.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				cap.setPlatform(Platform.MAC);
			} else {
				System.out.println("Unsupported platform");
				return;
			}

			// Browser
			switch (browser.toLowerCase()) {
			case "chrome":
				cap.setBrowserName("chrome");
				break;
			case "edge":
				cap.setBrowserName("MicrosoftEdge");
				break;
			default:
				System.out.println("Unsupported browser");
				return;
			}

			driver = new RemoteWebDriver(new URL(hubURL), cap);

		}

		if (properties.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (browser.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				System.out.println("Invalid Browser name: " + browser);
				return;
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(properties.getProperty("URL"));
		driver.manage().window().maximize();
	}

	@AfterClass(groups = { "Regression", "Master", "Sanity", "datadriven" })
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').get();
		return generator.generate(5);
	}

	public String randomNumber() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', '9').get();
		return generator.generate(10);
	}

	public String captureScreen(String name) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + name + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}

}
