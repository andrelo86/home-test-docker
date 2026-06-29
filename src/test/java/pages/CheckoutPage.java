package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class CheckoutPage {

    private final Page page;

    private final Locator fullNameInput;
    private final Locator emailInput;
    private final Locator addressInput;
    private final Locator cityInput;
    private final Locator stateInput;
    private final Locator zipInput;
    private final Locator cardNameInput;
    private final Locator cardNumberInput;
    private final Locator expMonthSelect;
    private final Locator expYearInput;
    private final Locator cvvInput;
    private final Locator submitButton;

    public final Locator shippingCheckbox;
    public final Locator orderNumber;
    public final Locator itemPrices;
    public final Locator cartTotal;

    public CheckoutPage(Page page) {
        this.page = page;
        fullNameInput   = page.locator("input#fname");
        emailInput      = page.locator("input#email");
        addressInput    = page.locator("input#adr");
        cityInput       = page.locator("input#city");
        stateInput      = page.locator("input#state");
        zipInput        = page.locator("input#zip");
        cardNameInput   = page.locator("input#cname");
        cardNumberInput = page.locator("input#ccnum");
        expMonthSelect  = page.locator("select#expmonth");
        expYearInput    = page.locator("input#expyear");
        cvvInput        = page.locator("input#cvv");
        shippingCheckbox = page.locator("input[name='sameadr']");
        submitButton    = page.locator("button.btn");
        orderNumber     = page.locator("p[data-id='ordernumber']");

        Locator cartSection = page.locator(".col-25 .container");
        itemPrices = cartSection.locator("p:has(a) span.price");
        cartTotal  = cartSection.locator("p").last().locator("span.price");
    }

    public void navigate() {
        page.navigate("/checkout");
    }

    public void fillBillingForm() {
        fullNameInput.fill("John Doe");
        emailInput.fill("john.doe@example.com");
        addressInput.fill("123 Main St");
        cityInput.fill("Springfield");
        stateInput.fill("IL");
        zipInput.fill("62701");
        cardNameInput.fill("John Doe");
        cardNumberInput.fill("4111111111111111");
        expMonthSelect.selectOption("January");
        expYearInput.fill("2028");
        cvvInput.fill("123");
    }

    public void submit() {
        submitButton.click();
    }

    public double sumItemPrices() {
        List<String> texts = itemPrices.allTextContents();
        return texts.stream()
                .mapToDouble(t -> Double.parseDouble(t.replace("$", "").trim()))
                .sum();
    }

    public double getCartTotal() {
        String text = cartTotal.textContent();
        return Double.parseDouble(text.replace("$", "").trim());
    }
}
