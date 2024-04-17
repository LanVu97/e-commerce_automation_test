package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(linkText = "My Account")
    WebElement myAccountDropdown;

    @FindBy(linkText = "Login")
    WebElement loginOption;

    @FindBy(linkText = "Register")
    WebElement registerOption;

    @FindBy(linkText = "Qafox.com")
    private WebElement myStoreLogo;

    @FindBy(name="search")
    private WebElement searchProductBox;

    @FindBy(xpath = "//span[@class=\"input-group-btn\"]/button")
    private WebElement searchButton;

    public void clickOnMyAccountDropdown(){
        myAccountDropdown.click();
    }


    public LoginPage clickOnSignIn() {
        loginOption.click();
        return new LoginPage(this.driver);
    }

    public boolean validateLogo() {
        return myStoreLogo.isDisplayed();
    }

    public String getMyStoreTitle() {
        String myStoreTitle= this.driver.getTitle();
        return myStoreTitle;
    }

    public SearchResultPage searchProduct(String productName){
        searchProductBox.clear();
        searchProductBox.sendKeys(productName);
        searchButton.click();

        return new SearchResultPage(this.driver);
    }
}
