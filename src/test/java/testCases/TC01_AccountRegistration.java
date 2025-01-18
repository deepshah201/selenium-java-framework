package testCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

@Listeners(utilities.ExtentReportManager.class)
public class TC01_AccountRegistration extends BaseClass {

	@Test(groups = { "Regression", "Master" })
	public void verify_account_registration() {
		try {
			logger.info("** Starting TC01_AccountRegistration **");
			HomePage hp = new HomePage(driver);
			hp.clickMyAccountLink();
			logger.info("Clicked on my account");
			hp.clickRegisterLink();
			logger.info("Clicked on register");
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			logger.info("Providing customer details...");
			regpage.setFirstName(randomString().toUpperCase());
			regpage.setLastName(randomString().toUpperCase());
			regpage.setEmail(randomString() + "@gmail.com");
			regpage.setTelephone(randomNumber());
			regpage.setPassword("12@qwesAQ");
			regpage.setConfirmPassword("12@qwesAQ");
			regpage.clickAgreeCheckbox();
			regpage.clickContinueBtn();
		} catch (Exception e) {
			logger.error("Test failed :- ", e);
			logger.debug("Debug logs");
			Assert.fail();
		}
		logger.info("** Finish TC01_AccountRegistration **");
	}

}
