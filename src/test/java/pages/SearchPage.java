package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.ConfigReader;


public class SearchPage {
    private Page page;
    private Locator searchInput;
    private Locator searchButton;
    private Locator productList;

    public SearchPage(Page page) {
        this.page = page;
        this.searchInput  = page.locator("#search_product");
        this.searchButton = page.locator("#submit_search");
        this.productList  = page.locator(".productinfo");
    }
    public void navigate() {
        page.navigate(ConfigReader.PRODUCTS_URL);
    }
    public void searchProduct(String productName) {
        searchInput.fill(productName);
        searchButton.click();
    }
    public boolean isProductListVisible() {
        return productList.first().isVisible();
    }
    public void verifyHasResults() {
        assertThat(productList.first()).isVisible();
    }
     public void verifyNoResults() {
        // assertThat(page.getByText("No Products Found")).isVisible();
        assertThat(productList).hasCount(0);
        // assertThat(productList.first()).not().isVisible();
    }

}