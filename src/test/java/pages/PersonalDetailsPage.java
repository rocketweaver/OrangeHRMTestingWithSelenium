package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class PersonalDetailsPage {
    WebDriver driver;
    Actions action;

    String firstName;
    String middleName;
    String lastName;
    String employeeId;
    String otherId;
    String driverLicense;
    String gender;
    String nationality;
    String maritalStatus;
    String filePath;
    String comment;

    int day;

    public PersonalDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.action = new Actions(driver);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void inputFullname(String firstName, String middleName, String lastName) {
        if(!firstName.trim().isEmpty()) {
            driver.findElement(By.xpath("//input[@placeholder='First Name'][1]")).sendKeys(firstName);
        }

        if(!middleName.trim().isEmpty()) {
            driver.findElement(By.xpath("//input[@placeholder='Middle Name'][1]")).sendKeys(middleName);
        }

        if(!lastName.trim().isEmpty()) {
            driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(lastName);
        }
    }

    public void inputEmployeeIds(String employeeId, String otherId) {
        if(!employeeId.trim().isEmpty()) {
            driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]")).sendKeys(employeeId);
        }

        if(!otherId.trim().isEmpty()) {
            driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]")).sendKeys(otherId);
        }
    }

    public void inputDriverLicense(String driverLiscense, int calendarToggler, int day) {
        if(!driverLiscense.isEmpty()) {
            driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[3]")).sendKeys(driverLiscense);
        }

        this.selectDate(calendarToggler, day);
    }

    public void selectOptions(int selectToggler, int option) {
        driver.findElement(By.xpath("(//div[@class='oxd-select-text-input'][normalize-space()='-- Select --'])[" + selectToggler + "]")).click();
        WebElement select = driver.findElement(By.xpath("(//div[@role='listbox'])[1]"));
        List<WebElement> options = select.findElements(By.xpath("./child::*"));
        options.get(option).click();
    }

    public void selectDate(int calendarToggler, int day) {
        driver.findElement(By.xpath("(//i[@class='oxd-icon bi-calendar oxd-date-input-icon'])["+ calendarToggler +"]")).click();
        driver.findElement(By.xpath("(//div[@class='oxd-calendar-date'][normalize-space()='" + day + "'])[1]")).click();
    }

    public void selectGender(String gender) {
        if(!gender.trim().isEmpty()) {
            driver.findElement(By.xpath("(//label[normalize-space()='"+ gender +"'])[1]")).click();
        }
    }

    public void updatePersonalDetails() {
        inputFullname(this.firstName, this.middleName, this.lastName);
        inputEmployeeIds(this.employeeId, this.otherId);
        inputDriverLicense(this.driverLicense, 1, this.day);
        selectOptions(1, 3);
        selectOptions(2, 3);
        selectDate(2, this.day);
        selectGender(this.gender);
        driver.findElement(By.xpath("(//button[normalize-space()='Save'])[1]")).click();
    }

    public void updateRequiredOnlyPersonalDetails() {
        inputFullname(this.firstName, "", this.lastName);
        driver.findElement(By.xpath("(//button[normalize-space()='Save'])[1]")).click();
    }

    public void inputFile(String file) {
        String filePath = System.getProperty("user.dir") + file;

        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(filePath);
    }

    public void inputComment(String comment) {
        if(!comment.trim().isEmpty()) {
            driver.findElement(By.className("oxd-textarea")).sendKeys(comment);
        }
    }

    public void inputProfilePicture(String file) {
        String filePath = System.getProperty("user.dir") + file;

        driver.findElement(By.xpath("(//i[@class='oxd-icon bi-plus'])[1]")).click();

        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(filePath);
    }

    public void addAttachmentFile() {
        WebElement addAttachmentSection = driver.findElement(By.className("bi-plus"));
        action.moveToElement(addAttachmentSection).perform();
        addAttachmentSection.click();

        WebElement saveAttachmentBtn = driver.findElement(By.xpath("(//button[normalize-space()='Save'])[2]"));
        action.moveToElement(saveAttachmentBtn).perform();

        inputFile(this.filePath);
        inputComment(this.comment);

        saveAttachmentBtn.click();
    }

    public void uploadProfilePicture() throws InterruptedException {
        driver.findElement(By.className("orangehrm-edit-employee-image")).click();
        Thread.sleep(2500);

        inputProfilePicture(this.filePath);

        driver.findElement(By.xpath("(//button[normalize-space()='Save'])[1]")).click();
    }
}
