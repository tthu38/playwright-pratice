package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


import utils.ConfigReader;

public class SignUpPage {
    private Page page;
    private Locator signupName;
    private Locator signupEmail;
    private Locator signupButton;

    // Enter account information
    private Locator mrRadio;
    private Locator mrsRadio;
    private Locator password;
    private Locator daySelect;
    private Locator monthSelect;
    private Locator yearSelect;
    private Locator newsletterCheckbox;
    private Locator offersCheckbox;

    // Address information
    private Locator firstName;
    private Locator lastName;
    private Locator company;
    private Locator address1;
    private Locator address2;
    private Locator countrySelect;
    private Locator state;
    private Locator city;
    private Locator zipcode;
    private Locator mobileNumber;
    private Locator createAccountButton;

    public SignUpPage(Page page) {
        this.page = page;
        this.signupName = page.locator("[data-qa='signup-name']");
        this.signupEmail = page.locator("[data-qa='signup-email']");
        this.signupButton = page.locator("[data-qa='signup-button']");
        this.mrRadio = page.getByLabel("Mr.");
        this.mrsRadio = page.getByLabel("Mrs.");
        this.password = page.locator("[data-qa='password']");
        this.daySelect = page.locator("[data-qa='days']");
        this.monthSelect = page.locator("#uniform-months select");
        this.yearSelect = page.locator("#uniform-years select");
        this.newsletterCheckbox = page.getByLabel("Sign up for our newsletter!");
        this.offersCheckbox = page.getByLabel("Receive special offers from our partners!");
        this.firstName = page.locator("[data-qa='first_name']");
        this.lastName = page.locator("[data-qa='last_name']");
        this.company = page.locator("[data-qa='company']");
        this.address1 = page.locator("[data-qa='address']");
        this.address2 = page.locator("[data-qa='address2']");
        this.countrySelect = page.locator("[data-qa='country']");
        this.state = page.locator("[data-qa='state']");
        this.city = page.locator("[data-qa='city']");
        this.zipcode = page.locator("[data-qa='zipcode']");
        this.mobileNumber = page.locator("[data-qa='mobile_number']");
        this.createAccountButton = page.locator("[data-qa='create-account']");
    }

    // Navigate
    public void navigate() {
        page.navigate(ConfigReader.LOGIN_URL);
    }

    // B1: Nhập name + email
    public void enterSignupInfo(String name, String email) {
        signupName.fill(name);
        signupEmail.fill(email);
        signupButton.click();
    }

    // B2: Account Information
    public void selectTitle(String title) {
        if (title.equalsIgnoreCase("Mr")) {
            mrRadio.check();
        } else {
            mrsRadio.check();
        }
    }

    public void fillAccountInfo(String title, String pwd,
            String day, String month, String year) {
        selectTitle(title);
        password.fill(pwd);
        daySelect.selectOption(day);
        monthSelect.selectOption(month);
        yearSelect.selectOption(year);
    }

    public void tickNewsletter() {
        newsletterCheckbox.check();
    }

    public void tickOffers() {
        offersCheckbox.check();
    }

    // B3: Address Information
    public void fillAddressInfo(
            String fName, String lName,
            String comp, String addr1, String addr2,
            String country, String st, String ct,
            String zip, String mobile) {
        firstName.fill(fName);
        lastName.fill(lName);
        company.fill(comp);
        address1.fill(addr1);
        address2.fill(addr2);
        countrySelect.selectOption(country);
        state.fill(st);
        city.fill(ct);
        zipcode.fill(zip);
        mobileNumber.fill(mobile);
        // createAccountButton.click();
    }

    public void fillAddressInfoDefault() {
        fillAddressInfo(
                ConfigReader.FIRST_NAME,
                ConfigReader.LAST_NAME,
                ConfigReader.COMPANY,
                ConfigReader.ADDRESS,
                ConfigReader.ADDRESS2,
                ConfigReader.COUNTRY,
                ConfigReader.STATE,
                ConfigReader.CITY,
                ConfigReader.ZIPCODE,
                ConfigReader.MOBILE);
    }

    public void submitCreateAccount() {
        createAccountButton.click();
    }

    public void verifyEnterAccountInfoVisible() {
        assertThat(page.getByText("Enter Account Information"))
                .isVisible();
    }

    public void verifyNewsletterChecked() {
        assertThat(newsletterCheckbox).isChecked();
    }

    public void verifyOffersChecked() {
        assertThat(offersCheckbox).isChecked();
    }

    public void verifyAccountCreated() {
        assertThat(page.getByText("Account Created!")).isVisible();
    }

}
