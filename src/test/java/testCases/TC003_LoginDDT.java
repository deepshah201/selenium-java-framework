package testCases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

@Listeners(utilities.ExtentReportManager.class)
public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "datadriven")
	public void verify_login_DDT(String email, String pswd, String expected) {

		logger.info("** Starting TC003_LoginDDT **");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccountLink();
			hp.clickLoginLink();
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pswd);
			lp.clickLoginButton();
			MyAccountPage myAccount = new MyAccountPage(driver);
			boolean target = myAccount.isMyAccountHeadingDisplayed();

			if (expected.equalsIgnoreCase("Valid")) {
				if (target == true) {
					Assert.assertTrue(true);
					myAccount.clickLogoutLink();
				} else {
					Assert.assertTrue(false);
				}
			}
			if (expected.equalsIgnoreCase("Invalid")) {
				if (target == true) {
					myAccount.clickLogoutLink();
					Assert.assertTrue(false);
				} else {
					Assert.assertTrue(true);
				}
			}

		} catch (Exception e) {
			logger.error("Test failed :- ", e);
			logger.debug("Debug logs");
			Assert.fail();
		}
		logger.info("** Finish TC003_LoginDDT **");

	}

}
