package com.bae.demosite2.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import constant.Constant;

public class Demosite2Test {
	public static WebDriver driver;

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Downloads/chromedriver.exe");
		driver = new ChromeDriver();
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void methodTest() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get(Constant.URL1);

		FileInputStream file = null;
		try {
			file = new FileInputStream(Constant.Path_DemoStieDDT);
		} catch (FileNotFoundException e) {
		}
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file);
		} catch (IOException e) {
		}

		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFCell username = sheet.getRow(1).getCell(0);
		XSSFCell password = sheet.getRow(1).getCell(1);
		for (int i = 1; i < 5; i++) {
			username = sheet.getRow(i).getCell(0);
			password = sheet.getRow(i).getCell(1);
			WebElement checkElement = driver.findElement(By
					.xpath("/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[3]"));
			checkElement.click();
			WebElement checkElement2 = driver.findElement(By.name("username"));
			checkElement2.sendKeys(username.getStringCellValue());
			WebElement checkElement3 = driver.findElement(By.name("password"));
			checkElement3.sendKeys(password.getStringCellValue());
			checkElement3.submit();

			driver.get(Constant.URL2);
			WebElement checkElement5 = driver.findElement(By.name("username"));
			checkElement5.sendKeys(username.getStringCellValue());
			WebElement checkElement6 = driver.findElement(By.name("password"));
			checkElement6.sendKeys(password.getStringCellValue());
			checkElement6.submit();
			// Thread.sleep(2000);

			WebElement checkElement7 = driver
					.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b"));
			assertTrue(checkElement7.isDisplayed());

			if (checkElement7.isDisplayed()) {
				XSSFCell result = sheet.getRow(i).createCell(2);
				result.setCellValue("true");
			}

			try {
				FileOutputStream out = new FileOutputStream(new File(Constant.Path_DemoStieDDT));
				workbook.write(out);
				out.close();
				System.out.println("File saved without issue");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Thread.sleep(3000);
	}

}
