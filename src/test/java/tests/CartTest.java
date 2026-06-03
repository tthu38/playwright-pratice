package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.baseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import pages.CartPage;
import pages.ProductDetailPage;

@Feature("Add to Cart")
public class CartTest extends baseTest {
    CartPage cartPage;

    @BeforeMethod
    public void initPage() {
        cartPage = new CartPage(page);
    }

    @Description("Verify that a product can be added to the cart from the product detail page and the add to cart modal appears.")
    @Test
    public void addProductToCartFromDetailPage() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.verifyModalVisible();
        System.out.println("Add to cart modal appeared.");
    }

    @Test
    public void continueShoppingAfterAddToCart() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.verifyModalVisible();
        cartPage.clickContinueShopping();
        Assert.assertTrue(page.url().contains("product_details"));
        System.out.println("Stayed on product page after clicking Continue Shopping.");
    }

    @Test
    public void viewCartAfterAddToCart() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.verifyModalVisible();
        cartPage.clickViewCart();
        cartPage.verifyOnCartPage();
        System.out.println("Navigated to cart page.");
    }

    @Test
    public void cartHasItemAfterAdding() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.clickViewCart();
        cartPage.verifyCartHasItems();
        System.out.println("Cart has at least one item.");
    }

    @Test
    public void cartShowsProductPrice() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.clickViewCart();
        cartPage.verifyCartProductPriceVisible();
        System.out.println("Cart product price is displayed.");
    }

    @Test
    public void cartShowsProductQuantity() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.clickViewCart();
        cartPage.verifyCartProductQuantityVisible();
        System.out.println("Cart product quantity is displayed.");
    }

    @Test
    public void cartShowsProductTotal() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.clickViewCart();
        cartPage.verifyCartProductTotalVisible();
        System.out.println("Cart product total is displayed.");
    }

    @Test
    public void addProductWithCustomQuantity() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPageWithQuantity("2");
        cartPage.clickViewCart();
        cartPage.verifyQuantityInCart("2");
        System.out.println("Product added to cart with quantity 2.");
    }

    @Test
    public void addMultipleProductsToCart() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.clickContinueShopping();
        cartPage.navigateToProduct(2);
        cartPage.addToCartFromDetailPage();
        cartPage.clickViewCart();
        cartPage.verifyCartItemCount(2);
        System.out.println("Cart has 2 different products.");
    }

    @Test
    public void removeItemFromCart() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.clickViewCart();
        int countBefore = cartPage.getCartItemCount();
        cartPage.removeFirstItem();
        page.waitForTimeout(1000);
        int countAfter = cartPage.getCartItemCount();
        Assert.assertEquals(countAfter, countBefore - 1, "Item count should decrease by 1 after removal");
        System.out.println("Item removed. Cart count: " + countBefore + " -> " + countAfter);
    }

    @Test
    public void removeAllItemsCartIsEmpty() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.clickContinueShopping();
        cartPage.navigateToProduct(2);
        cartPage.addToCartFromDetailPage();
        cartPage.clickContinueShopping();
        cartPage.navigateToProduct(3);
        cartPage.addToCartFromDetailPage();
        cartPage.clickViewCart();
        cartPage.removeAllItems();
        cartPage.verifyCartIsEmpty();
        System.out.println("Cart is empty after removing all items.");
    }

    @Test
    public void proceedToCheckoutButtonVisible() {
        cartPage.navigateToProduct(1);
        cartPage.addToCartFromDetailPage();
        cartPage.clickViewCart();
        cartPage.verifyProceedToCheckoutVisible();
        System.out.println("Proceed to Checkout button is visible.");
    }

    @Test
    public void addProductFromProductsListing() {
        cartPage.navigateToProducts();
        cartPage.addFirstProductFromListing();
        cartPage.verifyModalVisible();
        System.out.println("Product added to cart from product listing.");
    }

    @Test
    public void cartPageUrlIsCorrect() {
        cartPage.navigateToCart();
        cartPage.verifyOnCartPage();
        System.out.println("Cart page URL is correct.");
    }

    // ── Negative quantity tests ──────────────────────────────────────────

    @Test
    public void negativeQuantityFieldNotAccepted() {
        ProductDetailPage productDetailPage = new ProductDetailPage(page);
        productDetailPage.navigateToProduct(1);
        productDetailPage.setQuantity("-1");
        // HTML number input with min=1 should coerce or block negative values
        Assert.assertFalse(productDetailPage.isQuantityNegative(),
            "Quantity field should not hold a negative value");
        System.out.println("Quantity after entering -1: " + productDetailPage.getQuantityValue());
    }

    @Test
    public void zeroQuantityNotAllowed() {
        ProductDetailPage productDetailPage = new ProductDetailPage(page);
        productDetailPage.navigateToProduct(1);
        productDetailPage.setQuantity("0");
        Assert.assertFalse(productDetailPage.isQuantityZeroOrNegative(),
            "Quantity field should not hold zero or negative value");
        System.out.println("Quantity after entering 0: " + productDetailPage.getQuantityValue());
    }

    @Test
    public void negativeQuantityAddToCartUsesPositiveQty() {
        ProductDetailPage productDetailPage = new ProductDetailPage(page);
        productDetailPage.navigateToProduct(1);
        productDetailPage.setQuantity("-5");
        productDetailPage.clickAddToCart();
        cartPage.clickViewCart();
        // Cart must have exactly 1 row and quantity must be positive
        cartPage.verifyCartHasItems();
        String qtyInCart = page.locator(".cart_quantity button").first().textContent().trim();
        int qty = Integer.parseInt(qtyInCart);
        Assert.assertTrue(qty > 0, "Quantity in cart must be positive, but was: " + qty);
        System.out.println("Quantity in cart after entering -5: " + qty);
    }

    @Test
    public void largeNegativeQuantityNotStoredInCart() {
        ProductDetailPage productDetailPage = new ProductDetailPage(page);
        productDetailPage.navigateToProduct(1);
        productDetailPage.setQuantity("-100");
        productDetailPage.clickAddToCart();
        cartPage.clickViewCart();
        cartPage.verifyCartHasItems();
        String qtyInCart = page.locator(".cart_quantity button").first().textContent().trim();
        int qty = Integer.parseInt(qtyInCart);
        Assert.assertTrue(qty > 0, "Quantity in cart must be positive, but was: " + qty);
        System.out.println("Quantity in cart after entering -100: " + qty);
    }

    @Test
    public void negativeQuantityFieldRemainsPositiveAfterTyping() {
        ProductDetailPage productDetailPage = new ProductDetailPage(page);
        productDetailPage.navigateToProduct(1);
        productDetailPage.setQuantity("-3");
        productDetailPage.verifyQuantityIsPositive();
        System.out.println("Quantity field is positive after typing -3: " + productDetailPage.getQuantityValue());
    }
}
