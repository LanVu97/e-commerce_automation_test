import factories.UserFactory;
import models.User;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.*;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class RegisterPageTest extends Base{

    WebDriver driver;
    private RegisterPage registerPage;
    private AccountSuccessPage accountSuccessPage;

    @BeforeMethod
    public void openApplication() throws IOException {

        driver = intializeDriver();
        registerPage = new RegisterPage(driver);
        accountSuccessPage  = new AccountSuccessPage(driver);

    }


    @Test
    public void verifyElementsOnPageTest(){
        registerPage.open();
        Assert.assertEquals("Register Account", registerPage.getFormTitle().getText());
        Assert.assertTrue(registerPage.getFirstNameLabel().isDisplayed());
        Assert.assertTrue(registerPage.getLastNameLabel().isDisplayed());
        Assert.assertTrue(registerPage.getEmailLabel().isDisplayed());
        Assert.assertTrue(registerPage.getTelephoneLabel().isDisplayed());
        Assert.assertTrue(registerPage.getPasswordLabel().isDisplayed());
        Assert.assertTrue(registerPage.getConfirmPasswordLabel().isDisplayed());
        Assert.assertTrue(registerPage.getLoginPageLink().isDisplayed());
        Assert.assertTrue(registerPage.getPrivacyPolicyLink().isDisplayed());
        Assert.assertTrue(registerPage.getRegisterBtn().isDisplayed());
    }
    @Test
    public void verifySubcribeNoIsSelectedDefault(){
        registerPage.open();
        Assert.assertTrue(registerPage.isSubscribeNoSelected());
    }

    @Test
    public void passwordDisplayedEncrypted_when_typePassword() {
        registerPage.open();
        Assert.assertEquals("password", registerPage.getPasswordInput().getAttribute("type"));
    }

    @Test
    public void passwordConfirmDisplayedEncrypted_when_typePassword() {
        registerPage.open();
        Assert.assertEquals( registerPage.getComfirmPasswordInput().getAttribute("type"),"password");
    }

    @Test
    public void typeEmail(){
        registerPage.open();
        Assert.assertEquals(registerPage.getEmailInput().getAttribute("type"), "email");
    }

    //links
    @Test
    public void verifyLoginLink(){
        registerPage.open();
        registerPage.clickLoginPageLink();

        String expectedUrl = "https://tutorialsninja.com/demo/index.php?route=account/login";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl);

        String expectedTitle = "Account Login";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test
    public void verifyPrivacyPolicyLink(){
        registerPage.open();
        registerPage.clickPrivacyPolicyLink();
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));

        //wait for the modal box be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-dialog")));

        WebElement modalContainer = registerPage.getModalContainer();
        System.out.println("Fetching modal body content and asserting it");
        WebElement modalContentBody = modalContainer.findElement(By.xpath(".//div[@class='modal-body']"));
        Assert.assertEquals(modalContentBody.getText(),
                "Privacy Policy");

        //click on accept modal button
        System.out.println("Clicking modal close button");
        WebElement modalCloseButton = modalContainer
                .findElement(By.xpath(".//button[contains(text(),'×')]"));
        modalCloseButton.click();
    }

