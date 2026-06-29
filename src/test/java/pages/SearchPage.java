package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SearchPage {

    private final Page page;

    private final Locator searchInput;
    private final Locator submitButton;
    public final Locator resultText;

    public SearchPage(Page page) {
        this.page = page;
        searchInput  = page.locator("input[name='searchWord']");
        submitButton = page.locator("button[type='submit']");
        resultText   = page.locator("p#result");
    }

    public void navigate() {
        page.navigate("/search");
    }

    public void search(String term) {
        searchInput.fill(term);
        submitButton.click();
    }

    public void submitEmpty() {
        submitButton.click();
    }
}
