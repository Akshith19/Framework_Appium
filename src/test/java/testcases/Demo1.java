package testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import base.Base;
import io.appium.java_client.android.AndroidDriver;

public class Demo1 extends Base {

	
	@Test
	public void demo() throws IOException, InterruptedException {
	
		AndroidDriver driver = capabilities("demoapp", "local");
		
		driver.findElement(By.xpath("//android.widget.TextView[@text='Preference']")).click();
		
		driver.findElement(By.xpath("//android.widget.TextView[@text='3. Preference dependencies']")).click();
		
		driver.findElement(By.xpath("//android.widget.CheckBox[@resource-id='android:id/checkbox']")).click();
		
		driver.findElement(By.xpath("//android.widget.TextView[@text='WiFi settings']")).click();
	
		driver.findElement(By.xpath("//android.widget.EditText[@resource-id='android:id/edit']")).sendKeys("Akshith");
		
		driver.findElement(By.xpath("//android.widget.Button[@text='OK']")).click();
		
		

	}
	@AfterTest
	public void tearDown()
	{
		
		  if(service!=null) { service.stop(); }
		 
		
	}

}
