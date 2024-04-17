package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.swing.*;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//ul[@class=\"breadcrumb\"]/li[last()]")
    private WebElement loginBreadcrumb;

    @FindBy(id="input-email")
    private WebElement emailField;

    @FindBy(id="input-password")
    private WebElement passwordField;

    @FindBy(css="input[value='Login']")
    private WebElement loginButton;

    @FindBy(css = "#account-login div.alert")
    private WebElement errorMessageText;

    @FindBy(xpath = "//a[contains(text(),'Continue')]")
    private WebElement createNewAccountBtn;

    public AccountPage login(String email, String pswd)  {
        emailField.sendKeys(email);
        passwordField.sendKeys(pswd);
        loginButton.click();
        AccountPage accountPage=new AccountPage(this.driver);
        return accountPage;
    }


    public RegisterPage createNewAccount() {
        createNewAccountBtn.click();
        return new RegisterPage(this.driver);
    }


    public String getErrorMessageText() {
       return errorMessageText.getText();
    }

    public String getLoginBreadcrumb() {
        return loginBreadcrumb.getText();
    }

    public boolean isDisPlayLoginBreadcrumb(){
        return loginBreadcrumb.isDisplayed();
    }
}
