package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import utils.ConfigReader;

public class LoginPage {
    private Page page;
    private Locator loginForm;
    private Locator emailInput;
    private Locator passwordInput;
    private Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        this.loginForm    = page.locator(".login-form");
        this.emailInput   = loginForm.getByPlaceholder("Email Address");
        this.passwordInput = loginForm.getByPlaceholder("Password");
        this.loginButton  = loginForm.getByRole(AriaRole.BUTTON,
            new Locator.GetByRoleOptions().setName("Login"));
    }

    public void navigate() {
        page.navigate(ConfigReader.LOGIN_URL);
    }
     public void verifyLoginPageVisible() {
        assertThat(page.getByRole(AriaRole.HEADING,
            new Page.GetByRoleOptions().setName("Login to your account")))
            .isVisible();
    }

    public void login(String email, String password) {
        emailInput.fill(email);
        passwordInput.fill(password);
        loginButton.click();
    }

    public void verifyLoginSuccess() {
        assertThat(page).hasURL(ConfigReader.BASE_URL + "/");
        assertThat(page.getByText("Logged in as")).isVisible();
    }
    
    public void verifyLoginFailed() {
        assertThat(page.getByText("Your email or password is incorrect!"))
                .isVisible();
    }

    public void verifyStillOnLoginPage() {
        assertThat(page).hasURL(ConfigReader.LOGIN_URL);
    }
}
