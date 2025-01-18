package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// Define page elements and methods here

	@FindBy(id = "input-email")
	private WebElement emailField;

	@FindBy(id = "input-password")
	private WebElement passwordField;

	@FindBy(css = "input[value=\"Login\"]")
	private WebElement loginBtn;

	public void setEmail(String email) {
		emailField.sendKeys(email);
	}

	public void setPassword(String password) {
		passwordField.sendKeys(password);
	}

	public void clickLoginButton() {
		loginBtn.click();
	}

}
