package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumTest {
	public WebDriver driver;
	@BeforeTest
	public void setup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.google.co.in/");
		System.out.println("starting testing");
	}
	@AfterTest
	public void afterTest() {
		System.out.println("running testcases");
		driver.close();
	}
  @Test
  public void f() {
	  System.out.println("testcase suesful");
  }
}
