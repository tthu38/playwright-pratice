// package tests;

// import com.microsoft.playwright.*;
// import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
// import org.testng.annotations.*;

// public class FirstTest {
//     Playwright playwright;
//     Browser browser;
//     BrowserContext context;
//     Page page;

//     @BeforeClass
//     public void setup() {
//         playwright = Playwright.create();
//         browser = playwright.chromium().launch(
//                 new BrowserType.LaunchOptions().setHeadless(false));
//     }

//     @BeforeMethod
//     public void createContext() {
//         context = browser.newContext();
//         page = context.newPage();
//     }

//     @Test
//     public void openHomePage() {
//         page.navigate("https://automationexercise.com");
//         assertThat(page).hasTitle("Automation Exercise");
//         System.out.println("Homepage load thành công!");
//     }

//     @Test
//     public void searchProduct() {
//         page.navigate("https://automationexercise.com/products");
//         // Navigate thẳng vào trang products, có search box ở đây
//         page.locator("input#search_product").fill("top");
//         page.locator("button#submit_search").click();
//         assertThat(page.locator(".productinfo").first()).isVisible();
//         System.out.println("Search hoạt động!");
//     }

//     @AfterMethod
//     public void closeContext() {
//         context.close();
//     }

//     @AfterClass
//     public void teardown() {
//         browser.close();
//         playwright.close();
//     }
// }