package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

import base.baseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.LoginPage;
import utils.ConfigReader;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;

public class SessionTest extends baseTest {
    LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(page);
        loginPage.navigate();
    }

    @Test
    @Description("Verify session persists after page reload")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySessionPersistAfterReload() {
        // Bước 1: Login
        loginPage.login(ConfigReader.VALID_EMAIL, ConfigReader.VALID_PASSWORD);
        assertThat(page.getByText("Logged in as")).isVisible();
        System.out.println("Login successful!");

        // In cookie TRƯỚC khi reload
        System.out.println("=== COOKIES TRƯỚC RELOAD ===");
        context.cookies().forEach(c -> System.out.println(c.name + " = " + c.value));

        // Bước 2: Reload
        page.reload();
        System.out.println("\n=== SAU KHI RELOAD ===");

        System.out.println("=== COOKIES SAU RELOAD ===");
        context.cookies().forEach(c -> System.out.println(c.name + " = " + c.value));

        // Bước 3: Verify vẫn còn login
        assertThat(page.getByText("Logged in as")).isVisible();
        System.out.println("Session vẫn còn sau reload!");

        System.out.println("Số cookie: " + context.cookies().size());
    }

    @Test
    @Description("Save login session to file")
    @Severity(SeverityLevel.NORMAL)
    public void saveLoginSessionToFile(){
        loginPage.login(ConfigReader.VALID_EMAIL, ConfigReader.VALID_PASSWORD);
        assertThat(page.getByText("Logged in as")).isVisible();
        System.out.println("Login successful!");

        // Lưu session vào file
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("auth.json")));
        
        java.io.File file = new java.io.File("auth.json");
        assert file.exists() : "File auth.json should exist";
        System.out.println("Session saved to auth.json");

        try{
            String content = new String(java.nio.file.Files.readAllBytes(Paths.get("auth.json")));
            System.out.println("Content of auth.json:");
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Description("Reuse saved session without login")
    public void reuseSessionWithoutLogin() {
        loginPage.login(ConfigReader.VALID_EMAIL, ConfigReader.VALID_PASSWORD);
        assertThat(page.getByText("Logged in as")).isVisible();
        System.out.println("Login successful!");

        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("auth.json")));
        System.out.println("Session saved to auth.json");

        //B2: Tạo context mới - dùng lại session 
        BrowserContext newContext = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get("auth.json")));
        Page newPage = newContext.newPage();

        newPage.navigate(ConfigReader.BASE_URL);
        System.out.println("Navigate to homepage with new context");

        // B4: Verify vẫn đang login
        assertThat(newPage.getByText("Logged in as")).isVisible();
        System.out.println("Login state preserved in new context without re-login!");

        // B5: Vào trang checkout - verify vào được
        newPage.navigate(ConfigReader.BASE_URL + "/checkout");
        assertThat(newPage).hasURL(ConfigReader.BASE_URL + "/checkout");
        System.out.println("Successfully accessed checkout page without re-login!");

        // In sessionid để confirm
        newContext.cookies().stream()
            .filter(c -> c.name.equals("sessionid"))
            .findFirst()
            .ifPresent(c -> System.out.println("Session ID: " + c.value));

        newContext.close();
    }

    @Test
    @Description("Verify session expires when storage is cleared")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySessionExpiresWhenStorageCleared() {
        loginPage.login(ConfigReader.VALID_EMAIL, ConfigReader.VALID_PASSWORD);
        assertThat(page.getByText("Logged in as")).isVisible();
        System.out.println("Login successful!");

        int cookieBefore = context.cookies().size();
        System.out.println("Number of cookies before clearing storage: " + cookieBefore);
        // Clear storage
        context.clearCookies();

        //Verify cookies đã bị xóa 
        int cookiesAfter = context.cookies().size();
        assert cookiesAfter == 0 : "Cookies should be cleared";
        System.out.println("Cookies cleared successfully!");

        //Reload page 
        page.reload(new Page.ReloadOptions()
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        //Verify session đã hết hạn 
        assertThat(page.getByText("Logged in as")).not().isVisible();
        System.out.println("Logged out after clearing storage!");

        //Verify redirect về login 
        page.navigate(ConfigReader.BASE_URL + "/account");
        assertThat(page).hasURL(ConfigReader.LOGIN_URL);

       
    }
}


