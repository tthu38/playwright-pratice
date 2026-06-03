package base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class baseTest {
    protected Playwright playwright;
    protected Browser browser;

    protected BrowserContext context;
    protected Page page;

     @BeforeClass // Chạy 1 lần trước tất cả test trong class
    public void setup() {
        // Khởi tạo Playwright + Browser
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(true)
        );
    }

    @BeforeMethod // Chạy trước mỗi test method
    public void createContext() {
        // Tạo context + page mới trước mỗi test
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(30000); 
    }

     @AfterMethod // Chạy sau mỗi test method
    public void closeContext() {
        // Đóng context sau mỗi test
        context.close();
    }

    @AfterClass // Chạy 1 lần sau tất cả test trong class
    public void teardown() {
        // Đóng browser + playwright sau cả class
        browser.close();
        playwright.close();
    }

}
