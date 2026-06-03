package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.baseTest;
import pages.ProductDetailPage;

public class ProductDetailTest extends baseTest {
    ProductDetailPage productDetailPage;

    @BeforeMethod
    public void initPage() {
        productDetailPage = new ProductDetailPage(page);
    }

    @Test
    public void viewProductDetailFromProductList() {
        productDetailPage.navigateToProductsPage();
        productDetailPage.clickFirstViewProduct();
        Assert.assertTrue(page.url().contains("product_details"));
        System.out.println("Navigated to product detail page from product list.");
    }

    @Test
    public void verifyProductNameIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyProductNameVisible();
        System.out.println("Product name: " + productDetailPage.getProductName());
    }

    @Test
    public void verifyProductPriceIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyProductPriceVisible();
        System.out.println("Product price: " + productDetailPage.getProductPrice());
    }

    @Test
    public void verifyProductCategoryIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyProductCategoryVisible();
        System.out.println("Product category is displayed.");
    }

    @Test
    public void verifyProductAvailabilityIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyProductAvailabilityVisible();
        System.out.println("Product availability is displayed.");
    }

    @Test
    public void verifyProductConditionIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyProductConditionVisible();
        System.out.println("Product condition is displayed.");
    }

    @Test
    public void verifyProductBrandIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyProductBrandVisible();
        System.out.println("Product brand is displayed.");
    }

    @Test
    public void verifyAddToCartButtonIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyAddToCartButtonVisible();
        System.out.println("Add to cart button is displayed.");
    }

    @Test
    public void verifyQuantityInputIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyQuantityInputVisible();
        System.out.println("Quantity input is displayed.");
    }

    @Test
    public void verifyDefaultQuantityIsOne() {
        productDetailPage.navigateToProduct(1);
        String qty = productDetailPage.getQuantityValue();
        Assert.assertEquals(qty, "1", "Default quantity should be 1");
        System.out.println("Default quantity is: " + qty);
    }

    @Test
    public void verifyQuantityCanBeChanged() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.setQuantity("3");
        Assert.assertEquals(productDetailPage.getQuantityValue(), "3", "Quantity should update to 3");
        System.out.println("Quantity updated to 3 successfully.");
    }

    @Test
    public void verifyReviewSectionIsDisplayed() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyReviewSectionVisible();
        System.out.println("Review section is displayed.");
    }

    @Test
    public void verifyProductDetailPageUrl() {
        productDetailPage.navigateToProduct(1);
        productDetailPage.verifyOnProductDetailPage(1);
        System.out.println("Product detail page URL is correct.");
    }

    @Test
    public void verifyDifferentProductsHaveOwnDetailPage() {
        productDetailPage.navigateToProduct(2);
        productDetailPage.verifyOnProductDetailPage(2);
        productDetailPage.verifyProductNameVisible();
        productDetailPage.verifyProductPriceVisible();
        System.out.println("Product 2 detail page loaded correctly: " + productDetailPage.getProductName());
    }
}