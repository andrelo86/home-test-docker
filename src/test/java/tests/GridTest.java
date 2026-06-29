package tests;

import base.BaseTest;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.GridPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class GridTest extends BaseTest {

    private GridPage gridPage;

    @BeforeEach
    void setUpPage() {
        gridPage = new GridPage(page);
        gridPage.navigate();
    }

    @Test
    void itemAtPositionSevenIsSuperPepperoni() {
        Locator item = gridPage.itemAt(7);
        assertThat(gridPage.titleOf(item)).hasText("Super Pepperoni");
        assertThat(gridPage.priceOf(item)).hasText("$10");
    }

    @Test
    void allItemsHaveRequiredFields() {
        int count = gridPage.gridItems.count();
        assertTrue(count > 0, "Grid must contain at least one item");

        for (int i = 0; i < count; i++) {
            Locator item = gridPage.gridItems.nth(i);
            String itemLabel = "Item at position " + (i + 1);

            String title = gridPage.titleOf(item).textContent().trim();
            assertFalse(title.isEmpty(), itemLabel + " must have a non-empty title");

            String price = gridPage.priceOf(item).textContent().trim();
            assertFalse(price.isEmpty(), itemLabel + " must have a non-empty price");

            String src = gridPage.imageOf(item).getAttribute("src");
            assertNotNull(src, itemLabel + " must have an image");
            assertFalse(src.isEmpty(), itemLabel + " image src must not be empty");

            assertThat(gridPage.buttonOf(item)).isVisible();
        }
    }
}
