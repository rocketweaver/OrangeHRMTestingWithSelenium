import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;

public class InvalidLogin {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeTest
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        driver.get("http://orangehrm-5.7.test/auth/login");
    }

    @BeforeMethod
    public void waitToSleep() throws InterruptedException {
        Thread.sleep(1000);
    }

    @AfterMethod
    public void refresh() {
        driver.navigate().refresh();
    }

    @AfterTest
    public void stop() {
        driver.quit();
    }

    @Test (priority = 0)
    public void loginWithInvalidUsername() throws InterruptedException {
        loginPage.login("admin__", "a:@oN8N!E1!4");
        Thread.sleep(1000);
        String errorMsg = driver.findElement(By.className("oxd-alert-content-text")).getText();
        Assert.assertEquals(errorMsg, "Invalid credentials");
    }

    @Test (priority = 1)
    public void loginWithInvalidPassword() throws InterruptedException {
        loginPage.login("admin123", "password");
        Thread.sleep(1000);
        String errorMsg = driver.findElement(By.className("oxd-alert-content-text")).getText();
        Assert.assertEquals(errorMsg, "Invalid credentials");
    }

    @Test (priority = 2)
    public void loginWithEmptyUsername() throws InterruptedException {
        loginPage.login("", "a:@oN8N!E1!4");
        Thread.sleep(1000);
        String errorMsg = driver.findElement(By.className("oxd-input-field-error-message")).getText();
        Assert.assertEquals(errorMsg, "Required");
    }

    @Test (priority = 3)
    public void loginWithEmptyPassword() throws InterruptedException {
        loginPage.login("admin123", "");
        Thread.sleep(1000);
        String errorMsg = driver.findElement(By.className("oxd-input-field-error-message")).getText();
        Assert.assertEquals(errorMsg, "Required");
    }
}
