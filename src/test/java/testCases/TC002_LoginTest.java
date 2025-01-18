package testCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

@Listeners(utilities.ExtentReportManager.class)
public class TC002_LoginTest extends BaseClass {

	@Test(groups = { "Sanity", "Master" })
	public void verify_login_with_valid_credentials() {
		logger.info("** Starting TC002_LoginTest **");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccountLink();
			hp.clickLoginLink();
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(properties.getProperty("EMAIL"));
			lp.setPassword(properties.getProperty("PASSWORD"));
			lp.clickLoginButton();
			MyAccountPage myAccount = new MyAccountPage(driver);
			Assert.assertTrue(myAccount.isMyAccountHeadingDisplayed());
		} catch (Exception e) {
			logger.error("Test failed :- ", e);
			logger.debug("Debug logs");
			Assert.fail();
		}
		logger.info("** Finish TC002_LoginTest **");
	}

}
