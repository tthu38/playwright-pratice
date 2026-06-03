package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.ConfigReader;

public class ProductDetailPage {
    private Page page;
    private Locator productName;
    private Locator productPrice;
    private Locator productCategory;
    private Locator productAvailability;
    private Locator productCondition;
    private Locator productBrand;
    private Locator addToCartButton;
    private Locator quantityInput;
    private Locator reviewSection;
    private Locator viewProductLinks;

    public ProductDetailPage(Page page) {
        this.page = page;
        this.productName         = page.locator(".product-information h2");
        this.productPrice        = page.locator(".product-information span span");
        this.productCategory     = page.locator(".product-information p").filter(new Locator.FilterOptions().setHasText("Category:"));
        this.productAvailability = page.locator(".product-information p").filter(new Locator.FilterOptions().setHasText("Availability:"));
        this.productCondition    = page.locator(".product-information p").filter(new Locator.FilterOptions().setHasText("Condition:"));
        this.productBrand        = page.locator(".product-information p").filter(new Locator.FilterOptions().setHasText("Brand:"));
        this.addToCartButton     = page.locator("button[type='button']").filter(new Locator.FilterOptions().setHasText("Add to cart"));
        this.quantityInput       = page.locator("#quantity");
        this.reviewSection       = page.locator("#review-form");
        this.viewProductLinks    = page.locator("a[href*='product_details']");
    }

    public void navigateToProduct(int productId) {
        page.navigate(ConfigReader.BASE_URL + "/product_details/" + productId);
    }

    public void navigateToProductsPage() {
        page.navigate(ConfigReader.PRODUCTS_URL);
    }

    public void clickFirstViewProduct() {
        viewProductLinks.first().click();
    }

    public void verifyProductNameVisible() {
        assertThat(productName).isVisible();
    }

    public void verifyProductPriceVisible() {
        assertThat(productPrice).isVisible();
    }

    public void verifyProductCategoryVisible() {
        assertThat(productCategory).isVisible();
    }

    public void verifyProductAvailabilityVisible() {
        assertThat(productAvailability).isVisible();
    }

    public void verifyProductConditionVisible() {
        assertThat(productCondition).isVisible();
    }

    public void verifyProductBrandVisible() {
        assertThat(productBrand).isVisible();
    }
    
    public void verifyAddToCartButtonVisible() {
        assertThat(addToCartButton).isVisible();
    }

    public void verifyQuantityInputVisible() {
        assertThat(quantityInput).isVisible();
    }

    public void verifyReviewSectionVisible() {
        assertThat(reviewSection).isVisible();
    }

    public void verifyOnProductDetailPage(int productId) {
        assertThat(page).hasURL(ConfigReader.BASE_URL + "/product_details/" + productId);
    }

    public String getProductName() {
        return productName.textContent().trim();
    }

    public String getProductPrice() {
        return productPrice.textContent().trim();
    }

    public void setQuantity(String qty) {
        quantityInput.fill(qty);
    }

    public String getQuantityValue() {
        return quantityInput.inputValue();
    }

    public boolean isQuantityNegative() {
        String val = quantityInput.inputValue();
        try {
            return Integer.parseInt(val) < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isQuantityZeroOrNegative() {
        String val = quantityInput.inputValue();
        try {
            return Integer.parseInt(val) <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void verifyQuantityIsPositive() {
        String val = quantityInput.inputValue();
        int qty = Integer.parseInt(val);
        if (qty <= 0) {
            throw new AssertionError("Expected quantity to be positive but was: " + qty);
        }
    }

    public void clickAddToCart() {
        page.locator("button[type='button']")
            .filter(new Locator.FilterOptions().setHasText("Add to cart"))
            .click();
    }
}
