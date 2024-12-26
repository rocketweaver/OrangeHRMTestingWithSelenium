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
        //Basic setup
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // Go to login page
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
        loginPage.setUsername("admin__");
        loginPage.setPassword("a:@oN8N!E1!4");
        loginPage.login();
        Thread.sleep(1000);
        loginPage.compareErrorAlert("Invalid credentials");
    }

    @Test (priority = 1)
    public void loginWithInvalidPassword() throws InterruptedException {
        loginPage.setUsername("admin123");
        loginPage.setPassword("123password");
        loginPage.login();
        Thread.sleep(1000);
        loginPage.compareErrorAlert("Invalid credentials");
    }

    @Test (priority = 2)
    public void loginWithEmptyUsername() throws InterruptedException {
        loginPage.setUsername("");
        loginPage.setPassword("a:@oN8N!E1!4");
        loginPage.login();
        Thread.sleep(1000);
        loginPage.compareErrorMsg("Required");
    }

    @Test (priority = 3)
    public void loginWithEmptyPassword() throws InterruptedException {
        loginPage.setUsername("admin123");
        loginPage.setPassword("");
        loginPage.login();
        Thread.sleep(1000);
        loginPage.compareErrorMsg("Required");
    }
}
