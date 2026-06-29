package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.SearchPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchTest extends BaseTest {

    private SearchPage searchPage;

    @BeforeEach
    void setUpPage() {
        searchPage = new SearchPage(page);
        searchPage.navigate();
    }

    @Test
    void searchReturnsResultForValidTerm() {
        searchPage.search("automation");
        assertThat(searchPage.resultText).containsText("Found one result for");
        assertThat(searchPage.resultText).containsText("automation");
    }

    @Test
    void searchShowsErrorForEmptyTerm() {
        searchPage.submitEmpty();
        assertThat(searchPage.resultText).containsText("Please provide a search word.");
    }
}
