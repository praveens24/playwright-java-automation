# Playwright Java UI Automation Framework

This repository contains a production-ready, containerized **UI Test Automation Framework** built using **Playwright for Java**, **JUnit 5**, and **Maven**. It features a modern **Page Object Model (POM)** design, a localized Docker execution setup, and auto-waiting assertions optimized to eliminate flaky tests in Continuous Integration (CI) environments.

The framework is actively validated against the live functional modules of the [SauceDemo e-commerce website](https://saucedemo.com).

---

## 🚀 Key Framework Features

* **📦 Dockerized Isolation:** Encapsulates Java, Maven, and native Playwright browser binaries into a reproducible Linux capsule. Eliminates environment drift entirely.
* **⚡ Parallel Run Architecture:** Tailored Maven Surefire and JUnit 5 configurations to execute test classes concurrently, cutting execution times in half.
* **🔧 Flake-Resistant Design:** Utilizes native Playwright Web Assertions (`assertThat`) with polling-based retry intervals instead of brittle thread sleeps.
* **📊 Automated CI Diagnostics:** Integrated with GitHub Actions to automatically record step-by-step graphical **Playwright Traces** and snapshots immediately upon a test failure.
* **🧹 Clean File Filtering:** Custom `.gitignore` layout protects your source control from massive test recordings, videos, or local compiler caches.

---

## ⚡ Parallel Run & Thread Configuration

To achieve optimal execution speeds, this framework runs test suites concurrently using a split isolation model:
* **Class-Level Concurrency:** Different test files (e.g., `LoginTest`, `InventoryTest`, `CartTest`) run **simultaneously** in completely isolated process tracks.
* **Method-Level Sequentialism:** Test methods *within* a single class run sequentially to prevent shared browser context cross-contamination.

The framework utilizes a dynamic thread allocation setting (`<forkCount>1C</forkCount>`). It scans the host hardware at runtime and spins up **1 concurrent thread runner per available CPU core** (e.g., 2 parallel tracks on a standard GitHub Actions runner runner node).

---

## 🐳 Running inside Docker Locally

Running your tests inside Docker ensures your execution environment perfectly matches your cloud CI pipelines without polluting your MacBook host machine with system packages or browsers.

### 1. Build the Docker Image
Run the following command in your root directory to compile the project and bake the required Chromium, Firefox, and WebKit browser binaries directly into the image layers:
```bash
docker build -t playwright-tests .
```

### 2. Execute Tests & Mount Volume
To run the automated test suite and capture diagnostic snapshots, you must mount your local `target` folder. This ensures screenshots and trace files generated inside the headless container are safely written back to your laptop:
```bash
docker run --rm -v "\$(pwd)/target:/app/target" playwright-tests
```
* `--rm`: Automatically destroys the container instance when the test run closes to free up system RAM.
* `-v`: Binds your local directory to the container volume layer to preserve build artifacts.

---

## 🏃 Running via Native Maven (Local Fallback)

If you prefer to execute tests locally outside of Docker during active development, ensure you have **Java 21** selected in your IDE and run:

```bash
# Pull browser binaries down locally (First time only)
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"

# Run all test suites in parallel
mvn test

# Run a specific test class separately
mvn test -Dtest=InventoryTest
```

---

## 📊 CI/CD Pipeline & Trace Analysis

The repository uses an automated GitHub Actions pipeline configuration (`.github/workflows/maven.yml`) that triggers automatically on every push or Pull Request validation cycle targeting `main` or `master` branches.

### Interrogating Failures via Playwright Trace Viewer:
If a test failure occurs inside a headless cloud container run:
1. Open the summary page of the failed workflow pipeline run on GitHub.
2. Locate the uploaded build artifacts and download the **`playwright-java-reports`** archive zip folder.
3. Unzip the package contents, go to [trace.playwright.dev](https://playwright.dev) in your browser, and drag-and-drop the specific failure trace zip file (e.g., `shouldHaveBuggerMenuInLeftSideOfThePage-trace.zip`) straight into the viewer window.
4. This unlocks a visual timeline slider, console tracking matrices, and live DOM snapshot trees showing exactly why the UI state did not align with your test conditions.

---

## 📝 Test Extension Compliance Rules

When adding new tests or page objects to this repository, you must avoid instant boolean validation checks to prevent race conditions on slower CI servers. Always use Playwright's `assertThat()` API, passing it a `Locator` object so it can handle waiting periods automatically:

```java
// ❌ BAD: Immediate evaluation without waiting (Will flake on slow CI environments)
assertTrue(inventoryPage.isSideBarMenuVisible());

//  GOOD: Auto-waiting assertion loops up to 5 seconds until element is verified stable
assertThat(inventoryPage.getSideBarMenuLocator()).isVisible();
```
