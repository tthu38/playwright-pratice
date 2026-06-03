package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import base.baseTest;
import pages.SignUpPage;
import utils.ConfigReader;

public class SignUpTest extends baseTest {
    SignUpPage signUpPage;

    @BeforeMethod
    public void initPage() {
        signUpPage = new SignUpPage(page);
        signUpPage.navigate();
    }

    @Test
    public void signUpSuccess() {
        // Bước 1
        signUpPage.enterSignupInfo(
                ConfigReader.SIGNUP_NAME,
                ConfigReader.SIGNUP_EMAIL);

        // Bước 2
        signUpPage.verifyEnterAccountInfoVisible();
        signUpPage.fillAccountInfo(
                "Mr",
                ConfigReader.SIGNUP_PASSWORD,
                ConfigReader.DOB_DAY,
                ConfigReader.DOB_MONTH,
                ConfigReader.DOB_YEAR);
        signUpPage.tickNewsletter();
        signUpPage.tickOffers();
        signUpPage.verifyNewsletterChecked();
        signUpPage.verifyOffersChecked();

        // Bước 3
        signUpPage.fillAddressInfoDefault();
        signUpPage.submitCreateAccount();

        // Verify
        signUpPage.verifyAccountCreated();
        System.out.println("Signup thành công!");
    }

    @Test
    public void signUpWithEmptyName() {
        signUpPage.enterSignupInfo("", ConfigReader.SIGNUP_EMAIL);
        assertThat(page).hasURL(ConfigReader.LOGIN_URL);
        System.out.println("Empty name → has error message!");
    }

    @Test
    public void signUpWithEmptyEmail() {
        signUpPage.enterSignupInfo(ConfigReader.SIGNUP_NAME, "");
        assertThat(page).hasURL(ConfigReader.LOGIN_URL);
        System.out.println("Empty email → has error message!");
    }

    @Test
    public void signUpWithInvalidEmail() {
        signUpPage.enterSignupInfo(ConfigReader.SIGNUP_NAME, "invalidmail@");
        assertThat(page).hasURL(ConfigReader.LOGIN_URL);
        System.out.println("Invalid email → form không submit!");
    }

    @Test
    public void signUpWithExistingEmail() {
        signUpPage.enterSignupInfo(ConfigReader.SIGNUP_NAME, ConfigReader.VALID_EMAIL);
        assertThat(page.getByText("Email Address already exist!")).isVisible();
        System.out.println("Existing email → hiện thông báo lỗi!");
    }

    @Test
    public void signUpWithEmptyPassword() {

        signUpPage.enterSignupInfo(
                ConfigReader.SIGNUP_NAME,
                ConfigReader.SIGNUP_EMAIL);

        signUpPage.verifyEnterAccountInfoVisible();
        signUpPage.fillAccountInfo(
                "Mr", "",
                ConfigReader.DOB_DAY,
                ConfigReader.DOB_MONTH,
                ConfigReader.DOB_YEAR);

        signUpPage.submitCreateAccount();
        assertThat(page).hasURL(ConfigReader.SIGNUP_URL);
        System.out.println("Empty password → form không submit!");
    }

    @Test
    public void signUpWithEmptyFirstName() {
        String randomEmail = "test" + System.currentTimeMillis() + "@gmail.com";
        signUpPage.enterSignupInfo(ConfigReader.SIGNUP_NAME, randomEmail);

        signUpPage.verifyEnterAccountInfoVisible();
        signUpPage.fillAccountInfo(
                "Mr",
                ConfigReader.SIGNUP_PASSWORD,
                ConfigReader.DOB_DAY,
                ConfigReader.DOB_MONTH,
                ConfigReader.DOB_YEAR);
        signUpPage.tickNewsletter();
        signUpPage.tickOffers();

        signUpPage.fillAddressInfo(
                "",
                ConfigReader.LAST_NAME,
                ConfigReader.COMPANY,
                ConfigReader.ADDRESS,
                ConfigReader.ADDRESS2,
                ConfigReader.COUNTRY,
                ConfigReader.STATE,
                ConfigReader.CITY,
                ConfigReader.ZIPCODE,
                ConfigReader.MOBILE);

        signUpPage.submitCreateAccount();
        assertThat(page).hasURL(ConfigReader.SIGNUP_URL);
        System.out.println("Empty first name → form without submit!");
    }

    @Test
    public void signUpWithEmptyLastName() {
        signUpPage.enterSignupInfo(
                ConfigReader.SIGNUP_NAME,
                ConfigReader.SIGNUP_EMAIL);

        signUpPage.verifyEnterAccountInfoVisible();
        signUpPage.fillAccountInfo(
                "Mr",
                ConfigReader.SIGNUP_PASSWORD,
                ConfigReader.DOB_DAY,
                ConfigReader.DOB_MONTH,
                ConfigReader.DOB_YEAR);
        signUpPage.tickNewsletter();
        signUpPage.tickOffers();

        signUpPage.fillAddressInfo(
                ConfigReader.FIRST_NAME,
                "",
                ConfigReader.COMPANY,
                ConfigReader.ADDRESS,
                ConfigReader.ADDRESS2,
                ConfigReader.COUNTRY,
                ConfigReader.STATE,
                ConfigReader.CITY,
                ConfigReader.ZIPCODE,
                ConfigReader.MOBILE);

        signUpPage.submitCreateAccount();
        assertThat(page).hasURL(ConfigReader.SIGNUP_URL);
        System.out.println("Empty last name → form without submit!");
    }

