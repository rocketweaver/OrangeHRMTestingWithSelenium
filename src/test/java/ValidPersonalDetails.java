import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.PersonalDetailsPage;

public class ValidPersonalDetails {
    WebDriver driver;
    Actions action;
    LoginPage loginPage;
    PersonalDetailsPage personalDetailPage;

    @BeforeTest
    public void setup() throws InterruptedException {
        //Basic setup
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        action = new Actions(driver);

        //Instantiate pages object
        loginPage = new LoginPage(driver);

        //Login
        driver.get("http://orangehrm-5.7.test/auth/login");
        Thread.sleep(1000);
        loginPage.login("admin123", "a:@oN8N!E1!4");

        // Go to My Info page
        Thread.sleep(2500);
        driver.findElement(By.xpath("//span[normalize-space()='My Info']")).click();
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

    @Test (priority = 0, description = "Update personal details data with all optional fields.")
    public void submitPersonalDetailsWithOptionalFields() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lewyn");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("Jenaka");
        personalDetailPage.setEmployeeId("0001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDriverLicenseYear(2024);
        personalDetailPage.setDriverLicenseMonth(10);
        personalDetailPage.setDriverLicenseDay(29);
        personalDetailPage.setBirthYear(2002);
        personalDetailPage.setBirthMonth(10);
        personalDetailPage.setBirthDay(12);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
    }

    @Test (priority = 1, description = "Update personal details data with only required fields.")
    public void submitPersonalDetailsWithRequiredOnly() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Bruce");
        personalDetailPage.setLastName("Wayn");
        personalDetailPage.updateRequiredOnlyPersonalDetails();
    }

    @Test(priority = 2, description = "Upload attachment file.")
    public void uploadAttachmentFile() throws InterruptedException {
        Thread.sleep(3000);
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFilePath("/src/test/resources/500-KB.pdf");
        personalDetailPage.setComment("");
        personalDetailPage.addAttachmentFile();
    }

    @Test(priority = 3, description = "Upload attachment file with comment.")
    public void uploadAttachmentFileWithComment() throws InterruptedException {
        Thread.sleep(3000);
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFilePath("/src/test/resources/500-KB.pdf");
        personalDetailPage.setComment("This is my first uploaded file");
        personalDetailPage.addAttachmentFile();
    }

    @Test(priority = 4, description = "Upload profile picture.")
    public void uploadProfilePicture() throws InterruptedException {
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFilePath("/src/test/resources/profile2.jpg");
        personalDetailPage.uploadProfilePicture();
    }
}
