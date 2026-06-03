package tests;

import base.baseTest;
import data.LoginDataProvider;
import pages.LoginPage;

import org.testng.annotations.*;

public class LoginTest extends baseTest {
    LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(page);
        loginPage.navigate();
    }

    @Test(dataProvider = "loginData", dataProviderClass = LoginDataProvider.class)
    public void testLogin(String email, String password, String verifyType) {
        loginPage.verifyLoginPageVisible();
        loginPage.login(email, password);

        switch (verifyType) {
            case "success"     -> { loginPage.verifyLoginSuccess();     System.out.println("Login successful: " + email); }
            case "failed"      -> { loginPage.verifyLoginFailed();      System.out.println("Login failed: " + email); }
            case "stillOnPage" -> { loginPage.verifyStillOnLoginPage(); System.out.println("Still on login page: " + email); }
        }
    }
}