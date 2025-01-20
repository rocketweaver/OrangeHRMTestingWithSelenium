import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.PersonalDetailsPage;

import java.time.Duration;

public class ValidPersonalDetails {
    WebDriver driver;
    Wait<WebDriver> wait;
    Actions action;
    LoginPage loginPage;
    PersonalDetailsPage personalDetailPage;

    @BeforeClass
    public void setup() throws InterruptedException {
        //Basic setup
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        action = new Actions(driver);

        //Instantiate pages object
        loginPage = new LoginPage(driver);

        //Login
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        loginPage.setUsername("Admin");
        loginPage.setPassword("admin123");
        loginPage.login();

        // Go to My Info page
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement myInfoLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("My Info")));
        myInfoLink.click();
    }

    @BeforeMethod
    public void loadPage() {
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

    @Test (priority = 0, description = "Update personal details data with all optional fields.")
    public void submitPersonalDetailsWithOptionalFields() {
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lisa");
        personalDetailPage.setMiddleName("Melisa");
        personalDetailPage.setLastName("Alisa");
        personalDetailPage.setEmployeeId("0003");
        personalDetailPage.setOtherId("0003");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDriverLicenseDay(5);
        personalDetailPage.setDriverLicenseYear("2024");
        personalDetailPage.setDriverLicenseMonth("2");
        personalDetailPage.setNationality("Afghan");
        personalDetailPage.setMaritalStatus("Married");
        personalDetailPage.setBirthDay(11);
        personalDetailPage.setBirthYear("2002");
        personalDetailPage.setBirthMonth("2");
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails(true);
    }

    @Test (priority = 1, description = "Update personal details data with only required fields.")
    public void submitPersonalDetailsWithRequiredOnly() {
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Bruce");
        personalDetailPage.setLastName("Wayn");
        personalDetailPage.updateRequiredOnlyPersonalDetails();
    }

    @Test(priority = 2, description = "Upload attachment file.")
    public void uploadAttachmentFile() {
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFilePath("/src/test/resources/500-KB.pdf");
        personalDetailPage.setComment("");
        personalDetailPage.addAttachmentFile(true);
    }

    @Test(priority = 3, description = "Upload attachment file with comment.")
    public void uploadAttachmentFileWithComment() {
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFilePath("/src/test/resources/500-KB.pdf");
        personalDetailPage.setComment("This is my first uploaded file");
        personalDetailPage.addAttachmentFile(true);
    }

    @Test(priority = 4, description = "Upload profile picture.")
    public void uploadProfilePicture() {
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFilePath("/src/test/resources/profile2.jpg");
        personalDetailPage.uploadProfilePicture(true);
    }
}
