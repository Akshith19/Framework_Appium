package testcases;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import base.Base;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Demo1 extends Base {

	
	@Test
	public void demo() throws IOException, InterruptedException {
	
		AndroidDriver<AndroidElement> driver = capabilities("demoapp", "cloud");
	
		
		driver.findElementByXPath("//android.widget.TextView[@text='Preference']").click();
		
		driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
		
		driver.findElementByXPath("//android.widget.CheckBox[@resource-id='android:id/checkbox']").click();
		
		driver.findElementByXPath("//android.widget.TextView[@text='WiFi settings']").click();
	
		driver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/edit']").sendKeys("Akshith");
		
		driver.findElementByXPath("//android.widget.Button[@text='OK']").click();
		
		

	}
	@AfterTest
	public void tearDown()
	{
		
		  if(service!=null) { service.stop(); }
		 
		
	}

}
