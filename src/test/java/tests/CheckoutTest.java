package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.CheckoutPage;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest extends BaseTest {

    private CheckoutPage checkoutPage;

    @BeforeEach
    void setUpPage() {
        checkoutPage = new CheckoutPage(page);
        checkoutPage.navigate();
    }

    @Test
    void orderSuccessWithShippingSameAsBilling() {
        checkoutPage.fillBillingForm();

        if (!checkoutPage.shippingCheckbox.isChecked()) {
            checkoutPage.shippingCheckbox.check();
        }

        checkoutPage.submit();

        assertThat(checkoutPage.orderNumber).isVisible();
        String orderText = checkoutPage.orderNumber.textContent().trim();
        assertFalse(orderText.isEmpty(), "Order confirmation must not be empty");
    }

    @Test
    void alertShownWhenShippingUnchecked() {
        checkoutPage.fillBillingForm();

        if (checkoutPage.shippingCheckbox.isChecked()) {
            checkoutPage.shippingCheckbox.uncheck();
        }

        AtomicBoolean dialogAccepted = new AtomicBoolean(false);
        page.onDialog(dialog -> {
            dialogAccepted.set(true);
            dialog.accept();
        });

        checkoutPage.submit();

        page.waitForTimeout(1000);
        assertTrue(dialogAccepted.get(), "Expected an alert dialog to appear");
    }

    @Test
    void cartTotalMatchesItemPrices() {
        double expectedTotal = checkoutPage.sumItemPrices();
        double displayedTotal = checkoutPage.getCartTotal();
        assertEquals(expectedTotal, displayedTotal, 0.01,
                "Cart total should equal the sum of individual item prices");
    }
}
