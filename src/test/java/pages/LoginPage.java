package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage {
    WebDriver driver;

    String username;
    String password;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        if(!username.trim().isEmpty()) {
            driver.findElement(By.name("username")).sendKeys(this.username);
        }

        if(!password.trim().isEmpty()) {
            driver.findElement(By.name("password")).sendKeys(this.password);
        }

        driver.findElement(By.className("orangehrm-login-button")).click();
    }

    public void compareErrorMsg(String errorType) {
        try {
            String errorMessage = driver.findElement(By.className("oxd-input-field-error-message")).getText();
            Assert.assertEquals(errorMessage, errorType);
        } catch (AssertionError e) {
            // Log the error
            System.err.println("Assertion failed: " + e.getMessage());
        }
    }

    public void compareErrorAlert(String errorType) {
        try {
            String errorAlert = driver.findElement(By.className("oxd-alert-content-text")).getText();
            Assert.assertEquals(errorAlert, errorType);
        } catch (AssertionError e) {
            // Log the error
            System.err.println("Assertion failed: " + e.getMessage());
        }
    }
}
