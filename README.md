# home-test-docker

Automated tests for the `automaticbytes/demo-app` web application, written in Java with Playwright and JUnit 5.

## Prerequisites

**To run with Docker Compose (recommended):**
- Docker Desktop

**To run locally:**
- Java 21+
- Maven 3.8+
- Docker Desktop (to run the demo app)

## Running with Docker Compose

This is the recommended approach. Docker Desktop handles the demo app and the test runner together. The process exits with the test result code.

```bash
docker compose up --exit-code-from tests
```

Test reports are mounted to `./test-results/` on the host.

To rebuild the test image after code changes:

```bash
docker compose up --build --exit-code-from tests
```

## Running tests locally

Start the demo app first:

```bash
docker run -p 3100:3100 automaticbytes/demo-app
```

Install Playwright browsers (first time only):

```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install" -Dexec.classpathScope=test
```

Run all tests:

```bash
mvn test
```

Run on a specific browser (default is `chromium`):

```bash
mvn test -DBROWSER=firefox
mvn test -DBROWSER=webkit
```

Run with a visible browser window (headed mode):

```bash
mvn test -DHEADED=true
```

Run with mobile device emulation:

```bash
mvn test -DBROWSER=chromium -DDEVICE="Pixel 5"
mvn test -DBROWSER=webkit   -DDEVICE="iPhone 12"
```

Run a specific test class:

```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=CheckoutTest
mvn test -Dtest=GridTest
mvn test -Dtest=SearchTest
```

Test reports are written to `target/surefire-reports/`.

## Project structure

```
src/test/java/
├── base/
│   └── BaseTest.java       browser lifecycle (launch once per class, fresh context per test)
├── pages/
│   ├── LoginPage.java      /login interactions
│   ├── CheckoutPage.java   /checkout form, cart helpers
│   ├── GridPage.java       /grid item access
│   └── SearchPage.java     /search interactions
└── tests/
    ├── LoginTest.java       3 login scenarios
    ├── CheckoutTest.java    3 checkout scenarios
    ├── GridTest.java        2 grid scenarios
    └── SearchTest.java      2 search scenarios
```

## Test scenarios

| Test class | Scenario |
|---|---|
| LoginTest | Login with valid credentials and assert welcome message |
| LoginTest | Login with wrong credentials and assert error message |
| LoginTest | Login with blank fields and assert error message |
| CheckoutTest | Submit order with shipping same as billing and assert order number |
| CheckoutTest | Submit with shipping unchecked, assert alert appears, confirm it |
| CheckoutTest | Assert cart total matches sum of item prices |
| GridTest | Item at position 7 is Super Pepperoni at $10 |
| GridTest | All grid items have a title, price, image and button |
| SearchTest | Search returns result for a valid term |
| SearchTest | Empty search shows validation error |
