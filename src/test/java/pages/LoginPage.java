package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        if(!username.trim().isEmpty()) {
            driver.findElement(By.name("username")).sendKeys(username);
        }

        if(!password.trim().isEmpty()) {
            driver.findElement(By.name("password")).sendKeys(password);
        }
        driver.findElement(By.className("orangehrm-login-button")).click();
    }
}
