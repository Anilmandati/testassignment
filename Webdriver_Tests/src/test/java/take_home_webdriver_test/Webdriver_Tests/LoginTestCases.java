package take_home_webdriver_test.Webdriver_Tests;

import java.util.ArrayList;
import java.util.NoSuchElementException;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import take_home_webdriver_test.Webdriver_Tests.pages.Login;
import take_home_webdriver_test.Webdriver_Tests.pages.Utilities;

public class LoginTestCases{

  Login login;
  WebDriver driver;
  Utilities utl;
  
  @BeforeTest
  public void initTestSuite(){
	  utl=new Utilities();
	  String dir = System.getProperty("user.dir");
	  if (utl.browser.equalsIgnoreCase("chrome")){
		  System. setProperty("webdriver.chrome.driver",dir+"\\src\\test\\resources\\chromedriver.exe");
		  //version 91
		  driver = new ChromeDriver();
		  
	  }
	  else if  (utl.browser.equalsIgnoreCase("firefox")){
		  System. setProperty("webdriver.gecko.driver",dir+"\\src\\test\\resources\\geckodriver.exe");
		  driver = new FirefoxDriver();
		  
	  }

//	 System.setProperty("webdriver.ie.driver", "src\\test\\resources\\IEDriverServer.exe");
//    driver = new ChromeDriver();
    driver.manage().window().maximize();
	login = PageFactory.initElements(driver, Login.class);
  }

  @DataProvider(name="credentials_dataset")
  public Object[][] credentials(){
  return new Object[][] 
	    	{
	            { "standard_user","secret_sauce"},
	            { "locked_out_user","secret_sauce"},
	            { "problem_user","secret_sauce"},
	            {"performance_glitch_user","secret_sauce"},
	            { "standard_user",""},
	            { "locked_out_user","gfv"},
	            { "",""},
	            {"","secret_sauce"},
	            {"fghd","secret_sauce"}
	        };
  }
  
  @DataProvider(name="invalidcredentials_error_dataset")
  public Object[][] invalidCredetialsForErrorMessages(){
  return new Object[][] 
	    	{
	            { "standard_user",""},
	            { "locked_out_user","gfv"},
	            { "",""},
	            {"","secret_sauce"},
	            {"fghd","secret_sauce"}
	        };
  }
  

  @Test
  public void verifyLoginPageUI() {
	driver.get(utl.url);
	Assert.assertEquals((driver.getTitle()), "Swag Labs");
	WebDriverWait wait = new WebDriverWait(driver,5);
	wait.until(ExpectedConditions.elementToBeClickable(login.username));
	wait.until(ExpectedConditions.elementToBeClickable(login.password));
	wait.until(ExpectedConditions.visibilityOf(login.loginBtn));
	wait.until(ExpectedConditions.visibilityOf(login.robotImg));
	
  }
  
  
  @Test(dataProvider="credentials_dataset")
  public void verifyLoginForValidAndInvalidCredentials(String username,String pswrd) {
	  driver.get(utl.url);
	  login.setValueInUsernameTxtBox(username);
	  login.setValueInPasswordTxtBox(pswrd);
	  login.clickOnLoginButton();
	  ArrayList<String> validUsrnameList =new ArrayList<>();
	  validUsrnameList.add("standard_user");
	  validUsrnameList.add("problem_user");
	  validUsrnameList.add("performance_glitch_user");
	  String validPswrd="secret_sauce";

	  WebDriverWait wait = new WebDriverWait(driver,10);
	  
	  if (validUsrnameList.contains(username) && validPswrd.equals(pswrd)){
		  try {
			  wait.until(ExpectedConditions.visibilityOf(login.loggedInPageTitle));
		  }
		  catch(NoSuchElementException e) {
			  Assert.fail("Login is not successful for valid Credentials");
		  }
	  }
	  else {
		  try {
			  wait.until(ExpectedConditions.visibilityOf(login.loginBtn));
		  }
		  catch(NoSuchElementException e) {
			  Assert.fail("Login is successful for invalid credentials");
		  }
	  }
	  
  }
  
  @Test
  public void verifyLockedOutUser()
  {
	  driver.get(utl.url);
	  login.setValueInUsernameTxtBox("locked_out_user");
	  login.setValueInPasswordTxtBox("secret_sauce");
	  login.clickOnLoginButton();
	  if((login.fldErrMsg).isDisplayed())
	  {
		  String erMsg="";
		  try {
			  erMsg=login.getErrorMessageText();
		  }
		  catch(NoSuchElementException e) {
			  Assert.fail("Error Message Element is not displayed");
		  }
		  
		  Assert.assertEquals(erMsg, "Epic sadface: Sorry, this user has been locked out.");
	  }
  }
  
  @Test(dataProvider="invalidcredentials_error_dataset")
  public void verifyValidationMessagesForInvalidCredentials(String username,String pswrd) {
	  driver.get(utl.url);
	  login.setValueInUsernameTxtBox(username);
	  login.setValueInPasswordTxtBox(pswrd);
	  login.clickOnLoginButton();
	  String erMsg="";
	  try {
		  erMsg=login.getErrorMessageText();
	  }
	  catch(NoSuchElementException e) {
		  Assert.fail("Error Message Element is not displayed");
	  }
	  if (username.isEmpty()) {
		  Assert.assertEquals(login.getErrorMessageText(),"Epic sadface: Username is required");
	  }
	  else if (pswrd.isEmpty()) 
		  Assert.assertEquals(login.getErrorMessageText(),"Epic sadface: Password is required");
	  else
		  Assert.assertEquals(login.getErrorMessageText(),"Epic sadface: Username and password do not match any user in this service");
  }	
 
  
  @AfterTest()
  public void tearDown() {
    driver.quit();
  }
  
}
