package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GridPage {

    private final Page page;
    public final Locator gridItems;

    public GridPage(Page page) {
        this.page = page;
        gridItems = page.locator(".item");
    }

    public void navigate() {
        page.navigate("/grid");
    }

    public Locator itemAt(int oneBasedPosition) {
        return gridItems.nth(oneBasedPosition - 1);
    }

    public Locator titleOf(Locator item) {
        return item.locator("h4");
    }

    public Locator priceOf(Locator item) {
        return item.locator("p");
    }

    public Locator imageOf(Locator item) {
        return item.locator("img");
    }

    public Locator buttonOf(Locator item) {
        return item.locator("button");
    }
}
