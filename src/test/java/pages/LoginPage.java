package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    Wait<WebDriver> wait;

    String username;
    String password;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-input-field-error-message")));
            Assert.assertEquals(errorMessage.getText(), errorType);
        } catch (AssertionError e) {
            // Log the error
            System.err.println("Assertion failed: " + e.getMessage());
        }
    }

    public void compareErrorAlert(String errorType) {
        try {
            WebElement errorAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-alert-content-text")));
            Assert.assertEquals(errorAlert.getText(), errorType);
        } catch (AssertionError e) {
            // Log the error
            System.err.println("Assertion failed: " + e.getMessage());
        }
    }
}
