package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void setUpPage() {
        loginPage = new LoginPage(page);
        loginPage.navigate();
    }

    @Test
    void loginSuccess() {
        loginPage.login("johndoe19", "supersecret");
        assertThat(loginPage.welcomeMessage).isVisible();
        assertThat(loginPage.welcomeMessage).containsText("johndoe19");
    }

    @Test
    void loginFailureWithWrongCredentials() {
        loginPage.login("wronguser", "wrongpassword");
        assertThat(loginPage.errorMessage).isVisible();
    }

    @Test
    void loginFailureWithBlankFields() {
        loginPage.login("", "");
        assertThat(loginPage.errorMessage).isVisible();
    }
}
