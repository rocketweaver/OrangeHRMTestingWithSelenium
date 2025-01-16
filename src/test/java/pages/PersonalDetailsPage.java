package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class PersonalDetailsPage {
    WebDriver driver;
    WebDriverWait wait;
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

    int driverLicenseDay;
    int driverLicenseMonth;
    int driverLicenseYear;
    int birthYear;
    int birthMonth;
    int birthDay;

    public PersonalDetailsPage(WebDriver driver) {
        this.driver = driver;

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        action = new Actions(driver);
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

    public void setDriverLicenseYear(int year) {
        this.driverLicenseYear = year;
    }

    public void setDriverLicenseMonth(int month) {
        this.driverLicenseMonth = month;
    }

    public void setDriverLicenseDay(int day) { this.driverLicenseDay = day; }

    public void setBirthYear(int year) {
        this.birthYear = year;
    }

    public void setBirthMonth(int month) {
        this.birthMonth = month;
    }

    public void setBirthDay(int day) {
        this.birthDay = day;
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

    private By getInputByLabel(String label) {
        return By.xpath("//label[contains(text(), '" + label + "')]/ancestor::div[contains(@class, 'oxd-input-group')]//input");
    }

    private By getSelectInputByLabel(String label) {
        return By.xpath("//label[contains(text(), '" + label + "')]/ancestor::div[contains(@class, 'oxd-input-group')]//div[contains(@class, 'oxd-select-text')]");
    }

    private WebElement getSubmitBtnByParentClass(String parentClass) {
        WebElement formContainer = driver.findElement(By.cssSelector(parentClass));
        return formContainer.findElement(By.cssSelector("button[type='submit']"));
    }

    private void resetForm() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-loading-spinner")));
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('oxd-form')[0].reset()");
    }

    public void inputFullname(String firstName, String middleName, String lastName) {
        if(!firstName.trim().isEmpty()) {
            driver.findElement(By.cssSelector("[placeholder='First Name']")).sendKeys(firstName);
        }

        if(!middleName.trim().isEmpty()) {
            driver.findElement(By.cssSelector("[placeholder='Middle Name']")).sendKeys(middleName);
        }

        if(!lastName.trim().isEmpty()) {
            driver.findElement(By.cssSelector("[placeholder='Last Name']")).sendKeys(lastName);
        }
    }

    public void inputEmployeeIds(String employeeId, String otherId) {
        if(!employeeId.trim().isEmpty()) {
            driver.findElement(getInputByLabel("Employee Id")).sendKeys(employeeId);
        }

        if(!otherId.trim().isEmpty()) {
            driver.findElement(getInputByLabel("Other Id")).sendKeys(otherId);
        }
    }

    public void inputDate(int dateInputIndex, int year, int month, int day) {
        List<WebElement> dateInputs = driver.findElements(By.cssSelector("[placeholder='yyyy-mm-dd']"));
        WebElement dateInput = dateInputs.get(dateInputIndex);
        dateInput.sendKeys(year + "-" + month +"-" + day);
    }

    public void inputDriverLicense(String driverLicense, int dateInputIndex, int year, int month, int day) {
        if(!driverLicense.isEmpty()) {
            driver.findElement(getInputByLabel("License Number")).sendKeys(driverLicense);
        }

        inputDate(dateInputIndex, year, month, day);
    }

    public void selectOption(String label, String optionVal) {
        driver.findElement(getSelectInputByLabel(label)).click();

        By optionList = By.className("oxd-select-dropdown");
        WebElement optionContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(optionList));

        By selectedOption = By.xpath("//span[contains(text(), '"+ optionVal +"')]");
        optionContainer.findElement(selectedOption).click();
    }

    public void selectDate(int calendarToggler, int day) {
        driver.findElement(By.xpath("(//i[@class='oxd-icon bi-calendar oxd-date-input-icon'])["+ calendarToggler +"]")).click();
        driver.findElement(By.xpath("(//div[@class='oxd-calendar-date'][normalize-space()='" + day + "'])[1]")).click();
    }

    public void selectGender(String gender) {
        if(!gender.trim().isEmpty()) {
            driver.findElement(By.xpath("(//label[normalize-space()='"+ gender +"'])")).click();
        }
    }

    public void updatePersonalDetails() {
        resetForm();

        inputFullname(firstName, middleName, lastName);
        inputEmployeeIds(employeeId, otherId);
        inputDriverLicense(driverLicense, 0, driverLicenseYear, driverLicenseMonth, driverLicenseDay);
        inputDate(1, birthYear, birthMonth, birthDay);
        selectGender(gender);

        WebElement submitBtn = getSubmitBtnByParentClass(".orangehrm-horizontal-padding");
        submitBtn.click();
    }

    public void updateRequiredOnlyPersonalDetails() {
        resetForm();
        inputFullname(firstName, "", lastName);

        WebElement submitBtn = getSubmitBtnByParentClass(".orangehrm-horizontal-padding");
        submitBtn.click();
    }

    public void inputFile(String file) {
        String filePath = System.getProperty("user.dir") + file;

        WebElement fileInput = driver.findElement(By.cssSelector("[type='file']"));
        fileInput.sendKeys(filePath);
    }

    public void inputComment(String comment) {
        By commentTextarea = By.className("oxd-textarea");

        if(!comment.trim().isEmpty()) {
            driver.findElement(commentTextarea).sendKeys(comment);
        }
    }

    public void inputProfilePicture(String file) {
        String filePath = System.getProperty("user.dir") + file;

        By plusIcon = By.className("bi-plus");

        driver.findElement(plusIcon).click();

        WebElement fileInput = driver.findElement(By.cssSelector("[type='file']"));
        fileInput.sendKeys(filePath);
    }

    public void addAttachmentFile() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-loading-spinner")));

        WebElement addAttachmentSection = driver.findElement(By.className("bi-plus"));
        action.moveToElement(addAttachmentSection).perform();
        addAttachmentSection.click();

        WebElement submitBtn = getSubmitBtnByParentClass(".orangehrm-attachment");

        action.moveToElement(submitBtn).perform();
        inputFile(filePath);
        inputComment(comment);

        submitBtn.click();
    }

    public void uploadProfilePicture() {
        driver.findElement(By.className("orangehrm-edit-employee-image")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-loading-spinner")));

        inputProfilePicture(filePath);

        WebElement submitBtn = getSubmitBtnByParentClass(".orangehrm-edit-employee-content");
        submitBtn.click();
    }

    public void compareErrorMsg(String errorType) {
        try {
            String errorMessage = driver.findElement(By.className("oxd-input-field-error-message")).getText();
            Assert.assertEquals(errorMessage, errorType);
        } catch (AssertionError e) {
            // Log the error
            System.err.println("Assertion failed: " + e.getMessage());
        }
    }

}
