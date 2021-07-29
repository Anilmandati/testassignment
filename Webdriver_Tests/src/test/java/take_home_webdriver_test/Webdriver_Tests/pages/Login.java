package take_home_webdriver_test.Webdriver_Tests.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public class Login{
  WebDriver driver;
  
  @FindBy(id="user-name")
  public WebElement username;
  
  @FindBy(id="password")
  public WebElement password;
  
  @FindBy(id="login-button")
  public WebElement loginBtn;
  
  @FindBy(xpath=".//*[text()='Products' and @class='title']")
  public WebElement loggedInPageTitle;

  @FindBy(xpath=".//*[@data-test='error']")
  public WebElement fldErrMsg;
  
  @FindBy(xpath=" .//*[@class='bot_column']")
  public WebElement robotImg;
  
 
  public Login(WebDriver driver){
      this.driver = driver;
      PageFactory.initElements(driver, this);
  }
 
  public void setValueInUsernameTxtBox(String usrNm) {
	  username.sendKeys(usrNm);
	  }

  public void setValueInPasswordTxtBox(String pswrd) {
	  password.sendKeys(pswrd);
	  }
  
  public void clickOnLoginButton() {
	  loginBtn.click();
	  }
  
  public String getErrorMessageText() {
	  return fldErrMsg.getText().trim();
	  }
  
  
}
