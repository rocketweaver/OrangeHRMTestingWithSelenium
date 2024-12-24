import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.PersonalDetailsPage;

public class InvalidPersonalDetails {
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

    @Test (priority = 0, description = "Verify that updating personal details fail when First Name is empty.")
    public void leaveFirstNameEmpty() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("Jenaka");
        personalDetailPage.setEmployeeId("0001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Required");
    }

    @Test (priority = 2, description = "Verify that updating personal details fail when Last Name is empty.")
    public void leaveLastNameEmpty() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lisa");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("");
        personalDetailPage.setEmployeeId("0001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Required");
    }

    @Test (priority = 3, description = "Verify that updating personal details fail when First Name is exceeding maximum characters (30 chars)")
    public void firstNameExceedingLimit() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("LisaLisaLisaLisaLisaLisaLisaLisaLisaLisa");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("Jenaka");
        personalDetailPage.setEmployeeId("0001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Should not exceed 30 characters");
    }

    @Test (priority = 4, description = "Verify that updating personal details fail when Middle Name is exceeding maximum characters (30 chars)")
    public void middleNameExceedingLimit() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lisa");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("JenakaJenakaJenakaJenakaJenakaJenakaJenakaJenakaJenakaJenaka");
        personalDetailPage.setEmployeeId("0001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Should not exceed 30 characters");
    }

    @Test (priority = 4, description = "Verify that updating personal details fail when Middle Name is exceeding maximum characters (30 chars)")
    public void lastNameExceedingLimit() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lisa");
        personalDetailPage.setMiddleName("PutriPutriPutriPutriPutriPutriPutriPutriPutriPutri");
        personalDetailPage.setLastName("Jenaka");
        personalDetailPage.setEmployeeId("0001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Should not exceed 30 characters");
    }

    @Test (priority = 5, description = "Verify that updating personal details fail when First Name is using invalid format")
    public void firstNameWithInvalidFormat() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lis44");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("Jenaka");
        personalDetailPage.setEmployeeId("0001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Invalid characters are not allowed");
    }

    @Test (priority = 6, description = "Verify that updating personal details fail when Last Name is using invalid format")
    public void lastNameWithInvalidFormat() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lisa");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("Jenak44");
        personalDetailPage.setEmployeeId("0001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Invalid characters are not allowed");
    }

    @Test (priority = 7, description = "Verify that updating personal details fail when Employee Id is exceeding maximum characters (10 chars)")
    public void employeeIdExceedingLimit() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lisa");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("Jenaka");
        personalDetailPage.setEmployeeId("00010001000100010001000100010001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Should not exceed 10 characters");
    }

    @Test (priority = 8, description = "Verify that updating personal details fail when Other Id is exceeding maximum characters (30 chars)")
    public void otherIdExceedingLimit() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lisa");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("Jenaka");
        personalDetailPage.setEmployeeId("001");
        personalDetailPage.setOtherId("000100010001000100010001000100010001000100010001000100010001000100010001000100010001000100010001");
        personalDetailPage.setDriverLicense("H4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Should not exceed 30 characters");
    }

    @Test (priority = 9, description = "Verify that updating personal details fail when Driver License is exceeding maximum characters (30 chars)")
    public void driverLicenseExceedingLimit() throws InterruptedException {
        Thread.sleep(3000);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
        personalDetailPage = new PersonalDetailsPage(driver);
        personalDetailPage.setFirstName("Lisa");
        personalDetailPage.setMiddleName("Putri");
        personalDetailPage.setLastName("Jenaka");
        personalDetailPage.setEmployeeId("001");
        personalDetailPage.setOtherId("0002");
        personalDetailPage.setDriverLicense("H4H4BDH4H4BDH4H4BDH4H4BDH4H4BDH4H4BDH4H4BDH4H4BD");
        personalDetailPage.setDay(29);
        personalDetailPage.setGender("Female");
        personalDetailPage.updatePersonalDetails();
        personalDetailPage.compareErrorMsg("Should not exceed 30 characters");
    }

//    @Test(priority = 2, description = "Upload attachment file.")
//    public void uploadAttachmentFile() throws InterruptedException {
//        Thread.sleep(3000);
//        personalDetailPage = new PersonalDetailsPage(driver);
//        personalDetailPage.setFilePath("/src/test/resources/500-KB.pdf");
//        personalDetailPage.setComment("");
//        personalDetailPage.addAttachmentFile();
//    }
//
//    @Test(priority = 3, description = "Upload attachment file with comment.")
//    public void uploadAttachmentFileWithComment() throws InterruptedException {
//        Thread.sleep(3000);
//        personalDetailPage = new PersonalDetailsPage(driver);
//        personalDetailPage.setFilePath("/src/test/resources/500-KB.pdf");
//        personalDetailPage.setComment("This is my first uploaded file");
//        personalDetailPage.addAttachmentFile();
//    }
//
//    @Test(priority = 4, description = "Upload profile picture.")
//    public void uploadProfilePicture() throws InterruptedException {
//        personalDetailPage = new PersonalDetailsPage(driver);
//        personalDetailPage.setFilePath("/src/test/resources/profile2.jpg");
//        personalDetailPage.uploadProfilePicture();
//    }
}
