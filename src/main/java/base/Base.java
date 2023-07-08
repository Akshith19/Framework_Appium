package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {
	public static AndroidDriver driver;
	public static AppiumDriverLocalService service;

	public static AppiumDriverLocalService startServer()
	{
		boolean flag=checkIfServerIsRunning(4723);
		if(!flag)
		{
		service=AppiumDriverLocalService.buildDefaultService();
		service.start();
		}
		return service;
	}

	

	public static boolean checkIfServerIsRunning(int port)
	{
		boolean isserverRunning=false;
		ServerSocket serverSocket;
		try
		{
			serverSocket=new ServerSocket(port);
			serverSocket.close();
		}
		catch(IOException e)
		{
			isserverRunning=true;
		}
		finally
		{
			serverSocket=null;
		}
		return isserverRunning;
	}
	public static void startEmulator() throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\test\\resources\\batchFiles\\startEmulator.bat");
		Thread.sleep(10000);
	}
	
	public static AndroidDriver capabilities(String appName, String platform) throws IOException, InterruptedException {
		if(platform.equalsIgnoreCase("local"))
		{
			localCapabilities(appName);
		}
		else if(platform.equalsIgnoreCase("cloud"))
		{
			cloudCapabilities(appName);
		}
		return driver;
		
	}
	
	public static AndroidDriver localCapabilities(String appName) throws IOException, InterruptedException {
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
		service=startServer();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties");

		Properties prop = new Properties();
		prop.load(fis);



		DesiredCapabilities cap= new DesiredCapabilities();

		File appDir = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\apps");

		File app = new File(appDir, (String) prop.get(appName));
		String device=(String) prop.get("device");

		if(device.contains("emulator")) 
		{ 
			startEmulator(); 
		}
		
		cap.setCapability("platformName", "Android");

		cap.setCapability("appium:deviceName",device );

		cap.setCapability("appium:automationName","uiautomator2");

		cap.setCapability("appium:app", app.getAbsolutePath());
		

		cap.setCapability("ignoreHiddenApiPolicyError", true);

		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), cap);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		return driver;


	}

	public static AndroidDriver cloudCapabilities(String appName) throws IOException, InterruptedException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties");

		Properties prop = new Properties();
		prop.load(fis);

		DesiredCapabilities cap= new DesiredCapabilities();
		
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
		
		cap.setCapability("browserstack.user", prop.get("browserstack.username"));
		
		cap.setCapability("browserstack.key", prop.get("browserstack.key"));

		cap.setCapability("app", prop.get("browserstack.app.location"));

		cap.setCapability("device", prop.get("browserstack.device.name"));
		
		cap.setCapability("os_version", prop.get("browserstack.device.os.version"));

		cap.setCapability("project", "Appium");
		
		cap.setCapability("build", "Appium-build");
		
		cap.setCapability("name", "Appium_Test");

		driver = new AndroidDriver(
				new URL("http://hub.browserstack.com/wd/hub"), cap);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		return driver;


	}
	
	
	/*
	 * public static void takeScreenshot(String testcaseName) throws IOException {
	 * File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	 * FileUtils.copyDirectory(src, new
	 * File(System.getProperty("user.dir")+"\\"+testcaseName+".png")); }
	 */
	 

}
