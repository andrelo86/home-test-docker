package base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;

import java.util.Map;

public class BaseTest {

    protected static Playwright playwright;
    protected static Browser browser;

    protected BrowserContext context;
    protected Page page;

    protected static final String BASE_URL =
            System.getenv().getOrDefault("BASE_URL", "http://localhost:3100");

    private record DeviceConfig(String userAgent, int width, int height, double scaleFactor, boolean isMobile, boolean hasTouch) {}

    private static final Map<String, DeviceConfig> DEVICES = Map.of(
            "Pixel 5", new DeviceConfig(
                    "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.6778.33 Mobile Safari/537.36",
                    393, 727, 2.75, true, true),
            "iPhone 12", new DeviceConfig(
                    "Mozilla/5.0 (iPhone; CPU iPhone OS 14_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.2 Mobile/15E148 Safari/604.1",
                    390, 664, 3, true, true)
    );

    @BeforeAll
    static void launchBrowser() {
        PlaywrightAssertions.setDefaultAssertionTimeout(15_000);
        playwright = Playwright.create();
        String browserName = System.getProperty("BROWSER", "chromium").toLowerCase();
        boolean headless = !Boolean.parseBoolean(System.getProperty("HEADED", "false"));
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
        browser = switch (browserName) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> playwright.chromium().launch(options);
        };
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        Browser.NewContextOptions options = new Browser.NewContextOptions().setBaseURL(BASE_URL);
        String deviceName = System.getProperty("DEVICE");
        if (deviceName != null) {
            DeviceConfig device = DEVICES.get(deviceName);
            if (device != null) {
                options.setUserAgent(device.userAgent())
                        .setViewportSize(device.width(), device.height())
                        .setDeviceScaleFactor(device.scaleFactor())
                        .setIsMobile(device.isMobile())
                        .setHasTouch(device.hasTouch());
            }
        }
        context = browser.newContext(options);
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}
