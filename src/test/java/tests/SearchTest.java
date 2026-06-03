package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.baseTest;
import pages.SearchPage;

public class SearchTest extends baseTest {
    SearchPage searchPage;

    @BeforeMethod
    public void initPage() {
        searchPage = new SearchPage(page);
        searchPage.navigate();
    }
    @Test
    public void searchProduct() {
        searchPage.searchProduct("top");
        searchPage.verifyHasResults();
        System.out.println("Search activity completed!");
    }

    @Test
    public void searchNonExistingProduct() {
        searchPage.searchProduct("abczyx123");
        searchPage.verifyNoResults();
        System.out.println("Search for non-existing product completed!");
    }

    @Test
    public void searchEmpty() {
        searchPage.searchProduct("");
        Assert.assertTrue(page.url().contains("products"));
        System.out.println("Search with empty input completed!");
    }

    @Test
    public void searchSpecialCharacters() {
        searchPage.searchProduct("@#$%^&*");
        searchPage.verifyNoResults();
        System.out.println("Search with special characters completed!");
    }
    @Test
    public void searchCaseInsensitive() {
        searchPage.searchProduct("TOP");
        searchPage.verifyHasResults();
        System.out.println("Search with case-insensitive input completed!");
    }


}
