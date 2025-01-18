package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "input-firstname")
	private WebElement firstNameField;

	@FindBy(id = "input-lastname")
	private WebElement lastNameField;

	@FindBy(id = "input-email")
	private WebElement emailField;

	@FindBy(id = "input-telephone")
	private WebElement telephoneField;

	@FindBy(id = "input-password")
	private WebElement passwordField;

	@FindBy(id = "input-confirm")
	private WebElement confirmPasswordField;

	@FindBy(css = "input[name=\"agree\"]")
	private WebElement agreeCheckbox;

	@FindBy(css = "input[value=\"Continue\"]")
	private WebElement continueBtn;

	public void setFirstName(String firstName) {
		firstNameField.sendKeys(firstName);
	}

	public void setLastName(String lastName) {
		lastNameField.sendKeys(lastName);
	}

	public void setEmail(String email) {
		emailField.sendKeys(email);
	}

	public void setTelephone(String telephone) {
		telephoneField.sendKeys(telephone);
	}

	public void setPassword(String password) {
		passwordField.sendKeys(password);
	}

	public void setConfirmPassword(String password) {
		confirmPasswordField.sendKeys(password);
	}

	public void clickAgreeCheckbox() {
		agreeCheckbox.click();
	}

	public void clickContinueBtn() {
		continueBtn.click();
	}

}
