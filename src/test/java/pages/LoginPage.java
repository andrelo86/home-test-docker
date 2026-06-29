package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator submitButton;
    public final Locator welcomeMessage;
    public final Locator errorMessage;

    public LoginPage(Page page) {
        this.page = page;
        usernameInput  = page.locator("input[name='username']");
        passwordInput  = page.locator("input[name='password']");
        submitButton   = page.locator("button.signin");
        welcomeMessage = page.locator("p[data-id='username']");
        errorMessage   = page.locator("h2#message");
    }

    public void navigate() {
        page.navigate("/login");
    }

    public void login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        submitButton.click();
    }
}
