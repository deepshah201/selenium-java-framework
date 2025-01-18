package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "a[title=\"My Account\"]")
	private WebElement myAccountLink;

	@FindBy(xpath = "//a[contains(text(),'Register')]")
	private WebElement registerLinkElement;

	@FindBy(xpath = "//ul[contains(@class,'drop')]//li//a[contains(text(),'Login')]")
	private WebElement loginLinkElement;

	public void clickMyAccountLink() {
		myAccountLink.click();
	}

	public void clickRegisterLink() {
		registerLinkElement.click();
	}

	public void clickLoginLink() {
		loginLinkElement.click();
	}

}
