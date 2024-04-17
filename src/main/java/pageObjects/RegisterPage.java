package pageObjects;


import models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[text()='Register Account']")
    private WebElement formTitle;

    @FindBy(css = "#account-register div.alert")
    private WebElement errorMessage;

    @FindBy(css = "#account-register div.alert-dismissible")
    private WebElement alertMessage;

    @FindBy(linkText = "login page")
    private WebElement loginPageLink;

    @FindBy(name = "firstname")
    private WebElement firstNameInput;

    @FindBy(xpath = "//label[@for='input-firstname']")
    private WebElement firstNameLabel;

    @FindBy(name = "lastname")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[@for='input-lastname']")
    private WebElement lastNameLabel;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(xpath = "//label[@for='input-email']")
    private WebElement emailLabel;

    @FindBy(name = "telephone")
    private WebElement telephoneInput;

    @FindBy(xpath = "//label[@for='input-telephone']")
    private WebElement telephoneLabel;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//label[@for='input-password']")
    private WebElement passwordLabel;

    @FindBy(name = "confirm")
    private WebElement comfirmPasswordInput;

    @FindBy(xpath = "//label[@for='input-confirm']")
    private WebElement confirmPasswordLabel;

    @FindBy(xpath = "//*[@id='content']/form/fieldset[3]/div/label")
    private WebElement subscribeLabel;

    @FindBy(xpath = "//input[@name='newsletter' and @value=1]")
    private WebElement yesSubscribe;

    @FindBy(xpath = "//input[@name='newsletter' and @value=0]")
    private WebElement noSubscribe;

    @FindBy(name = "agree")
    private WebElement policyCheckbox;

    @FindBy(linkText = "Privacy Policy")
    private WebElement privacyPolicyLink;

    @FindBy(xpath = "//input[@type=\"submit\" and @value=\"Continue\"]")
    private WebElement registerBtn;

    @FindBy(className = "modal-dialog")
    private WebElement modalContainer;

    public void open() {
        driver.navigate().to("https://tutorialsninja.com/demo/index.php?route=account/register");
    }

    public void clickPrivacyPolicyLink() {
        privacyPolicyLink.click();
    }

    public WebElement getFormTitle() {
        return formTitle;
    }

    public WebElement getRegisterBtn() {
        return registerBtn;
    }

    public WebElement getLoginPageLink() {
        return loginPageLink;
    }

    public WebElement getPrivacyPolicyLink() {
        return privacyPolicyLink;
    }

    public WebElement getFirstNameLabel() {
        return firstNameLabel;
    }

    public WebElement getLastNameLabel() {
        return lastNameLabel;
    }

    public WebElement getEmailLabel() {
        return emailLabel;
    }

    public WebElement getTelephoneLabel() {
        return telephoneLabel;
    }

    public WebElement getPasswordLabel() {
        return passwordLabel;
    }

    public WebElement getConfirmPasswordLabel() {
        return confirmPasswordLabel;
    }

    public WebElement getSubscribeLabel() {
        return subscribeLabel;
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public void clickLoginPageLink() {
        loginPageLink.click();
    }

    public WebElement getModalContainer() {
        return modalContainer;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getComfirmPasswordInput() {
        return comfirmPasswordInput;
    }

    public String getErrorMessage(String inputName){
        String xpathLocator = String.format("//input[@name='%s']/following-sibling::div", inputName);
       return driver.findElement(By.xpath(xpathLocator)).getText();
    }

    public String getAlertMessage(){

        return alertMessage.getText();
    }

    public Boolean isSubscribeNoSelected(){
        return noSubscribe.isSelected();
    }

    public void register( User user,
                          Boolean useEnter){

        firstNameInput.sendKeys(user.getFirstName());
        lastNameInput.sendKeys(user.getLastName());
        emailInput.sendKeys(user.getEmail());
        telephoneInput.sendKeys(user.getTelephone());
        passwordInput.sendKeys(user.getPassword());
        comfirmPasswordInput.sendKeys(user.getPasswordConfirm());

        if(user.getShouldSubscribe()){
            yesSubscribe.click();
        }

        if(user.getAgreePrivacyPolicy()){
            policyCheckbox.click();
        }

        if (useEnter) {
            registerBtn.sendKeys(Keys.ENTER);
        } else {
            registerBtn.click();
        }

    }

}
