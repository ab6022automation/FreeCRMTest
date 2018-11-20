package com.crm.qa.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class HomePageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	public ExtentTest extentLogger;
	public ExtentReports extentReports;
	
	public HomePageTest() {
		super();
	}

	//test cases should be separated -- independent with each other
	//before each test case -- launch the browser and login
	//@test -- execute test case
	//after each test case -- close the browser
	
	@BeforeTest
	public void setExtentProperty() {
		extentReports=setExtent();
	}
	
	@AfterTest
	public void endExtent() {
		endReport();
	}
	
	@BeforeMethod
	public void setUp() {
		initialization();
		testUtil = new TestUtil();
		contactsPage = new ContactsPage();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@Test(priority=1)
	public void verifyHomePageTitleTest(){
		extentLogger=extentReports.startTest("verifyHomePageTitleTest");
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, "CRMPROmmmm","Home page title not matched");
	}
	
	@Test(priority=2)
	public void verifyUserNameTest(){
		extentLogger=extentReports.startTest("verifyUserNameTest");
		
		testUtil.switchToFrame();
		Assert.assertTrue(homePage.verifyCorrectUserName());
	}
	
	@Test(priority=3)
	public void verifyContactsLinkTest(){
		extentLogger=extentReports.startTest("verifyContactsLinkTest");
		testUtil.switchToFrame();
		contactsPage = homePage.clickOnContactsLink();
	}
	
	
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException{
		
if(result.getStatus()==ITestResult.FAILURE) {
			
			extentLogger.log(LogStatus.FAIL, "Failed TestCase: "+result.getName());
			String screenshotPath=TestUtil.getScreenshot(driver, result.getName());
			extentLogger.log(LogStatus.FAIL, extentLogger.addScreenCapture(screenshotPath));
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			extentLogger.log(LogStatus.PASS, "Passed TestCase: "+result.getName());
		}
		else {
			extentLogger.log(LogStatus.SKIP, "Skipped TestCase: "+result.getName());
		}
		
		
		driver.quit();
	}
	
	

}
