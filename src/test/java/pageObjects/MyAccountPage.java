package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[contains(text(),'My Account')]")
	private WebElement myAccountHeading;

	@FindBy(xpath = "//*[@id='column-right']//a[contains(text(),'Logout')]")
	private WebElement logoutLink;

	public boolean isMyAccountHeadingDisplayed() {
		try {
			return myAccountHeading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickLogoutLink() {
		logoutLink.click();
	}
}