// happy case
    @Test
    public void userCreatedSuccessfully_when_allRequiredFieldsField_and_clickContinueButton() {
        var user = UserFactory.createDefault();
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }

    @Test
    public void userCreatedSuccessfully_when_allRequiredFieldsField_and_pressContinueButtonWithEnter() {
        var user = UserFactory.createDefault();
        registerPage.open();
        registerPage.register(user, true);
        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }

    // boundary values
    @Test
    public void userCreatedSuccessfully_when_firstName1Character() {
        var user = UserFactory.createDefault();
        user.setFirstName(StringUtils.repeat("A", 1));
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");

    }

    @Test
    public void userCreatedSuccessfully_when_firstName32Characters() {
        var user = UserFactory.createDefault();
        user.setFirstName(StringUtils.repeat("A", 32));
        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }

    @Test
    public void userCreatedSuccessfully_when_lastName1Character() {
        var user = UserFactory.createDefault();
        user.setLastName(StringUtils.repeat("A", 1));

        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }

    @Test
    public void userCreatedSuccessfully_when_lastName32Characters() {
        var user = UserFactory.createDefault();
        user.setLastName(StringUtils.repeat("A", 32));

        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }

    @Test
    public void userCreatedSuccessfully_when_email5Character() {
        var user = UserFactory.createDefault();
        String email = generateRandomString(1) + "@" + generateRandomString(1) + "." + generateRandomString(1);
        System.out.println("email:" + email);
        user.setEmail(email);

        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }


    @Test
    public void userCreatedSuccessfully_when_telephone3Character() {
        var user = UserFactory.createDefault();
        user.setTelephone("123");

        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }


    @Test
    public void userCreatedSuccessfully_when_telephone32Characters() {
        var user = UserFactory.createDefault();
        user.setTelephone(StringUtils.repeat("9", 32));

        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }

    @Test
    public void userCreatedSuccessfully_when_password4Character() {
        var user = UserFactory.createDefault();
        user.setPassword("1234");
        user.setPasswordConfirm("1234");

        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }

    @Test
    public void userCreatedSuccessfully_when_password20Characters() {
        var user = UserFactory.createDefault();
        user.setPassword(StringUtils.repeat("9", 20));
        user.setPasswordConfirm(StringUtils.repeat("9", 20));

        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }

    @Test
    public void userCreatedSuccessfully_when_newsletterSubscribeTrue() {
        var user = UserFactory.createDefault();
        user.setShouldSubscribe(true);

        registerPage.open();
        registerPage.register(user, false);

        Assert.assertEquals(accountSuccessPage.getAccountSuccessHeader(), "Your Account Has Been Created!");
    }


    //validation
    @Test
    public void register_when_firstNameIsEmpty(){
        var user = UserFactory.createDefault();
        user.setFirstName("");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("firstname"), "First Name must be between 1 and 32 characters!");
    }

    @Test
    public void register_when_firstName33Characters(){
        var user = UserFactory.createDefault();
        user.setFirstName(StringUtils.repeat("a", 33));
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("firstname"), "First Name must be between 1 and 32 characters!");
    }

    @Test
    public void register_when_lastNameIsEmpty(){
        var user = UserFactory.createDefault();
        user.setLastName("");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("lastname"), "Last Name must be between 1 and 32 characters!");
    }

    @Test
    public void register_when_lastName33Characters(){
        var user = UserFactory.createDefault();
        user.setLastName(StringUtils.repeat("a", 33));
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("lastname"), "Last Name must be between 1 and 32 characters!");
    }

    @Test
    public void register_when_emailIsEmpty(){
        var user = UserFactory.createDefault();
        user.setEmail("");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("email"), "E-Mail Address does not appear to be valid!");
    }

    public void register_when_incorrectPassword(){
        var user = UserFactory.createDefault();
        user.setEmail("c@c");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("email"), "E-Mail Address does not appear to be valid!");
    }

    @Test
    public void register_when_telephoneIsEmpty(){
        var user = UserFactory.createDefault();
        user.setTelephone("");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("telephone"), "Telephone must be between 3 and 32 characters!");
    }

    @Test
    public void register_when_telephone33Characters(){
        var user = UserFactory.createDefault();
        user.setTelephone(StringUtils.repeat("1", 33));
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("telephone"), "Telephone must be between 3 and 32 characters!");
    }

    @Test
    public void register_when_passwordIsEmpty(){
        var user = UserFactory.createDefault();
        user.setPassword("");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("password"), "Password must be between 4 and 20 characters!");
    }

    @Test
    public void register_when_password3Characters(){
        var user = UserFactory.createDefault();
        user.setPassword(StringUtils.repeat("1", 3));
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("password"), "Password must be between 4 and 20 characters!");
    }

    @Test
    public void register_when_password21Characters(){
        var user = UserFactory.createDefault();
        user.setPassword(StringUtils.repeat("1", 21));
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("password"), "Password must be between 4 and 20 characters!");
    }

    @Test
    public void register_when_mismatchPassword(){
        var user = UserFactory.createDefault();
        user.setPassword("1234");
        user.setPasswordConfirm("12345");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("confirm"), "Password confirmation does not match password!");
    }

    @Test
    public void register_when_confirmEmpty(){
        var user = UserFactory.createDefault();
        user.setPassword("1234");
        user.setPasswordConfirm("");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getErrorMessage("confirm"), "Password confirmation does not match password!");
    }

    @Test
    public void register_when_privacyPolicyNotCheck(){
        var user = UserFactory.createDefault();
        user.setAgreePrivacyPolicy(false);
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getAlertMessage(), "Warning: You must agree to the Privacy Policy!");
    }

    @Test
    public void register_when_accountExisted(){
        var user = UserFactory.createDefault();
        user.setEmail("scarletvu@gmail.com");
        registerPage.open();
        registerPage.register(user, false);
        Assert.assertEquals(registerPage.getAlertMessage(), "Warning: E-Mail Address is already registered!");
    }

    @AfterMethod
    public void closure(){
        driver.close();
    }


    public String generateRandomString(int length) {
        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

        StringBuffer randomString = new StringBuffer(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphanumericCharacters.length());
            char randomChar = alphanumericCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
}
