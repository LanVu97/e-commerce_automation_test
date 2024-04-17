import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.RegisterPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginTest extends Base{

    WebDriver driver;
    @BeforeClass
    public void openApplication() throws IOException {

        driver = intializeDriver();
        driver.get(prop.getProperty("url"));

    }

    @Test(dataProvider = "getLoginData")
    public void login(String email, String password, Boolean isValidUser) {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnMyAccountDropdown();
        LoginPage loginPage = homePage.clickOnSignIn();

        Assert.assertTrue(loginPage.isDisPlayLoginBreadcrumb());
        Assert.assertEquals(loginPage.getLoginBreadcrumb(), "Login");
        loginPage.login(email, password);

      if(!isValidUser){
        Assert.assertEquals(loginPage.getErrorMessageText(), "Warning: No match for E-Mail Address and/or Password.");
      }else{
          AccountPage accountPage = new AccountPage(driver);
          Assert.assertEquals(accountPage.getPageTitle().getText(), "My Account");
      }
    }

    @Test
    public void verifyCreateAccountPageTest() {
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage =  loginPage.createNewAccount();
        Assert.assertTrue(registerPage.getFormTitle().isDisplayed());
    }

    @AfterClass
    public void closure(){
        driver.quit();
    }

    @DataProvider
    public Iterator<Object[]> getLoginData(){

        List<Object[]> loginData = new ArrayList<>();
        loginData.add(new Object[] {"scarletvu@gmail.com","Password",false});
        loginData.add(new Object[] {"scarletvu@gmail.com","",false});
        loginData.add(new Object[] {"scarletvu@gmail.com","12345",true});
        return loginData.iterator();
    }

}
