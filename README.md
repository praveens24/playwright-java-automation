# Playwright Java UI Automation Framework

This repository contains a production-ready, highly optimized **UI Test Automation Framework** built using **Playwright for Java**, **JUnit 5**, and **Maven**. It features a modern **Page Object Model (POM)** design, customized parallel execution topologies, and robust auto-waiting web assertions designed to eliminate flaky tests in Continuous Integration (CI) runners.

The framework is actively validated against the live functional modules of the [SauceDemo e-commerce website](https://saucedemo.com).

---

## 🚀 Key Framework Features

* **Parallel Run Architecture:** Fine-tuned Maven Surefire and JUnit Engine synchronization to safely run test classes concurrently without sharing browser states.
* **Flake-Resistant Page Objects:** Utilizes native Playwright Web Assertions (`assertThat`) featuring polling-based retry intervals instead of brittle instant boolean checks or thread sleeps.
* **Automated CI Diagnostics:** Integrated with GitHub Actions to automatically record step-by-step graphical **Playwright Traces** and snapshots immediately upon a test failure.
* **Global Environment Management:** Clean parameter abstraction layer using a standard `config.properties` file with dynamic fallbacks to system variables for injected secrets.
* **Type-Safe Element Wrapper:** A robust `BasePage` utility layer that protects repetitive core browser routines (`click`, `fill`, `getText`) with explicit element-presence validations.

---

## ⚡ Parallel Test Execution Architecture

To achieve optimal build speeds, this framework runs test suites concurrently using a split isolation model managed via `pom.xml` and `junit-platform.properties`.

### 1. The Concurrency Strategy
* **Class-Level Concurrency:** Different test classes (e.g., `LoginTest`, `InventoryTest`, `CartTest`) run **simultaneously** in completely isolated process tracks.
* **Method-Level Sequentialism:** Test methods *within* a single class run sequentially. This approach ensures individual tests don't overlap, lock, or corrupt the class-level `Page` and `BrowserContext` resource references.

### 2. Thread Pool Sizing
The framework uses a **Dynamic Thread Strategy**. By passing the parameter configuration `<forkCount>1C</forkCount>` down to the Maven Surefire plugin, the build engine checks the underlying CPU core count of the host computer and spins up exactly 1 thread worker per core.
* **Local Run Example (Quad-Core CPU):** Spins up **4 concurrent runners** to process 4 test files at once.
* **GitHub Actions VM Runner:** Spins up **2 concurrent runners**, matching the specific hardware limits of the cloud execution environment safely without resource exhaustion.

---

## 📁 Project Directory Structure

```text
├── .github/workflows/           # GitHub Actions CI workflow pipelines
├── src/
│   ├── test/
│   │   ├── java/org/example/
│   │   │   ├── config/          # TestConfig environment-loading properties engine
│   │   │   ├── core/            # PlaywrightManager lifecycle orchestration
│   │   │   ├── page/            # Page Object Model components (BasePage, LoginPage, etc.)
│   │   │   └── test/            # Isolated test classes running in parallel
│   │   └── resources/           # App settings properties & junit parallel profiles
└── pom.xml                      # Dependencies, build plugins, and fork managers
```

---

## 🔧 Getting Started & Local Installation

### 1. Clone the Repository
```bash
git clone https://github.com
cd playwright-java-automation
```

### 2. Configure Your Local Java Environment
This framework is configured to compile using **Java 21** tokens. Ensure your project SDK settings inside your IDE (such as IntelliJ IDEA) are set to Java 21, or modify the property versions inside your `pom.xml` to fallback to Java 17 to match your locally installed environment.

### 3. Build Project & Download Playwright Browsers
Run the maven compile cycle and pull the required browser binaries directly via the Playwright command-line tool interface:
```bash
mvn clean install -DskipTests
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"
```

---

## 🏃 Running Automated Tests

### Run All Test Suites in Parallel
```bash
mvn test
```

### Run a Single Test Class Separately
```bash
mvn test -Dtest=InventoryTest
```

### Debug Locally (Headed Mode)
To see the browser step through actions on your screen, locate your `src/test/resources/config.properties` configuration file and adjust the headless parameter flag:
```properties
env.headless=false
```

---

## 📊 CI/CD Pipelines & Trace Analysis

The framework utilizes a built-in workflow file that runs on every code push or Pull Request validation cycle targeting your main codebase branches.

### Interrogating Failures via Playwright Trace Viewer:
If a test failure occurs on the headless Linux runner environment in GitHub Actions:
1. Open the summary section of the failed workflow pipeline run.
2. Locate the uploaded build artifacts and download the **`playwright-java-reports`** archive folder.
3. Unzip the package contents, go to [trace.playwright.dev](https://playwright.dev) in your browser, and drag-and-drop the specific failure trace zip file (e.g., `shouldHaveBuggerMenuInLeftSideOfThePage-trace.zip`) straight into the viewer window.
4. This opens a visual timeline slider, console diagnostics tracking, and DOM snapshot trees showing exactly why the UI state did not align with your test conditions.

---

## 📝 Test Extension Compliance Rules

When adding new tests to this repository, you must avoid instant boolean validation checks to prevent race conditions on slower CI servers. Always use Playwright's `assertThat()` API, passing it a `Locator` object so it can handle waiting periods automatically:

```java
// ❌ BAD: Immediate evaluation without waiting (Will flake on slow CI environments)
assertTrue(inventoryPage.isSideBarMenuVisible());

//  GOOD: Auto-waiting assertion loops up to 5 seconds until element is verified stable
assertThat(inventoryPage.getSideBarMenuLocator()).isVisible();
```
