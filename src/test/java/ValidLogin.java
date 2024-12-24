import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class ValidLogin {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        driver.get("http://orangehrm-5.7.test/auth/login");
        Thread.sleep(1000);
    }

    @AfterMethod
    public void stop() {
        driver.quit();
    }

    @Test (priority = 0)
    public void loginWithValidCredential() {
        loginPage.login("admin123", "a:@oN8N!E1!4");
    }

    @Test (priority = 1)
    public void redirectToForgotPasswordPage() {
        driver.findElement(By.className("orangehrm-login-forgot-header")).click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, "http://orangehrm-5.7.test/auth/login");
    }
}