    @Test
    public void signUpWithEmptyAddress() {
        signUpPage.enterSignupInfo(
                ConfigReader.SIGNUP_NAME,
                ConfigReader.SIGNUP_EMAIL);

        signUpPage.verifyEnterAccountInfoVisible();
        signUpPage.fillAccountInfo(
                "Mr",
                ConfigReader.SIGNUP_PASSWORD,
                ConfigReader.DOB_DAY,
                ConfigReader.DOB_MONTH,
                ConfigReader.DOB_YEAR);
        signUpPage.tickNewsletter();
        signUpPage.tickOffers();

        signUpPage.fillAddressInfo(
                ConfigReader.FIRST_NAME,
                ConfigReader.LAST_NAME,
                ConfigReader.COMPANY,
                "",
                ConfigReader.ADDRESS2,
                ConfigReader.COUNTRY,
                ConfigReader.STATE,
                ConfigReader.CITY,
                ConfigReader.ZIPCODE,
                ConfigReader.MOBILE);

        signUpPage.submitCreateAccount();
        assertThat(page).hasURL(ConfigReader.SIGNUP_URL);
        System.out.println("Empty address → form without submit!");
    }

    @Test
    public void signUpWithEmptyMobile() {
        signUpPage.enterSignupInfo(
                ConfigReader.SIGNUP_NAME,
                ConfigReader.SIGNUP_EMAIL);

        signUpPage.verifyEnterAccountInfoVisible();
        signUpPage.fillAccountInfo(
                "Mr",
                ConfigReader.SIGNUP_PASSWORD,
                ConfigReader.DOB_DAY,
                ConfigReader.DOB_MONTH,
                ConfigReader.DOB_YEAR);
        signUpPage.tickNewsletter();
        signUpPage.tickOffers();

        signUpPage.fillAddressInfo(
                ConfigReader.FIRST_NAME,
                ConfigReader.LAST_NAME,
                ConfigReader.COMPANY,
                ConfigReader.ADDRESS,
                ConfigReader.ADDRESS2,
                ConfigReader.COUNTRY,
                ConfigReader.STATE,
                ConfigReader.CITY,
                ConfigReader.ZIPCODE,
                "");

        signUpPage.submitCreateAccount();
        assertThat(page).hasURL(ConfigReader.SIGNUP_URL);
        System.out.println("Empty mobile → form without submit!");
    }

    // Phone chứa ký tự không phải số
    @Test
    public void signUpWithInvalidMobile() {
            signUpPage.enterSignupInfo(
                            ConfigReader.SIGNUP_NAME,
                            ConfigReader.SIGNUP_EMAIL);

            signUpPage.verifyEnterAccountInfoVisible();
            signUpPage.fillAccountInfo(
                            "Mr",
                            ConfigReader.SIGNUP_PASSWORD,
                            ConfigReader.DOB_DAY,
                            ConfigReader.DOB_MONTH,
                            ConfigReader.DOB_YEAR);
            signUpPage.tickNewsletter();
            signUpPage.tickOffers();

            signUpPage.fillAddressInfo(
                            ConfigReader.FIRST_NAME,
                            ConfigReader.LAST_NAME,
                            ConfigReader.COMPANY,
                            ConfigReader.ADDRESS,
                            ConfigReader.ADDRESS2,
                            ConfigReader.COUNTRY,
                            ConfigReader.STATE,
                            ConfigReader.CITY,
                            ConfigReader.ZIPCODE,
                            "abc!@#");

            signUpPage.submitCreateAccount();
            // Thêm vào BaseTest.java hoặc trước khi chụp
            try {
                    Files.createDirectories(Paths.get("screenshots"));
            } catch (IOException e) {
                    e.printStackTrace();
            }

            page.screenshot(new Page.ScreenshotOptions()
                            .setPath(Paths.get("screenshots/invalid_mobile_bug.png"))
                            .setFullPage(true));
            assertThat(page).hasURL(ConfigReader.SIGNUP_URL);
            System.out.println("Invalid mobile → form without submit!");
    }

    // Phone không được quá 10 số
    @Test
    public void signUpWithPhonethan10Digits() {
            signUpPage.enterSignupInfo(
                            ConfigReader.SIGNUP_NAME,
                            ConfigReader.SIGNUP_EMAIL);

            signUpPage.verifyEnterAccountInfoVisible();
            signUpPage.fillAccountInfo(
                            "Mr",
                            ConfigReader.SIGNUP_PASSWORD,
                            ConfigReader.DOB_DAY,
                            ConfigReader.DOB_MONTH,
                            ConfigReader.DOB_YEAR);
            signUpPage.tickNewsletter();
            signUpPage.tickOffers();

            signUpPage.fillAddressInfo(
                            ConfigReader.FIRST_NAME,
                            ConfigReader.LAST_NAME,
                            ConfigReader.COMPANY,
                            ConfigReader.ADDRESS,
                            ConfigReader.ADDRESS2,
                            ConfigReader.COUNTRY,
                            ConfigReader.STATE,
                            ConfigReader.CITY,
                            ConfigReader.ZIPCODE,
                            "090128577777");

            signUpPage.submitCreateAccount();
            assertThat(page).hasURL(ConfigReader.SIGNUP_URL);
            System.out.println("Invalid mobile → form without submit!");

}
}
