import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.LoginPage;

import java.time.Duration;

public class InvalidLogin {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeClass
    public void setup(){
        //Basic setup
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Go to login page
        loginPage = new LoginPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @BeforeMethod
    public void loadPage(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void refresh() {
        driver.navigate().refresh();
    }

    @AfterClass
    public void stop() {
        driver.quit();
    }

    @Test (priority = 0)
    public void loginWithInvalidUsername() throws InterruptedException {
        loginPage.setUsername("admin__");
        loginPage.setPassword("a:@oN8N!E1!4");
        loginPage.login();
        loginPage.compareErrorAlert("Invalid credentials");
    }

    @Test (priority = 1)
    public void loginWithInvalidPassword() throws InterruptedException {
        loginPage.setUsername("admin123");
        loginPage.setPassword("123password");
        loginPage.login();
        loginPage.compareErrorAlert("Invalid credentials");
    }

    @Test (priority = 2)
    public void loginWithEmptyUsername() throws InterruptedException {
        loginPage.setUsername("");
        loginPage.setPassword("a:@oN8N!E1!4");
        loginPage.login();
        loginPage.compareErrorMsg("Required");
    }

    @Test (priority = 3)
    public void loginWithEmptyPassword() throws InterruptedException {
        loginPage.setUsername("admin123");
        loginPage.setPassword("");
        loginPage.login();
        loginPage.compareErrorMsg("Required");
    }
}
