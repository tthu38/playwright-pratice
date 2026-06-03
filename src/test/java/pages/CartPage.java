package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.ConfigReader;

public class CartPage {
    private Page page;

    private Locator cartRows;
    private Locator cartProductNames;
    private Locator cartProductPrices;
    private Locator cartProductQuantities;
    private Locator cartProductTotals;
    private Locator deleteButtons;
    private Locator proceedToCheckoutButton;
    private Locator emptyCartMessage;

    // Modal after adding to cart
    private Locator modalDialog;
    private Locator continueShoppingButton;
    private Locator viewCartButton;

    // Products page add-to-cart
    private Locator productCards;

    public CartPage(Page page) {
        this.page = page;
        this.cartRows               = page.locator("#cart_info_table tbody tr");
        this.cartProductNames       = page.locator(".cart_description h4 a");
        this.cartProductPrices      = page.locator(".cart_price p");
        this.cartProductQuantities  = page.locator(".cart_quantity button");
        this.cartProductTotals      = page.locator(".cart_total p");
        this.deleteButtons          = page.locator(".cart_quantity_delete");
        this.proceedToCheckoutButton = page.locator(".btn.btn-default.check_out");
        this.emptyCartMessage       = page.locator("#empty_cart");
        this.modalDialog            = page.locator("#cartModal");
        this.continueShoppingButton = modalDialog.locator("button").filter(new Locator.FilterOptions().setHasText("Continue Shopping"));
        this.viewCartButton         = modalDialog.locator("a").filter(new Locator.FilterOptions().setHasText("View Cart"));
        this.productCards           = page.locator(".productinfo");
    }

    public void navigateToCart() {
        page.navigate(ConfigReader.BASE_URL + "/view_cart");
    }

    public void navigateToProducts() {
        page.navigate(ConfigReader.PRODUCTS_URL);
    }

    public void navigateToProduct(int productId) {
        page.navigate(ConfigReader.BASE_URL + "/product_details/" + productId);
    }

    // --- Add to cart from product detail page ---
    public void addToCartFromDetailPage() {
        page.locator("button[type='button']")
            .filter(new Locator.FilterOptions().setHasText("Add to cart"))
            .click();
    }

    public void addToCartFromDetailPageWithQuantity(String qty) {
        page.locator("#quantity").fill(qty);
        addToCartFromDetailPage();
    }

    // --- Modal actions ---
    public void verifyModalVisible() {
        assertThat(modalDialog).isVisible();
    }

    public void clickContinueShopping() {
        continueShoppingButton.click();
    }

    public void clickViewCart() {
        viewCartButton.click();
    }

    // --- Add to cart from products listing (hover) ---
    public void addFirstProductFromListing() {
        Locator firstCard = productCards.first();
        firstCard.hover();
        firstCard.locator("a").filter(new Locator.FilterOptions().setHasText("Add to cart")).click();
    }

    // --- Cart page assertions ---
    public void verifyOnCartPage() {
        assertThat(page).hasURL(ConfigReader.BASE_URL + "/view_cart");
    }

    public void verifyCartHasItems() {
        assertThat(cartRows.first()).isVisible();
    }

    public void verifyCartIsEmpty() {
        assertThat(emptyCartMessage).isVisible();
    }

    public void verifyCartItemCount(int expectedCount) {
        assertThat(cartRows).hasCount(expectedCount);
    }

    public void verifyProductNameInCart(String productName) {
        assertThat(cartProductNames.filter(new Locator.FilterOptions().setHasText(productName))).isVisible();
    }

    public void verifyCartProductPriceVisible() {
        assertThat(cartProductPrices.first()).isVisible();
    }

    public void verifyCartProductQuantityVisible() {
        assertThat(cartProductQuantities.first()).isVisible();
    }

    public void verifyCartProductTotalVisible() {
        assertThat(cartProductTotals.first()).isVisible();
    }

    public void verifyProceedToCheckoutVisible() {
        assertThat(proceedToCheckoutButton).isVisible();
    }

    public void verifyQuantityInCart(String expectedQty) {
        assertThat(cartProductQuantities.first()).hasText(expectedQty);
    }

    // --- Remove item ---
    public void removeFirstItem() {
        deleteButtons.first().click();
    }

    public void removeAllItems() {
        while (cartRows.count() > 0) {
            deleteButtons.first().click();
            // wait for the row to disappear before attempting next removal
            int remaining = (int) cartRows.count();
            cartRows.nth(remaining > 0 ? remaining - 1 : 0).waitFor(
                new Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options
                        .WaitForSelectorState.DETACHED)
            );
        }
    }

    public int getCartItemCount() {
        return (int) cartRows.count();
    }
}