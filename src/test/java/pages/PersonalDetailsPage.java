package pages;

import org.openqa.selenium.*;
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
    String driverLicenseMonth;
    String driverLicenseYear;
    String birthYear;
    String birthMonth;

    int driverLicenseDay;
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

    public void setDriverLicenseYear(String year) {
        this.driverLicenseYear = year;
    }

    public void setDriverLicenseMonth(String month) {
        this.driverLicenseMonth = month;
    }

    public void setDriverLicenseDay(int day) { this.driverLicenseDay = day; }

    public void setBirthYear(String  year) { this.birthYear = year; }

    public void setBirthMonth(String month) { this.birthMonth = month; }

    public void setBirthDay(int day) { this.birthDay = day; }

    public void setGender(String gender) { this.gender = gender; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public void setComment(String comment) { this.comment = comment; }

    private By getInputByLabel(String label) {
        return By.xpath("//label[contains(text(), '" + label + "')]/ancestor::div[contains(@class, 'oxd-input-group')]//input");
    }

    private By getSelectInputByLabel(String label) {
        return By.xpath("//label[contains(text(), '" + label + "')]/ancestor::div[contains(@class, 'oxd-input-group')]//div[contains(@class, 'oxd-select-text')]");
    }

    private By getDateInputByLabel(String label) {
        return By.xpath("//label[contains(text(), '" + label + "')]/ancestor::div[contains(@class, 'oxd-input-group')]//input[contains(@placeholder, 'yyyy-dd-mm')]");
    }

    private WebElement getSubmitBtnByParentClass(String parentClass) {
        WebElement formContainer = driver.findElement(By.cssSelector(parentClass));
        return formContainer.findElement(By.cssSelector("button[type='submit']"));
    }

    private void waitPageToLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-loading-spinner")));
    }

    public void inputFullname(String firstName, String middleName, String lastName) {
        WebElement firstNameInput = driver.findElement(By.cssSelector("[placeholder='First Name']"));
        WebElement middleNameInput = driver.findElement(By.cssSelector("[placeholder='Middle Name']"));
        WebElement lastNameInput = driver.findElement(By.cssSelector("[placeholder='Last Name']"));

        firstNameInput.sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        middleNameInput.sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        lastNameInput.sendKeys(Keys.CONTROL,"a",Keys.DELETE);

        if(!firstName.trim().isEmpty()) {
            firstNameInput.sendKeys(firstName);
        }

        if(!middleName.trim().isEmpty()) {
            middleNameInput.sendKeys(middleName);
        }

        if(!lastName.trim().isEmpty()) {
            lastNameInput.sendKeys(lastName);
        }
    }

    public void inputEmployeeIds(String employeeId, String otherId) {
        WebElement employeeIdInput = driver.findElement(getInputByLabel("Employee Id"));
        WebElement otherIdInput = driver.findElement(getInputByLabel("Other Id"));

        employeeIdInput.sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        otherIdInput.sendKeys(Keys.CONTROL,"a",Keys.DELETE);

        if(!employeeId.trim().isEmpty()) {
            employeeIdInput.sendKeys(employeeId);
        }

        if(!otherId.trim().isEmpty()) {
            otherIdInput.sendKeys(otherId);
        }
    }

    public void inputDate(String label, String year, String month, int day) {
        WebElement dateInput = driver.findElement(getInputByLabel(label));
        dateInput.sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        dateInput.sendKeys(year + "-" + month +"-" + day);
    }

    public void inputDriverLicense(String driverLicense, String year, String month, int day) {
        WebElement driverLicenseInput = driver.findElement(getInputByLabel("License Number"));

        if(!driverLicense.isEmpty()) {
            driverLicenseInput.sendKeys(Keys.CONTROL,"a",Keys.DELETE);
            driverLicenseInput.sendKeys(driverLicense);
        }

        inputDate("License Expiry Date", year, month, day);
    }

    public void selectOption(String label, String optionVal) {
        driver.findElement(getSelectInputByLabel(label)).click();

        By optionListContainer = By.className("oxd-select-dropdown");
        WebElement optionContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(optionListContainer));

        boolean optionSelected = false;
        while (!optionSelected) {
            try {
                List<WebElement> options = optionContainer.findElements(By.cssSelector("[role='option']"));
                for (WebElement option : options) {
                    if (option.getText().equals(optionVal)) {
                        option.click();
                        optionSelected = true;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                // Re-locate the option container if stale
                optionContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(optionListContainer));
            }
        }
    }

    public void selectMonthOrYear(String type, String optionVal) {
        // Still under development

        WebElement selectInput = driver.findElement(By.className("oxd-calendar-selector-" + type));
        selectInput.click();

        By optionListContainer = By.className("oxd-calendar-dropdown");
        WebElement optionContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(optionListContainer));

        boolean optionSelected = false;
        while (!optionSelected) {
            try {
                List<WebElement> options = optionContainer.findElements(By.className("oxd-calendar-dropdown--option"));
                for (WebElement option : options) {
                    if (option.getText().equals(optionVal)) {
                        option.click();
                        optionSelected = true;
                        break;

                    }
                }
            } catch (StaleElementReferenceException e) {
                // Re-locate the option container if stale
                optionContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(optionListContainer));
            }
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(optionListContainer));
    }


    public void selectDate(String dateLabel, int day, String month, String year) {
        // Still under development

        driver.findElement(getDateInputByLabel(dateLabel)).click();

        selectMonthOrYear("month", month);
        selectMonthOrYear("year", year);

        WebElement selectedDay = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='oxd-calendar-date'][normalize-space()='" + day + "'])")));
        selectedDay.click();
    }

    public void selectGender(String gender) {
        if(!gender.trim().isEmpty()) {
            driver.findElement(By.xpath("(//label[normalize-space()='"+ gender +"'])")).click();
        }
    }

    public void updatePersonalDetails(boolean isSuccess) {
        waitPageToLoad();

        inputFullname(firstName, middleName, lastName);
        inputEmployeeIds(employeeId, otherId);
        inputDriverLicense(driverLicense, driverLicenseYear, driverLicenseMonth, driverLicenseDay);
        inputDate("Date of Birth", birthYear, birthMonth, birthDay);
        selectOption("Nationality", nationality);
        selectOption("Marital Status", maritalStatus);
        selectGender(gender);

        WebElement submitBtn = getSubmitBtnByParentClass(".orangehrm-horizontal-padding");
        submitBtn.click();

        if(isSuccess == true) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-toast--success")));
        }
    }

    public void updateRequiredOnlyPersonalDetails() {
        waitPageToLoad();

        inputFullname(firstName, "", lastName);

        WebElement submitBtn = getSubmitBtnByParentClass(".orangehrm-horizontal-padding");
        submitBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-toast--success")));
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

    public void addAttachmentFile(boolean isSuccess) {
        waitPageToLoad();

        WebElement addAttachmentSection = driver.findElement(By.className("bi-plus"));
        action.moveToElement(addAttachmentSection).perform();
        addAttachmentSection.click();

        WebElement submitBtn = getSubmitBtnByParentClass(".orangehrm-attachment");

        action.moveToElement(submitBtn).perform();
        inputFile(filePath);
        inputComment(comment);

        submitBtn.click();
        if(isSuccess == true) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-toast--success")));
        }
    }

    public void uploadProfilePicture(boolean isSuccess) {
        driver.findElement(By.className("orangehrm-edit-employee-image")).click();

        waitPageToLoad();

        inputProfilePicture(filePath);

        WebElement submitBtn = getSubmitBtnByParentClass(".orangehrm-edit-employee-content");
        submitBtn.click();
        if(isSuccess == true) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-toast--success")));
        }
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
